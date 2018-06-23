package com.kingdee.hrp.sms.system.user.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingdee.hrp.sms.common.dao.generate.*;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.filter.SmsPropertyPlaceholderConfigurer;
import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.pojo.BaseStatusEnum;
import com.kingdee.hrp.sms.common.pojo.UserRoleTypeEnum;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.system.user.controller.UserController;
import com.kingdee.hrp.sms.system.user.service.IUserService;
import com.kingdee.hrp.sms.util.Common;
import com.kingdee.hrp.sms.util.Environ;
import com.kingdee.hrp.sms.util.Pinyin4jUtil;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/2/5
 */

@Service
public class UserService extends BaseService implements IUserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 用户注册
     * 1:根据注册用户类别新增一基础资料，eg：对于供应商类别，新增一供应商信息，对于医院类别，新增一医院信息
     * 2:根据注册用户类别新增一角色(顶级角色)并授予所有权限，eg对于供应商类别，新增一供应商角色并绑定给此用户，对于医院类别，新增一医院角色并绑定给此用户
     * 3:新增注册用户,将1步中新增组织，2步中新增角色绑定到此用户
     *
     * @param registerInfo 用户注册信息
     */
    @Override
    @Transactional(rollbackFor = { Exception.class })
    public void register(Map<String, Object> registerInfo) throws IOException {

        ObjectMapper mapper = Environ.getBean(ObjectMapper.class);
        JsonNode jsonNode = mapper.readTree(mapper.writeValueAsString(registerInfo));

        Integer userType;
        String userName;
        String password;
        String name;
        String mobile;
        String creditCode;
        String orgName;

        JsonNode userTypeJsonNode = jsonNode.get("userType");
        JsonNode userNameJsonNode = jsonNode.get("userName");
        JsonNode passwordJsonNode = jsonNode.get("password");
        JsonNode nameJsonNode = jsonNode.get("name");
        JsonNode mobileJsonNode = jsonNode.get("mobile");
        JsonNode creditCodeJsonNode = jsonNode.get("creditCode");
        JsonNode orgNameJsonNode = jsonNode.get("orgName");

        if (null == userTypeJsonNode || userTypeJsonNode.asInt() <= 0) {
            logger.error("缺少注册用户类别");
            throw new BusinessLogicRunTimeException("缺少注册用户类别");
        } else {
            userType = userTypeJsonNode.asInt();
        }

        if (null == userNameJsonNode || !StringUtils.isNotBlank(userNameJsonNode.asText())) {
            logger.error("缺少用户名");
            throw new BusinessLogicRunTimeException("缺少用户名");
        } else {
            userName = userNameJsonNode.asText();
        }

        if (null == passwordJsonNode || !StringUtils.isNotBlank(passwordJsonNode.asText())) {
            logger.error("缺少密码");
            throw new BusinessLogicRunTimeException("缺少密码");
        } else {
            password = passwordJsonNode.asText();
        }

        if (null == nameJsonNode || !StringUtils.isNotBlank(nameJsonNode.asText())) {
            logger.error("缺少真实姓名");
            throw new BusinessLogicRunTimeException("缺少真实姓名");
        } else {
            name = nameJsonNode.asText();
        }

        if (null == mobileJsonNode || !StringUtils.isNotBlank(mobileJsonNode.asText())) {
            logger.error("缺少手机号码");
            throw new BusinessLogicRunTimeException("缺少手机号码");
        } else {
            mobile = mobileJsonNode.asText();
        }

        if (null == creditCodeJsonNode || !StringUtils.isNotBlank(creditCodeJsonNode.asText())) {
            String msg = "";
            if (userType == UserRoleTypeEnum.HOSPITAL.getNumber().intValue()) {
                logger.error("缺少医疗机构登记号");
                msg = "缺少医疗机构登记号";
            } else {
                logger.error("缺少企业统一信用代码");
                msg = "缺少企业统一信用代码";
            }
            throw new BusinessLogicRunTimeException(msg);
        } else {
            creditCode = creditCodeJsonNode.asText();
        }

        if (null == orgNameJsonNode || !StringUtils.isNotBlank(orgNameJsonNode.asText())) {
            logger.error("缺少企业名称");
            throw new BusinessLogicRunTimeException("缺少企业名称");
        } else {
            orgName = orgNameJsonNode.asText();
        }

        Boolean userExist = this.check(userName);

        if (userExist) {
            logger.error("该用户名已被注册");
            throw new BusinessLogicRunTimeException("该用户名已被注册,请换一个用户名!");
        }

        //1: 新增医院/供应商基础资料

        // 记录新增医院/供应商基础资料内码-新增用户关联时用
        Long org = 0L;
        if (userType == UserRoleTypeEnum.SYSTEM.getNumber().intValue()) {
            throw new BusinessLogicRunTimeException("暂不提供系统用户注册权限!");
        } else if (userType == UserRoleTypeEnum.HOSPITAL.getNumber().intValue()) {
            // 医院用户注册
            HospitalMapper hospitalMapper = sqlSession.getMapper(HospitalMapper.class);
            org = getId();

            Hospital hospital = new Hospital();
            hospital.setName(orgName);
            hospital.setNumber(Pinyin4jUtil.converterToFirstSpell(orgName));
            hospital.setCreditCode(creditCode);
            hospital.setId(org);

            hospitalMapper.insertSelective(hospital);
        } else if (userType == UserRoleTypeEnum.SUPPLIER.getNumber().intValue()) {
            // 供应商注册
            SupplierMapper supplierMapper = sqlSession.getMapper(SupplierMapper.class);
            org = getId();

            Supplier supplier = new Supplier();
            supplier.setName(orgName);
            supplier.setNumber(Pinyin4jUtil.converterToFirstSpell(orgName));
            supplier.setCreditCode(creditCode);
            supplier.setId(org);

            supplierMapper.insertSelective(supplier);
        } else {
            throw new BusinessLogicRunTimeException("错误的用户类别!");
        }

        // 2；为org新增一个顶级角色
        //记录新增角色id，用户关联时用
        Long roleId = getId();
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);

        Role role = new Role();
        role.setId(roleId);
        role.setName(orgName + "-管理员");
        role.setNumber(Pinyin4jUtil.converterToFirstSpell(orgName));
        role.setOrg(org);
        role.setType(userType);
        role.setUserDefine(true);

        roleMapper.insert(role);
        // 给生成的角色授默认权限(角色类别的全部可用权限)
        setDefaultPermission(role);

        // 3:新增注册用户,将1步中新增组织，2步中新增角色绑定到此用户
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 保存下用户id，生成用户角色子表时用
        Long userId = getId();

        User user = new User();
        user.setPassword(password);
        user.setId(userId);
        // 注册的用户都是管理员
        user.setIsAdmin(true);
        user.setMobile(mobile);
        user.setName(name);
        user.setUserName(userName);
        user.setOrg(org);

        userMapper.insertSelective(user);

        // 生成用户角色子表
        UserEntryMapper userEntryMapper = sqlSession.getMapper(UserEntryMapper.class);

        UserEntry userEntry = new UserEntry();
        userEntry.setId(getId());
        userEntry.setParent(userId);
        userEntry.setRole(roleId);

        userEntryMapper.insertSelective(userEntry);

    }

    /**
     * 根据角色的类别设置角色默认授权结果
     * 只支持贵供应商与医院角色类别的默认权限设置，其他角色类别跑出异常
     *
     * @param role 角色
     */
    private void setDefaultPermission(Role role) throws IOException {

        String roleTypeName = "";

        if (role.getType() == UserRoleTypeEnum.HOSPITAL.getNumber().intValue()) {
            roleTypeName = "hospital";
        } else if (role.getType() == UserRoleTypeEnum.SUPPLIER.getNumber().intValue()) {
            roleTypeName = "supplier";
        } else {
            logger.error("不支持该类别角色默认权限设置");
            throw new BusinessLogicRunTimeException("不支持该类别角色默认权限设置");
        }

        String defaultPermission = SmsPropertyPlaceholderConfigurer.getContextProperty(roleTypeName);

        if (defaultPermission == null || "".equals(defaultPermission.trim())) {
            logger.error(String.format("不存在[%s]默认权限配置，请检查权限配置文件!", roleTypeName));
            throw new BusinessLogicRunTimeException(String.format("不存在[%s]默认权限配置，请检查权限配置文件!", roleTypeName));
        }

        ObjectMapper mapper = Environ.getBean(ObjectMapper.class);
        JsonNode defaultPermissions = mapper.readTree(defaultPermission);

        if (!defaultPermissions.isArray()) {
            logger.error(String.format("[%s]默认权限配置错误，请检查权限配置文件!", roleTypeName));
            throw new BusinessLogicRunTimeException(String.format("[%s]默认权限配置错误，请检查权限配置文件!", roleTypeName));
        }

        List<AccessControl> accessControlList = new ArrayList<AccessControl>();

        for (JsonNode permission : defaultPermissions) {

            int classId = permission.path("classId").asInt(0);
            int accessMask = permission.path("accessMask").asInt(0);

            if (classId <= 0) {
                // ignore error conf
                continue;
            }

            AccessControl accessControl = new AccessControl();
            accessControl.setRoleId(role.getId());
            accessControl.setClassId(classId);
            accessControl.setAccessMask(accessMask);

            accessControlList.add(accessControl);
        }

        AccessControlMapper accessControlMapper = sqlSession.getMapper(AccessControlMapper.class);
        // 授权结果插入数据库--暂时走单条循环插入，应该自写mapper批量插入
        for (AccessControl accessControl : accessControlList) {
            accessControlMapper.insertSelective(accessControl);
        }
        // accessControlMapper.batchInsert(accessControlList);

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
            throw new BusinessLogicRunTimeException("用户名或密码错误!");
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
    @Transactional(rollbackFor = { Exception.class })
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

            Map<Integer, AccessControl> temp = new HashMap<>();
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
     * @return Boolean
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

        // 新增roleId权限项rolePermissions
        accessControlMapper.batchInsert(accessControlList);
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

                Map<String, Object> menuItem = new LinkedHashMap<>();

                String name = menu.getName();
                Integer id = menu.getId();
                Integer formActionId = menu.getFormActionId();

                menuItem.put("id", id);
                menuItem.put("name", name);
                menuItem.put("formActionId", formActionId);

                List<Map<String, Object>> items = toTree(menus, formActions, accessControlsMap, id, role);

                if (items.size() == 0) {
                    // 明细菜单获取对应权限项
                    List<Map<String, Object>> accessList = new ArrayList<>();

                    // 是否需要进行权限控制-menu可以不进行权限控制
                    // (当t_menu不配置form_action_id或者配置的form_action在t_form_action中不存在时，认为不需要进行权限控制)
                    Boolean isAccessControl = false;

                    for (FormAction formAction : formActions) {

                        if (formAction.getClassId().equals(formActionId)) {

                            // 有在t_form_action表中配置权限项
                            isAccessControl = true;

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

/*                                if ((currentRoleMask & accessMask) == accessMask) {
                                    // 当前角色对该权限项有权限(已授权)
                                }else {
                                    // 当前角色对该权限项没有权限(未授权)
                                }*/

                                Map<String, Object> accessItem = new HashMap<>(8);
                                accessItem.put("accessMask", accessMask);
                                accessItem.put("accessUse", accessUse);
                                accessItem.put("accessName", accessName);
                                accessItem.put("enable", (currentRoleMask & accessMask) == accessMask);
                                accessList.add(accessItem);
                            }
                        }
                    }

                    if (accessList.size() > 0) {
                        menuItem.put("accessList", accessList);
                    }

                } else {

                    if (items.size() > 0) {
                        menuItem.put("items", items);
                    }
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
            criteria.andStatusEqualTo(BaseStatusEnum.UN_PROCESSED.getNumber());
        } else if (type == 1) {
            // 已处理消息
            criteria.andStatusEqualTo(BaseStatusEnum.PROCESSED.getNumber());
        }

        example.setOrderByClause("`date` DESC");

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
            item.put("status", BaseStatusEnum.getBaseStatusEnum(messageStatus).getName());

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
        message.setStatus(BaseStatusEnum.PROCESSED.getNumber());

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

        if (userRoleType == 2) {
            // 医院
            cooperationApplyExampleCriteria.andHospitalEqualTo(org);
        } else if (userRoleType == 3) {
            // 供应商
            cooperationApplyExampleCriteria.andSupplierEqualTo(org);
        }
        cooperationApplyExampleCriteria.andStatusEqualTo(BaseStatusEnum.UN_PROCESSED.getNumber());

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

}
