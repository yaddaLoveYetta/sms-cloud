package com.kingdee.hrp.sms.system.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingdee.hrp.sms.common.dao.generate.*;
import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.pojo.RegisterModel;
import com.kingdee.hrp.sms.common.pojo.StatusCode;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.system.user.service.UserService;
import com.kingdee.hrp.sms.util.Common;
import com.kingdee.hrp.sms.util.Pinyin4jUtil;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/2/5
 */

@Service
public class UserServiceImpl extends BaseService implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 用户注册
     * 1:根据注册用户类别新增一基础资料，eg：对于供应商类别，新增一供应商信息，对于医院类别，新增一医院信息
     * 2:根据注册用户类别新增一角色(顶级角色)并授予所有权限，eg对于供应商类别，新增一供应商角色并绑定给此用户，对于医院类别，新增一医院角色并绑定给此用户
     * 3:新增注册用户,将1步中新增组织，2步中新增角色绑定到此用户
     *
     * @param registerModel 用户注册信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void register(RegisterModel registerModel) throws IOException {

        // 1:参数校验
        validateRegisterModel(registerModel);

        //2: 新增医院/供应商基础资料
        Long orgId = generateOrg(registerModel);

        // 2；为org新增一个顶级角色
        Role role = generateRole(registerModel, orgId);

        // 3:新增注册用户,将1步中新增组织，2步中新增角色绑定到此用户
        User user = generateUser(registerModel, orgId, role);

        // ======================其他对新增用户的关联处理项=================================

        // 4:给生成的角色授默认权限(角色类别的全部可用权限)
        setDefaultPermission(role);

        // 5:给生成的医院/供应商组织设置系统默认参数
        if (registerModel.getUserType() != Constants.UserRoleType.SYSTEM.value()) {
            setDefaultSystemSetting(registerModel.getUserType(), orgId);
        }

    }

    /**
     * 用户登录
     *
     * @param username 用户账号
     * @param password 用户密码 (MD5)
     * @return User
     */
    @Override
    public User login(String username, String password) {

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        criteria.andUserNameEqualTo(username);
        criteria.andPasswordEqualTo(password);

        List<User> list = userMapper.selectByExample(userExample);

        if (CollectionUtils.isEmpty(list)) {
            logger.error("用户名或密码错误!");
            throw new BusinessLogicRunTimeException(StatusCode.USERNAME_PWD_ERROR, "用户名或密码错误!");
        }

        if (list.size() > 1) {
            // 用户名是唯一的-只可能有一个用户
            logger.error("账户数据异常[用户名重复]，请联系管理员");
            throw new BusinessLogicRunTimeException("账户数据异常[用户名重复]，请联系管理员!");
        }

        User user = list.get(0);
        if (user.getStatus()) {
            logger.error("你的账户已经被禁用，请联系管理员!");
            throw new BusinessLogicRunTimeException("你的账户已经被禁用，请联系管理员!");
        }

        return user;
    }

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> getUserRole(Long userId) {

        UserEntryMapper userEntryMapper = sqlSession.getMapper(UserEntryMapper.class);
        UserEntryExample userEntryExample = new UserEntryExample();
        UserEntryExample.Criteria criteria = userEntryExample.createCriteria();
        criteria.andParentEqualTo(userId);
        // 用户子表记录
        List<UserEntry> userEntries = userEntryMapper.selectByExample(userEntryExample);
        List<Long> userRoleIds = new ArrayList<Long>();
        for (UserEntry userEntry : userEntries) {
            userRoleIds.add(userEntry.getRole());
        }

        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria roleExampleCriteria = roleExample.createCriteria();
        roleExampleCriteria.andIdIn(userRoleIds);

        return roleMapper.selectByExample(roleExample);
    }

    /**
     * 获取当前用户关联的医院信息
     *
     * @param id 当前用户所属医院id
     * @return Hospital
     */
    @Override
    public Hospital getUserLinkHospital(Long id) {

        HospitalMapper hospitalMapper = sqlSession.getMapper(HospitalMapper.class);
        return hospitalMapper.selectByPrimaryKey(id);

    }

    /**
     * 获取当前用户关联的供应商信息
     *
     * @param id 当前用户所属供应商id
     * @return Supplier
     */
    @Override
    public Supplier getUserLinkSupplier(Long id) {

        SupplierMapper supplierMapper = sqlSession.getMapper(SupplierMapper.class);
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean editPwd(Long userId, String oldPwd, String newPwd) {

        User user = null;
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andPasswordEqualTo(oldPwd);
        criteria.andIdEqualTo(userId);

        List<User> list = userMapper.selectByExample(example);

        if (list != null && list.size() > 0) {
            user = list.get(0);
        }
        if (null == user) {
            throw new BusinessLogicRunTimeException("原密码错误");
        }
        if (user.getPassword().equals(newPwd)) {
            throw new BusinessLogicRunTimeException("新密码不能与原密码相同");
        }
        user.setPassword(newPwd);
        userMapper.updateByPrimaryKey(user);
        return true;
    }

    /**
     * 用户注册时候校验用户名是否已存在
     *
     * @param userName 用户名
     * @return Boolean
     */
    private Boolean check(String userName) {

        if (!StringUtils.isNotBlank(userName)) {
            throw new BusinessLogicRunTimeException("用户名不能为空!");
        }

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        criteria.andUserNameEqualTo(userName);
        return userMapper.countByExample(userExample) > 0;

    }

    /**
     * 获取角色所有权限
     *
     * @param roleId 角色id
     * @return List<Map<String, Object>>
     */
    @Override
    public List<Map<String, Object>> getRolePermissions(Long roleId) {

        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
        Role role = roleMapper.selectByPrimaryKey(roleId);

        if (null == role) {
            throw new BusinessLogicRunTimeException("该角色不存在！");
        }

        //1：获取菜单数据
        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);

        List<Menu> menus = menuMapper.selectByExample(null);

        // 2：获取功能权限数据
        FormActionMapper formActionMapper = sqlSession.getMapper(FormActionMapper.class);

        List<FormAction> formActions = formActionMapper.selectByExample(null);

        // 3：获取该角色所有功能授权结果

        Map<Integer, AccessControl> accessControlsMap = getAccessControl(roleId);

        // 4：根据-菜单数据-权限数据-功能授权结果 构建当前角色功能授权数据结构
        List<Map<String, Object>> ret = toTree(menus, formActions, accessControlsMap, 0, role);

        return ret;
    }

    @Override
    public Map<Integer, AccessControl> getAccessControl(Long roleId) {

        // 授权结果转成Map好操作
        Map<Integer, AccessControl> ret = new HashMap<Integer, AccessControl>(32);

        AccessControlMapper accessControlMapper = sqlSession.getMapper(AccessControlMapper.class);
        AccessControlExample accessControlExample = new AccessControlExample();
        AccessControlExample.Criteria accessControlExampleCriteria = accessControlExample.createCriteria();
        accessControlExampleCriteria.andRoleIdEqualTo(roleId);

        List<AccessControl> accessControls = accessControlMapper.selectByExample(accessControlExample);

        for (AccessControl accessControl : accessControls) {
            ret.put(accessControl.getClassId(), accessControl);
        }

        return ret;
    }

    /**
     * 获取用户所有权限集合（用户有多个角色时将权限做和，||运算）
     *
     * @return 权限授权结果
     */
    @Override
    public Map<Integer, Integer> getAccessControl() {

        Map<Integer, Integer> ret = new HashMap<>(32);

        List<Role> userRole = SessionUtil.getUserRole();

        List<Map<Integer, AccessControl>> roleAccessControlList = new ArrayList<>();

        for (Role role : userRole) {
            Map<Integer, AccessControl> accessControl = getAccessControl(role.getId());
            roleAccessControlList.add(accessControl);
        }

        if (roleAccessControlList.size() == 1) {
            // 用户只有一个角色
            Map<Integer, AccessControl> roleAccessControl = roleAccessControlList.get(0);
            for (Map.Entry<Integer, AccessControl> roleAccessControlEntry : roleAccessControl.entrySet()) {
                ret.put(roleAccessControlEntry.getKey(), roleAccessControlEntry.getValue().getAccessMask());
            }

        }

        if (roleAccessControlList.size() > 1) {

            Map<Integer, AccessControl> temp = new HashMap<>(16);
            // 用户有多个角色
            for (Map<Integer, AccessControl> roleAccessControlItem : roleAccessControlList) {
                // 这里只为获取用户所有有权限的class_id
                temp.putAll(roleAccessControlItem);
            }

            for (Map.Entry<Integer, AccessControl> roleAccessControlEntry : temp.entrySet()) {

                Integer classId = roleAccessControlEntry.getKey();
                // 设置合成前默认值
                Integer allAccessMask = 0;

                // 合成多角色对相同classId的权限掩码
                for (Map<Integer, AccessControl> roleAccessControlItem : roleAccessControlList) {

                    if (roleAccessControlItem.containsKey(classId)) {
                        // 或运算合成总权限
                        allAccessMask = allAccessMask | roleAccessControlItem.get(classId).getAccessMask();
                    }

                }

                ret.put(classId, allAccessMask);

            }

        }

        return ret;
    }

    /**
     * 保存角色权限
     * 先删除roleId已有权限再新增rolePermissions
     *
     * @param roleId            角色id
     * @param accessControlList 角色授权结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRolePermissions(Long roleId, List<AccessControl> accessControlList) {

        // 1:删除roleId已存在的保存的授权结果
        AccessControlMapper accessControlMapper = sqlSession.getMapper(AccessControlMapper.class);
        AccessControlExample accessControlExample = new AccessControlExample();
        AccessControlExample.Criteria accessControlExampleCriteria = accessControlExample.createCriteria();
        accessControlExampleCriteria.andRoleIdEqualTo(roleId);
        accessControlMapper.deleteByExample(accessControlExample);

        // 可能有多个二级菜单使用相同的formActionId即多个菜单公用权限，此处对accessControlList处理，将共用formActionId的权限做|取权限和
        // 如此配置可能会出现意想不到的结果，不建议这样配置，

        List<AccessControl> accessControlWarp = new ArrayList<>();

        Map<Integer, List<AccessControl>> accessControlMap = accessControlList.stream()
                .collect(Collectors.groupingBy(AccessControlKey::getClassId));

        for (Map.Entry<Integer, List<AccessControl>> entry : accessControlMap.entrySet()) {

            if (entry.getValue().size() > 1) {
                // 多个菜单公用权限
                AccessControl accessControl = new AccessControl();

                accessControl.setClassId(entry.getValue().get(0).getClassId());
                accessControl.setRoleId(entry.getValue().get(0).getRoleId());

                // 合并后的权限掩码
                Integer accessMaskMerged = 0;
                for (AccessControl control : entry.getValue()) {
                    accessMaskMerged = accessMaskMerged | control.getAccessMask();
                }

                accessControl.setAccessMask(accessMaskMerged);

                accessControlWarp.add(accessControl);
            } else {

                accessControlWarp.addAll(entry.getValue());
            }
        }

        // 新增roleId权限项rolePermissions
        accessControlMapper.batchInsert(accessControlWarp);
    }

    /**
     * 菜单权限打包成树形结构数据
     *
     * @param menus             菜单数据
     * @param formActions       功能权限数据
     * @param accessControlsMap 角色对各模块的授权结果
     * @param parentId          菜单父节点id(传0)
     * @return list
     */
    private List<Map<String, Object>> toTree(List<Menu> menus, List<FormAction> formActions,
                                             Map<Integer, AccessControl> accessControlsMap, int parentId, Role role) {

        List<Map<String, Object>> ret = new ArrayList<>();

        /*
        001（1）:系统类别角色可用
        010（2）:医院类别角色可用
        100（4）:供应商类别角色可用*/

        int roleTypeControl = role.getType() == 1 ? 1 : role.getType() == 2 ? 2 : 4;

        for (Menu menu : menus) {

            if (menu.getParentId() == parentId) {

                // 根据parentId来递归处理该parentId下所有二级菜单
                Map<String, Object> menuItem = new LinkedHashMap<>();

                String name = menu.getName();
                Integer id = menu.getId();
                Integer formActionId = menu.getFormActionId();

                menuItem.put("id", id);
                menuItem.put("name", name);
                menuItem.put("formActionId", formActionId);

                // 递归处理parentId=id的所有子菜单
                List<Map<String, Object>> items = toTree(menus, formActions, accessControlsMap, id, role);

                if (items.size() == 0) {
                    // 明细菜单获取对应权限项
                    List<Map<String, Object>> accessList = new ArrayList<>();

                    // 是否需要进行权限控制-menu可以不进行权限控制
                    // (当t_menu不配置form_action_id或者配置的form_action在t_form_action中不存在时，认为不需要进行权限控制)-test
                    Boolean isAccessControl = false;

                    // 当前菜单可用的所有功能项
                    List<FormAction> currentSubMenuFormActions = formActions.stream()
                            .filter(formAction -> formAction.getClassId().equals(formActionId) &&
                                    formAction.getNeedAuthorization())
                            .collect(Collectors.toList());

                    currentSubMenuFormActions.forEach(formAction -> {

                        Integer accessMask = formAction.getAccessMask();
                        Integer accessUse = formAction.getAccessUse();
                        String accessName = formAction.getName();
                        Integer accessOwnerType = formAction.getOwnerType();

                        Integer currentRoleMask = 0;
                        if (accessControlsMap.containsKey(formActionId)) {
                            currentRoleMask = accessControlsMap.get(formActionId).getAccessMask();
                        }
                        // 判断是否该角色类别可用的权限，如果是，获取该权限对当前角色的设置
                        if ((accessOwnerType & roleTypeControl) == roleTypeControl) {
                            // 是该角色类别可用的权限

                            Map<String, Object> accessItem = new HashMap<>(8);

                            accessItem.put("accessMask", accessMask);
                            accessItem.put("accessUse", accessUse);
                            accessItem.put("accessName", accessName);
                            accessItem.put("enable", (currentRoleMask & accessMask) == accessMask);
                            accessList.add(accessItem);
                        }

                    });

                    if (accessList.size() > 0) {
                        menuItem.put("accessList", accessList);
                    }

                } else {
                    // 有下级菜单，当前项为一级菜单
                    menuItem.put("items", items);
                }

                if (menuItem.get("items") != null || menuItem.get("accessList") != null) {
                    ret.add(menuItem);
                }
            }
        }

        return ret;
    }

    /**
     * 获取消息通知
     *
     * @param userRoleType 角色类别
     * @param org          组织id
     * @param type         消息类别 （0未处理，1已处理，其他值全部）
     * @param pageSize     分页大小
     * @param pageNo       当前页码
     * @return Map
     */
    @Override
    public Map<String, Object> getMessage(Integer userRoleType, Long org, Integer type, Integer pageSize,
                                          Integer pageNo) {

        Map<String, Object> ret = new HashMap<>(16);

        MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);

        MessageExample example = new MessageExample();

        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andReceiverTypeEqualTo(userRoleType);
        criteria.andReceiverOrgEqualTo(org);
        if (type == 0) {
            // 未处理消息
            criteria.andStatusEqualTo(Constants.MessageStatus.UN_PROCESSED.getNumber());
        } else if (type == 1) {
            // 已处理消息
            criteria.andStatusEqualTo(Constants.MessageStatus.PROCESSED.getNumber());
        }

        example.orderBy(Message.Column.date.desc());

        if (pageNo == 1) {
            // 查询第一页数据是查询出总记录数
            PageHelper.startPage(pageNo, pageSize, true);
        } else {
            // 非第一页查询时不查询总记录数
            PageHelper.startPage(pageNo, pageSize, false);
        }

        List<Message> messages = messageMapper.selectByExample(example);

        List<Map<String, Object>> messageList = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        for (Message message : messages) {

            item = new HashMap<>(32);

            try {
                item.putAll(Common.beanToMap(message));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Integer senderType = message.getSenderType();
            Long senderOrg = message.getSenderOrg();
            Integer receiverType = message.getReceiverType();
            Long receiverOrg = message.getReceiverOrg();
            Integer messageStatus = message.getStatus();

            if (senderType == 2) {
                //医院发送的
                HospitalMapper mapper = sqlSession.getMapper(HospitalMapper.class);
                Hospital hospital = mapper.selectByPrimaryKey(senderOrg);
                item.put("senderOrg", hospital);
            } else if (senderType == 3) {
                // 供应商发送的
                SupplierMapper mapper = sqlSession.getMapper(SupplierMapper.class);
                Supplier supplier = mapper.selectByPrimaryKey(senderOrg);
                item.put("senderOrg", supplier);
            }

            if (receiverType == 2) {
                // 医院接收的
                HospitalMapper mapper = sqlSession.getMapper(HospitalMapper.class);
                Hospital hospital = mapper.selectByPrimaryKey(receiverOrg);
                item.put("receiverOrg", hospital);
            } else if (receiverType == 3) {
                // 供应商接收的
                SupplierMapper mapper = sqlSession.getMapper(SupplierMapper.class);
                Supplier supplier = mapper.selectByPrimaryKey(receiverOrg);
                item.put("receiverOrg", supplier);
            }
            item.put("status", Constants.CooperationApplyStatus.getCooperationApplyStatus(messageStatus).getName());

            messageList.add(item);
        }

        if (pageNo == 1) {
            PageInfo<Message> pageInfo = new PageInfo<Message>(messages);
            ret.put("count", pageInfo.getTotal());
        }

        //ret.put("count", messageList.size());
        ret.put("list", messageList);

        return ret;
    }

    /**
     * 设置消息为已读状态
     *
     * @param id 消息id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setMessageProcessed(Long id) {

        MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);

        Message message = new Message();
        message.setId(id);
        message.setStatus(Constants.MessageStatus.PROCESSED.getNumber());

        messageMapper.updateByPrimaryKeySelective(message);
    }

    /**
     * 获取供应商发送给医院的，申请成为其供应商的消息
     *
     * @param userRoleType 角色类别
     * @param org          组织id
     * @return Map
     */
    private List getCooperationApplyMessage(Integer userRoleType, Long org) {

        // 供应商申请成为医院供应商的消息
        List<CooperationApply> cooperationApplyList = new ArrayList<>();

        CooperationApplyMapper cooperationApplyMapper = sqlSession.getMapper(CooperationApplyMapper.class);

        CooperationApplyExample cooperationApplyExample = new CooperationApplyExample();

        CooperationApplyExample.Criteria cooperationApplyExampleCriteria = cooperationApplyExample.createCriteria();

        if (userRoleType == Constants.UserRoleType.HOSPITAL.value()) {
            // 医院
            cooperationApplyExampleCriteria.andHospitalEqualTo(org);
        } else if (userRoleType == Constants.UserRoleType.SUPPLIER.value()) {
            // 供应商
            cooperationApplyExampleCriteria.andSupplierEqualTo(org);
        }
        cooperationApplyExampleCriteria.andStatusEqualTo(Constants.CooperationApplyStatus.UN_PROCESSED.getNumber());

        List<CooperationApply> cooperationApplies = cooperationApplyMapper.selectByExample(cooperationApplyExample);

        if (cooperationApplies != null && cooperationApplies.size() > 0) {
            // 最多取2条
            int count = cooperationApplies.size() > 2 ? 2 : cooperationApplies.size();

            for (int i = 0; i < count; i++) {
                cooperationApplyList.add(cooperationApplies.get(i));
            }
        }

        return cooperationApplyList;

    }

    /**
     * 校验注册参数
     *
     * @param registerModel 前端提交的注册参数
     */
    private void validateRegisterModel(RegisterModel registerModel) {

        if (null == Constants.UserRoleType.getUserRoleType(registerModel.getUserType())) {
            logger.error("缺少或错误的注册用户类别");
            throw new BusinessLogicRunTimeException("缺少或错误的注册用户类别");
        }

        Constants.UserRoleType.getUserRoleType(registerModel.getUserType());

        if (StringUtils.isBlank(registerModel.getUserName())) {
            logger.error("缺少用户名");
            throw new BusinessLogicRunTimeException("缺少用户名");
        }

        if (StringUtils.isBlank(registerModel.getPassword())) {
            logger.error("缺少密码");
            throw new BusinessLogicRunTimeException("缺少密码");
        }

        if (StringUtils.isBlank(registerModel.getName())) {
            logger.error("缺少真实姓名");
            throw new BusinessLogicRunTimeException("缺少真实姓名");
        }

        if (StringUtils.isBlank(registerModel.getMobile())) {
            logger.error("缺少手机号码");
            throw new BusinessLogicRunTimeException("缺少手机号码");
        }

        if (registerModel.getUserType() == Constants.UserRoleType.HOSPITAL.value()
                && StringUtils.isBlank(registerModel.getRegistrationNo())) {
            logger.error("缺少医疗机构登记号");
            throw new BusinessLogicRunTimeException("缺少医疗机构登记号");
        }

        if (registerModel.getUserType() == Constants.UserRoleType.SUPPLIER.value()
                && StringUtils.isBlank(registerModel.getCreditCode())) {
            logger.error("缺少企业统一信用代码");
            throw new BusinessLogicRunTimeException("缺少企业统一信用代码");
        } else {
            if (!Common.isValidCreditCode(registerModel.getCreditCode())) {
                logger.error("企业统一信用代码不正确");
                throw new BusinessLogicRunTimeException("企业统一信用代码不正确，请检查!");
            }
        }

        if (StringUtils.isBlank(registerModel.getOrgName())) {
            logger.error("缺少企业名称");
            throw new BusinessLogicRunTimeException("缺少企业名称");
        }

        if (check(registerModel.getUserName())) {
            logger.error("该用户名已被注册");
            throw new BusinessLogicRunTimeException("该用户名已被注册,请换一个用户名!");
        }

    }

    /**
     * 新增医院/供应商基础资料
     *
     * @param registerModel 前端提交的注册信息
     * @return 新增的医院/供应商基础资料id
     */
    private Long generateOrg(RegisterModel registerModel) {

        Long orgId;
        if (registerModel.getUserType() == Constants.UserRoleType.SYSTEM.getNumber().intValue()) {
            throw new BusinessLogicRunTimeException("暂不提供系统用户注册权限!");
        } else if (registerModel.getUserType() == Constants.UserRoleType.HOSPITAL.getNumber().intValue()) {
            // 医院用户注册
            HospitalMapper hospitalMapper = sqlSession.getMapper(HospitalMapper.class);
            orgId = getId();

            Hospital hospital = new Hospital();
            hospital.setName(registerModel.getOrgName());
            // 用医院名称拼音简写做代码
            hospital.setNumber(Pinyin4jUtil.converterToFirstSpell(registerModel.getOrgName()));
            hospital.setRegistrationNo(registerModel.getRegistrationNo());
            hospital.setId(orgId);

            hospitalMapper.insertSelective(hospital);
        } else if (registerModel.getUserType() == Constants.UserRoleType.SUPPLIER.getNumber().intValue()) {
            // 供应商注册
            SupplierMapper supplierMapper = sqlSession.getMapper(SupplierMapper.class);
            orgId = getId();

            Supplier supplier = new Supplier();
            supplier.setName(registerModel.getOrgName());
            // 用公司名称拼音简写做代码
            supplier.setNumber(Pinyin4jUtil.converterToFirstSpell(registerModel.getOrgName()));
            supplier.setCreditCode(registerModel.getCreditCode());
            supplier.setId(orgId);

            supplierMapper.insertSelective(supplier);
        } else {
            throw new BusinessLogicRunTimeException("错误的用户类别!");
        }

        return orgId;

    }

    /**
     * 新增归属orgId的角色
     *
     * @param registerModel 前端提交的注册信息
     * @param orgId         医院/供应商基础资料id
     * @return 新增的角色基础资料
     */
    private Role generateRole(RegisterModel registerModel, Long orgId) throws IOException {

        Long roleId = getId();
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);

        Role role = new Role();
        role.setId(roleId);
        role.setName(registerModel.getOrgName() + "-管理员");
        role.setNumber(Pinyin4jUtil.converterToFirstSpell(registerModel.getOrgName()));
        role.setOrg(orgId);
        role.setType(registerModel.getUserType());
        role.setUserDefine(true);

        roleMapper.insertSelective(role);

        return role;
    }

    /**
     * 新增归属orgId的用户
     *
     * @param registerModel 前端提交的注册信息
     * @param orgId         医院/供应商基础资料id
     * @return 新增用户基础资料
     */
    private User generateUser(RegisterModel registerModel, Long orgId, Role role) {

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        Long userId = getId();

        User user = new User();
        user.setPassword(registerModel.getPassword());
        user.setId(userId);
        // 注册的用户都是管理员
        user.setIsAdmin(true);
        user.setMobile(registerModel.getMobile());
        user.setName(registerModel.getName());
        user.setUserName(registerModel.getUserName());
        user.setOrg(orgId);

        userMapper.insertSelective(user);

        // 生成用户角色子表
        UserEntryMapper userEntryMapper = sqlSession.getMapper(UserEntryMapper.class);

        UserEntry userEntry = new UserEntry();
        userEntry.setId(getId());
        userEntry.setParent(userId);
        userEntry.setRole(role.getId());

        userEntryMapper.insertSelective(userEntry);

        return user;
    }

    /**
     * 根据角色的类别设置角色默认授权结果
     * 只支持贵供应商与医院角色类别的默认权限设置，其他角色类别跑出异常
     *
     * @param role 角色
     */
    private void setDefaultPermission(Role role) {

        String configPath = Thread.currentThread().getContextClassLoader().getResource("config/default-permissions.xml")
                .getPath();

        if (StringUtils.isBlank(configPath)) {
            throw new BusinessLogicRunTimeException("默认权限配置文件不存在!");
        }

        SAXReader saxReader = new SAXReader();

        try {

            Document document = saxReader.read(new File(configPath));

            List<AccessControl> accessControls = parserDefaultPermissions(document, role);

            AccessControlMapper accessControlMapper = sqlSession.getMapper(AccessControlMapper.class);

            // 新增时创建的新角色，直接插入，无需先删除
            accessControlMapper.batchInsert(accessControls);

        } catch (DocumentException e) {
            logger.error("解析默认权限配置xml错误:" + e.getMessage(), e);
            throw new BusinessLogicRunTimeException("解析默认权限配置xml错误:" + e.getMessage(), e);
        }

    }

    /**
     * @param document 系统参数配置文件
     * @param role     角色
     * @return 角色默认权限列表
     */
    @SuppressWarnings("unchecked")
    private List<AccessControl> parserDefaultPermissions(Document document, Role role) {

        List<AccessControl> accessControls = new ArrayList<>();

        String roleTypeName = "";

        if (role.getType() == Constants.UserRoleType.SYSTEM.value()) {
            roleTypeName = "system";
        } else if (role.getType() == Constants.UserRoleType.HOSPITAL.value()) {
            roleTypeName = "hospital";
        } else if (role.getType() == Constants.UserRoleType.SUPPLIER.value()) {
            roleTypeName = "supplier";
        } else {
            logger.error("不支持该类别角色默认权限设置");
            throw new BusinessLogicRunTimeException("不支持该类别角色默认权限设置");
        }

        Element ele = document.getRootElement();

        List<Element> permissionItems = ele.elements();

        // 过滤出当前注册用户角色类别的配置项
        String finalRoleTypeName = roleTypeName;
        permissionItems = permissionItems.stream().filter(permissionItem -> finalRoleTypeName
                .equalsIgnoreCase(permissionItem.attribute("ownerType").getValue())).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(permissionItems)) {
            // 没有配置该角色类别的默认权限
            logger.error("没有配置角色类别:{}的默认权限", role.getType());
            return accessControls;
        }

        // permissionItems应该只有一个节点了-否则就是重复配置-只取第一个
        Element permissionItem = permissionItems.get(0);

        // 所有业务类别配置的默认权限
        List<Element> accessControlItems = permissionItem.elements();

        for (Element accessControlItem : accessControlItems) {

            AccessControl accessControl = new AccessControl();

            Integer classId = Integer.parseInt(accessControlItem.attribute("classId").getValue());
            Integer accessMask = Integer.parseInt(accessControlItem.attribute("accessMask").getValue());

            accessControl.setRoleId(role.getId());
            accessControl.setClassId(classId);
            accessControl.setAccessMask(accessMask);

            accessControls.add(accessControl);

        }

        return accessControls;

    }

    /**
     * 给医院/供应商设置默认系统参数（注册时使用）
     *
     * @param userType 用户类别
     * @param orgId    医院或供应商基础资料id
     */
    private void setDefaultSystemSetting(Integer userType, Long orgId) {

        String configPath = Thread.currentThread().getContextClassLoader().getResource("config/default-settings.xml")
                .getPath();

        SAXReader saxReader = new SAXReader();

        try {

            Document document = saxReader.read(new File(configPath));

            List<SystemSetting> systemSettings = parserSystemSettings(document, userType, orgId);

            // systemSettings正确性校验 TODO

            SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);

            systemSettingMapper.batchInsert(systemSettings);

        } catch (DocumentException e) {
            logger.error("解析系统参数配置xml错误:" + e.getMessage(), e);
            throw new BusinessLogicRunTimeException("解析系统参数配置xml错误:" + e.getMessage(), e);
        }

    }

    /**
     * 从xml解析系统参数默认配置
     *
     * @param document 系统参数配置文件
     * @param orgId    组织id
     * @return 组织的系统默认参数列表
     */
    @SuppressWarnings("unchecked")
    private List<SystemSetting> parserSystemSettings(Document document, Integer ownerType, Long orgId) {

        List<SystemSetting> systemSettings = new ArrayList<>();

        Element ele = document.getRootElement();

        List<Element> settingItems = ele.elements();

        //循环处理每一个系统参数配置
        for (Element settingItem : settingItems) {

            String settingOwnerType = settingItem.attribute("ownerType").getValue();

            if ("all".equalsIgnoreCase(settingOwnerType) || !ownerType.toString().equalsIgnoreCase(settingOwnerType)) {
                //不是该类用户的配置参数
                continue;
            }

            SystemSetting systemSetting = new SystemSetting();

            systemSetting.setOrg(orgId);

            List<Element> settingItemDetails = settingItem.elements();

            for (Element settingItemDetail : settingItemDetails) {

                String name = settingItemDetail.getName();
                String value = settingItemDetail.getStringValue();

                Object targetValue = value;
                Class<?> clazz = SystemSetting.class;
                try {

                    Field field = null;
                    // 获取SystemSetting属性，可能在是其表主键父类
                    for (; clazz != Object.class; clazz = clazz.getSuperclass()) {

                        try {
                            field = clazz.getDeclaredField(name);
                            break;
                        } catch (Exception e) {
                            //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                            //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
                            // 多余配置忽略
                            continue;
                        }
                    }

                    if (field == null) {
                        continue;
                    }

                    if (field.getType().equals(Integer.class)) {
                        targetValue = Integer.parseInt(value);
                    }
                    if (field.getType().equals(Long.class)) {
                        targetValue = Long.parseLong(value);
                    }
                    if (field.getType().equals(Boolean.class)) {
                        targetValue = Boolean.parseBoolean(value);
                    }

                    field.setAccessible(true);
                    field.set(systemSetting, targetValue);

                } catch (IllegalAccessException e) {
                    logger.error("系统参数xml配置转换异常" + e.getMessage(), e);
                    throw new BusinessLogicRunTimeException("系统参数xml配置转换异常" + e.getMessage(), e);
                }
            }

            systemSettings.add(systemSetting);
        }

        return systemSettings;
    }

}
