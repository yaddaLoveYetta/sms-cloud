package com.kingdee.hrp.sms.system.user.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.RoleMapper;
import com.kingdee.hrp.sms.common.dao.generate.UserMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.Role;
import com.kingdee.hrp.sms.common.model.RoleExample;
import com.kingdee.hrp.sms.common.model.User;
import com.kingdee.hrp.sms.common.model.UserExample;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.system.user.service.IUserService;
import com.kingdee.hrp.sms.util.Common;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    @Transactional(rollbackFor = {RuntimeException.class})
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
    public Role getUserRole(Long userId) {

        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);

        return roleMapper.selectByPrimaryKey(userId);
    }
    
}
