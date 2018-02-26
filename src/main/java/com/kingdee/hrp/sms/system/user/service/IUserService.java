package com.kingdee.hrp.sms.system.user.service;

import com.kingdee.hrp.sms.common.model.Role;
import com.kingdee.hrp.sms.common.model.User;

import java.io.Serializable;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/2/5
 */
public interface IUserService extends Serializable {

    /**
     * 用户注册
     *
     * @param user 用户pojo
     */
    void register(User user);

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码 (MD5)
     * @return
     */
    User login(String userName, String password);

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    Role getUserRole(Long userId);
}
