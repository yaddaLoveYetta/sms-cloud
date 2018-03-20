package com.kingdee.hrp.sms.system.user.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.*;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.system.user.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Date: 2018/2/6 0006
 * Author: H.Yang
 * Desc:
 */
@Service
public class UserService extends BaseService implements IUserService {


    /**
     * 用户注册时候校验用户名是否已存在
     *
     * @param user
     * @return
     */
    private Boolean check(User user) {

        if (!StringUtils.isNotBlank(user.getUserName())) {
            throw new BusinessLogicRunTimeException("用户名不能为空!");
        }

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        criteria.andUserNameEqualTo(user.getUserName());
        return userMapper.countByExample(userExample) == 0;

    }

    /**
     * 用户注册
     *
     * @param user 用户pojo
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void register(User user) {

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        Boolean bool = this.check(user);

        if (bool) {
            //用户数据插入数据库需要设置主键ID
            user.setId(getId());
            userMapper.insertSelective(user);
        } else {
            throw new BusinessLogicRunTimeException("用户已存在!");
        }

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

}
