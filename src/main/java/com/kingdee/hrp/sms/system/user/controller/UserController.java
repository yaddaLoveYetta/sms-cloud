package com.kingdee.hrp.sms.system.user.controller;

import com.kingdee.hrp.sms.common.domain.ResultWarp;
import com.kingdee.hrp.sms.common.domain.StatusCode;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.User;
import com.kingdee.hrp.sms.system.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
     *@param user
     */
    @RequestMapping("/register")
    public ResultWarp register(@RequestParam(value = "user") User user) {
        ResultWarp resultWarp = new ResultWarp();
        try {
            userService.register(user);
            resultWarp.setCode(StatusCode.SUCCESS);
            resultWarp.setMsg("注册成功！");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicRunTimeException("注册失败，请重新注册");
        }
        return resultWarp;
    }


    /**
     * 用户登录
     *
     * @param username
     * @param password
     */
    @RequestMapping("/login")
    public ResultWarp login(String username, String password, HttpServletRequest request) {
        ResultWarp resultWarp = new ResultWarp();
        if ("".equals(username) || "".equals(password)) {
            throw new BusinessLogicRunTimeException("用户名或密码不能为空!");
        }
        try {
            User user = userService.login(username, password);
            if(null != user){
                request.getSession().setAttribute("user",user);
                resultWarp.setCode(StatusCode.SUCCESS);
                resultWarp.setMsg("登陆成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicRunTimeException("登录失败，请联系管理员!");
        }
        return resultWarp;
    }

    /**
     * 用户注销
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        logger.info("注销成功");

        return "redirect:/index.jsp";
    }
}
