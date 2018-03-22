package com.kingdee.hrp.sms.system.user.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingdee.hrp.sms.common.dao.generate.*;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.system.user.service.IUserService;
import com.kingdee.hrp.sms.util.Environ;
import com.kingdee.hrp.sms.util.Pinyin4jUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/2/5
 */

@Service
public class UserService extends BaseService implements IUserService {


    /**
     * 用户注册
     * 1:根据注册用户类别新增一基础资料，eg：对于供应商类别，新增一供应商信息，对于医院类别，新增一医院信息
     * 2:根据注册用户类别新增一角色(顶级角色)并授予所有权限，eg对于供应商类别，新增一供应商角色并绑定给此用户，对于医院类别，新增一医院角色并绑定给此用户
     * 3:新增注册用户,将1步中新增组织，2步中新增角色绑定到此用户
     *
     * @param registerInfo 用户注册信息
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void register(Map<String, Object> registerInfo) throws IOException {

        ObjectMapper mapper = Environ.getBean(ObjectMapper.class);
        JsonNode jsonNode = mapper.readTree(mapper.writeValueAsString(registerInfo));

        Integer userType;
        String userName;
        String password;
        String name;
        String mobile;
        String businessLicense;
        String taxId;
        String address;
        String orgName;

        JsonNode userTypeJsonNode = jsonNode.get("userType");
        JsonNode userNameJsonNode = jsonNode.get("userName");
        JsonNode passwordJsonNode = jsonNode.get("password");
        JsonNode nameJsonNode = jsonNode.get("name");
        JsonNode mobileJsonNode = jsonNode.get("mobile");
        JsonNode businessLicenseJsonNode = jsonNode.get("businessLicense");
        JsonNode taxIdJsonNode = jsonNode.get("taxId");
        JsonNode addressJsonNode = jsonNode.get("address");
        JsonNode orgNameJsonNode = jsonNode.get("orgName");

        if (null == userTypeJsonNode || userTypeJsonNode.asInt() <= 0) {
            throw new BusinessLogicRunTimeException("缺少注册用户类别");
        } else {
            userType = userTypeJsonNode.asInt();
        }

        if (null == userNameJsonNode || !StringUtils.isNotBlank(userNameJsonNode.asText())) {
            throw new BusinessLogicRunTimeException("缺少用户名");
        } else {
            userName = userNameJsonNode.asText();
        }

        if (null == passwordJsonNode || !StringUtils.isNotBlank(passwordJsonNode.asText())) {
            throw new BusinessLogicRunTimeException("缺少密码");
        } else {
            password = passwordJsonNode.asText();
        }

        if (null == nameJsonNode || !StringUtils.isNotBlank(nameJsonNode.asText())) {
            throw new BusinessLogicRunTimeException("缺少真实姓名");
        } else {
            name = nameJsonNode.asText();
        }

        if (null == mobileJsonNode || !StringUtils.isNotBlank(mobileJsonNode.asText())) {
            throw new BusinessLogicRunTimeException("缺少手机号码");
        } else {
            mobile = mobileJsonNode.asText();
        }

        if (null == businessLicenseJsonNode || !StringUtils.isNotBlank(businessLicenseJsonNode.asText())) {
            throw new BusinessLogicRunTimeException("缺少营业执照号");
        } else {
            businessLicense = businessLicenseJsonNode.asText();
        }

        if (null == taxIdJsonNode || !StringUtils.isNotBlank(taxIdJsonNode.asText())) {
            throw new BusinessLogicRunTimeException("缺少税务登记号");
        } else {
            taxId = taxIdJsonNode.asText();
        }

        if (null == addressJsonNode || !StringUtils.isNotBlank(addressJsonNode.asText())) {
            throw new BusinessLogicRunTimeException("缺少企业地址");
        } else {
            address = addressJsonNode.asText();
        }

        if (null == orgNameJsonNode || !StringUtils.isNotBlank(orgNameJsonNode.asText())) {
            throw new BusinessLogicRunTimeException("缺少企业名称");
        } else {
            orgName = orgNameJsonNode.asText();
        }

        Boolean userExist = this.check(userName);

        if (userExist) {
            throw new BusinessLogicRunTimeException("该用户名已被注册,请换一个用户名!");
        }


        //1: 新增医院/供应商基础资料

        // 记录新增医院/供应商基础资料内码-新增用户关联时用
        Long org = 0L;
        if (userType == 1) {
            throw new BusinessLogicRunTimeException("暂不提供系统用户注册权限!");
        } else if (userType == 2) {
            // 医院用户注册
            HospitalMapper hospitalMapper = sqlSession.getMapper(HospitalMapper.class);
            org = getId();

            Hospital hospital = new Hospital();
            hospital.setName(orgName);
            hospital.setNumber(Pinyin4jUtil.converterToFirstSpell(orgName));
            hospital.setBusinessLicense(businessLicense);
            hospital.setTaxId(taxId);
            hospital.setAddress(address);
            hospital.setId(getId());

            hospitalMapper.insert(hospital);
        } else if (userType == 3) {
            // 供应商注册
            SupplierMapper supplierMapper = sqlSession.getMapper(SupplierMapper.class);
            org = getId();

            Supplier supplier = new Supplier();
            supplier.setName(orgName);
            supplier.setNumber(Pinyin4jUtil.converterToFirstSpell(orgName));
            supplier.setBusinessLicense(businessLicense);
            supplier.setTaxId(taxId);
            supplier.setAddress(address);
            supplier.setId(getId());

            supplierMapper.insert(supplier);
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
        role.setUserDefine(false);

        roleMapper.insert(role);

        // 角色授权 TODO
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
     * 用户登录
     *
     * @param username 用户账号
     * @param password 用户密码 (MD5)
     * @return
     */
    @Override
    public User login(String username, String password) {

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        criteria.andUserNameEqualTo(username);
        criteria.andPasswordEqualTo(password);

        List<User> list = userMapper.selectByExample(userExample);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        throw new BusinessLogicRunTimeException("用户名或密码错误!");
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
    @Transactional(rollbackFor = {RuntimeException.class})
    public boolean editpwd(Long userId, String oldpwd, String newpwd) {

        User user = null;
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);


        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andPasswordEqualTo(oldpwd);
        criteria.andIdEqualTo(userId);

        List<User> list = userMapper.selectByExample(example);

        if (list != null && list.size() > 0) {
            user = list.get(0);
        }
        if (null == user) {
            throw new BusinessLogicRunTimeException("输入的原密码错误");
        }
        if (user.getPassword().equals(newpwd)) {
            throw new BusinessLogicRunTimeException("修改密码的新密码不能与原密码相同");
        }
        user.setPassword(newpwd);
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

}
