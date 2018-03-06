package com.kingdee.hrp.sms.system.user.service;

import com.kingdee.hrp.sms.common.model.Role;
import com.kingdee.hrp.sms.common.model.User;

import java.io.Serializable;
import java.util.List;

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
    List<Role> getUserRole(Long userId);

    /**
     * 用户修改密码
     *
     * @param userId 用户ID
     * @param oldpwd 原密码
     * @param newpwd 新密码
     * @return
     */
    boolean editpwd(Long userId, String oldpwd, String newpwd);

}
