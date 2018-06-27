package com.kingdee.hrp.sms.system.user.controller;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.AccessControl;
import com.kingdee.hrp.sms.common.model.Role;
import com.kingdee.hrp.sms.common.model.User;
import com.kingdee.hrp.sms.common.pojo.RegisterModel;
import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.system.user.service.UserService;
import com.kingdee.hrp.sms.util.Common;
import com.kingdee.hrp.sms.util.SessionUtil;
import com.kingdee.hrp.sms.util.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Enumeration;
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
@RequestMapping("/user/")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 图形验证码放到session中的key
     */
    public static final String VERIFICATION_CODE_KEY = "VERIFICATION_CODE_KEY";

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * <p>
     * 1:根据注册用户类别新增一基础资料，eg：对于供应商类别，新增一供应商信息，对于医院类别，新增一医院信息
     * 2:根据注册用户类别新增一角色(顶级角色)，eg对于供应商类别，新增一供应商角色并绑定给此用户，对于医院类别，新增一医院角色并绑定给此用户
     * 3:新增注册用户,将1步中新增组织，2步中新增角色绑定到此用户
     *
     * @param registerModel
     */
    @RequestMapping("register")
    @ResponseBody
    public Boolean register(RegisterModel registerModel) throws IOException {

        logger.info("用户注册提交参数:" + registerModel);

        userService.register(registerModel);
        return true;

    }

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @param code     验证码
     */
    @RequestMapping("login")
    @ResponseBody
    public Map<String, Object> login(String userName, String password, String code, HttpServletRequest request) {

        logger.info(String.format("用户登录提交参数:userName=%s,password=%s,code=%s", userName, password, code));

        Map<String, Object> ret = new HashMap<>(8);

        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            logger.error("用户名或密码不能为空!");
            throw new BusinessLogicRunTimeException("用户名或密码不能为空!");
        }

        if (StringUtils.isBlank(code) || !checkVerificationCode(code, request.getSession(true))) {
            logger.error("验证码错误!");
            throw new BusinessLogicRunTimeException("验证码错误!");
        }

        User user = userService.login(userName, password);

        if (null != user) {

            Long org = user.getOrg();
            // 一个用户可以对应多个角色-但多个角色都属于同一个类型
            List<Role> roles = userService.getUserRole(user.getId());

            // 获取用户所属的组织信息(医院/供应商)
            Object userLinkOrg = null;
            if (roles.get(0).getType() == UserRoleType.HOSPITAL.getNumber().intValue()) {
                // 医院角色
                userLinkOrg = userService.getUserLinkHospital(user.getOrg());
            }
            if (roles.get(0).getType() == UserRoleType.SUPPLIER.getNumber().intValue()) {
                // 供应商角色
                userLinkOrg = userService.getUserLinkSupplier(user.getOrg());
            }

            try {
                ret = Common.beanToMap(user);
                // 用户角色信息返回给客户端
                ret.put("roles", roles);
                ret.put("org", null == userLinkOrg ? "" : userLinkOrg);
            } catch (Exception e) {
                throw new BusinessLogicRunTimeException(e);
            }

            // 将用户及用户角色信息放到session中
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("roles", roles);
            request.getSession().setAttribute("userLinkOrg", null == userLinkOrg ? "" : userLinkOrg);

            return ret;
        }

        throw new BusinessLogicRunTimeException("登录失败!");
    }

    /**
     * 用户注销
     *
     * @return
     */
    @RequestMapping("logout")
    @ResponseBody
    public Boolean logout(HttpServletRequest request) {

        HttpSession session = request.getSession();

        Enumeration<String> attributeNames = session.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            session.removeAttribute(attributeNames.nextElement());
        }

        logger.info("注销成功");
        return true;
    }

    /**
     * 用户修改密码
     *
     * @param userId 用户ID
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @return Boolean
     */
    @RequestMapping("editPwd")
    @ResponseBody
    public Boolean editPwd(Long userId, String oldPwd, String newPwd) {

        logger.info(String.format("用户修改密码提交参数:userId=%s,oldPwd=%s,newPwd=%s", userId, oldPwd, newPwd));

        if (userService.editPwd(userId, oldPwd, newPwd)) {
            logger.info("密码修改成功");
            return true;
        } else {
            logger.info("密码修改失败");
            return false;
        }
    }

    /**
     * 获取用户关联角色的可用权限
     *
     * @return List
     */
    @RequestMapping(value = "getRolePermissions")
    @ResponseBody
    public List<Map<String, Object>> getRolePermissions(Long roleId) {

        if (null == roleId || roleId <= 0) {
            throw new BusinessLogicRunTimeException("角色id不能为空!");
        }

        return userService.getRolePermissions(roleId);
    }

    /**
     * 保存角色权限
     *
     * @param roleId      角色id
     * @param perMissions 权限设置
     * @return Boolean
     */
    @RequestMapping(value = "saveRolePerMissions")
    @ResponseBody
    public Boolean saveRolePerMissions(Long roleId, String perMissions) {

        List<AccessControl> accessControlList = Common.stringToList(perMissions, AccessControl.class);
        userService.saveRolePermissions(roleId, accessControlList);
        return true;
    }

    /**
     * 获取消息通知
     *
     * @param type 消息类别 （0未处理，1已处理，其他值全部）
     * @return List<Map<String, Object>>
     */
    @RequestMapping(value = "getMessage")
    @ResponseBody
    public Map<String, Object> getMessage(Integer type, @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNo) {

        type = type == null ? -1 : type;

        Map<String, Object> ret = new HashMap<String, Object>(32);

        Integer userRoleType = SessionUtil.getUserRoleType().getNumber();
        Long org = SessionUtil.getUser().getOrg();

        return userService.getMessage(userRoleType, org, type, pageSize, pageNo);

    }

    /**
     * 标记消息为已读
     *
     * @param id 消息id
     * @return Boolean
     */
    @RequestMapping(value = "setMessageProcessed")
    @ResponseBody
    public Boolean setMessageProcessed(Long id) {

        userService.setMessageProcessed(id);
        return true;

    }

    /**
     * 获取登陆时验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "getVerificationCode")
    public void getVerificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);

        ValidateCode validateCode = new ValidateCode();
        try {
            //获取图形验证码信息
            Map<String, Object> verificationCode = validateCode.getRandomCode(130, 50, 20, 5);

            // 将验证码放到session中，用于登陆时验证
            request.getSession().setAttribute(VERIFICATION_CODE_KEY, verificationCode.get("code").toString());
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write((RenderedImage) verificationCode.get("image"), "JPEG", response.getOutputStream());

        } catch (Exception e) {
            logger.error("获取图形验证码错误:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 登录页面校验验证码
     */
    @RequestMapping(value = "checkVerificationCode")
    @ResponseBody
    public Boolean checkVerificationCode(String inputCode, HttpSession session) {
        //从session中获取随机数
        String random = (String) session.getAttribute(VERIFICATION_CODE_KEY);
        return null != random && random.equalsIgnoreCase(inputCode);

    }
}
