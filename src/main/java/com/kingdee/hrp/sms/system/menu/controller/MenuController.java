package com.kingdee.hrp.sms.system.menu.controller;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.system.menu.service.IMenuService;
import com.kingdee.hrp.sms.common.model.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/16
 */
@Controller
@RequestMapping(value = "/menu/")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @RequestMapping(value = "getMenu")
    @ResponseBody
    public List<Menu> getMenu(Integer parentId) {
        return menuService.getMenusByParentId(parentId);
    }

    @ResponseBody
    @RequestMapping(value = "a")
    public void a() {
        return;
    }

    @ResponseBody
    @RequestMapping(value = "b")
    public String b() {
        return new String("hello word");
    }

    @ResponseBody
    @RequestMapping(value = "c")
    public Map c() {
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "d")
    public String d(HttpServletRequest request) {

        System.out.println(request.getParameter("parentId"));

        return "parentId=" + request.getParameter("parentId");
    }

    @ResponseBody
    @RequestMapping(value = "e")
    public String e(HttpServletRequest request) {

        throw new BusinessLogicRunTimeException("业务处理异常");
    }
}
