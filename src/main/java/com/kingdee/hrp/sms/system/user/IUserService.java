package com.kingdee.hrp.sms.system.user;

import java.io.Serializable;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/2/5
 */
public interface IUserService extends Serializable {

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码 (MD5)
     * @return
     */
    Boolean login(String userName, String password);

    /**
     * 登出
     *
     * @param userName 用户名
     * @return
     */
    Boolean loginOut(String userName);
}
