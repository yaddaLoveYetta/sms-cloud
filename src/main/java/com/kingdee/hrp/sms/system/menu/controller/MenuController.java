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

}
