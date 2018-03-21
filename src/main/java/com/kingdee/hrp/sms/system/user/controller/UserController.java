package com.kingdee.hrp.sms.system.user.controller;

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
import java.util.HashMap;
import java.util.List;
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
     * <p>
     * 1:根据注册用户类别新增一基础资料，eg：对于供应商类别，新增一供应商信息，对于医院类别，新增一医院信息
     * 2:根据注册用户类别新增一角色(顶级角色)，eg对于供应商类别，新增一供应商角色并绑定给此用户，对于医院类别，新增一医院角色并绑定给此用户
     * 3:新增注册用户,将1步中新增组织，2步中新增角色绑定到此用户
     *
     * @param registerInfo
     */
    @RequestMapping("/register")
    @ResponseBody
    public Boolean register(@RequestParam Map<String, Object> registerInfo) {

        userService.register(new User());
        return true;

    }

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
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


            Long org = user.getOrg();
            // 一个用户可以对应多个角色
            List<Role> roles = userService.getUserRole(user.getId());

            // 获取用户所属的组织信息(医院/供应商)
            Object userLinkOrg = new Object();
            if (roles.get(0).getType() == 2) {
                // 医院角色
                userLinkOrg = userService.getUserLinkHospital(user.getOrg());
            }
            if (roles.get(0).getType() == 3) {
                // 供应商角色
                userLinkOrg = userService.getUserLinkSupplier(user.getOrg());
            }

            try {
                ret = Common.beanToMap(user);
                // 用户角色信息返回给客户端
                ret.put("roles", roles);
                ret.put("org", userLinkOrg);
            } catch (Exception e) {
                throw new BusinessLogicRunTimeException(e);
            }

            // 将用户及用户角色信息放到session中
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("roles", roles);
            request.getSession().setAttribute("userLinkOrg", userLinkOrg);

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

    /**
     * 用户修改密码
     *
     * @param userId 用户ID
     * @param oldpwd 原密码
     * @param newpwd 新密码
     * @return
     */
    @RequestMapping("/editpwd")
    @ResponseBody
    public Boolean editpwd(Long userId, String oldpwd, String newpwd) {

        if (userService.editpwd(userId, oldpwd, newpwd)) {
            logger.info("密码修改成功");
            return true;
        } else {
            logger.info("密码修改失败");
            return false;
        }
    }
}
