package com.kingdee.hrp.sms.system.user.controller;

import com.kingdee.hrp.sms.common.domain.ResultWarp;
import com.kingdee.hrp.sms.common.domain.StatusCode;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.Role;
import com.kingdee.hrp.sms.common.model.User;
import com.kingdee.hrp.sms.system.user.service.IUserService;
import com.kingdee.hrp.sms.util.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 *
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/24
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Resource
    private IUserService userService;

    /**
     * 用户注册
     *
     * @param user
     */
    @RequestMapping("/register")
    @ResponseBody
    public Boolean register(@RequestParam(value = "user") User user) {

        userService.register(user);
        return true;

    }


    /**
     * 用户登录
     *
     * @param userName
     * @param password
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String userName, String password, HttpServletRequest request) {

        Map<String, Object> ret = new HashMap<>();

        if ("".equals(userName) || "".equals(password)) {
            throw new BusinessLogicRunTimeException("用户名或密码不能为空!");
        }

        User user = userService.login(userName, password);

        if (null != user) {

            Role role = userService.getUserRole(user.getRole());

            try {
                ret = Common.beanToMap(user);
                // 用户角色信息返回给客户端
                ret.put("role", role);
            } catch (Exception e) {
                throw new BusinessLogicRunTimeException(e);
            }

            // 将用户及用户角色信息放到session中
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("role", role);

            return ret;
        }

        throw new BusinessLogicRunTimeException("登录失败!");
    }

    /**
     * 用户注销
     *
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public Boolean logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        logger.info("注销成功");
        return true;
    }
}
