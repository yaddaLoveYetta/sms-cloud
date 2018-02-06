package com.kingdee.hrp.sms.system.user.controller;

import com.kingdee.hrp.sms.common.model.User;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户控制器
 *
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/24
 */
public class UserController {

    /**
     * 用户登录
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {

    }

    /**
     * 用户注册
     *@param user
     */
    public void register(@RequestParam(value = "user") User user) {

    }
}
