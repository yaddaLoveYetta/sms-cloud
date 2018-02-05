package com.kingdee.hrp.sms.basedata.controller;

import com.kingdee.hrp.sms.basedata.model.UserEnum;
import com.kingdee.hrp.sms.common.menu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户控制器
 *
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/24
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验注册数据是否合法
     * @param param  需要校验的数据 0是用户手机号码，1是用户登陆账号名
     * @param type   数据的类型
     * @return
     */
    @RequestMapping(value = "/check?param=0&type=1", method = RequestMethod.GET)
    public ResponseEntity<String> check(@PathVariable("param") String param,
                                        @PathVariable("type") Integer type) {
        String result = "false";
        try {
            if(0 < type && type < 2) {
                Boolean bool = userService.check(param, type);
                result = bool.toString();
                return ResponseEntity.ok(result);
            } else {//数据不合法
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }


    /**
     * 用户登录
     *
     * @param username
     * @param password
     */
    @RequestMapping("/login")
    public void login( String username,@RequestParam(value="password") String password) {

    }

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @param userType
     */
    public void register(String username, String password, UserEnum userType) {

    }
}
