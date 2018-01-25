package com.kingdee.hrp.sms.common.menu.controller;

import com.kingdee.hrp.sms.common.menu.model.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/16
 */
@Controller
@RequestMapping(value = "/menu/")
public class MenuController {

    @RequestMapping(value = "getMenu")
    @ResponseBody
    public List<Map<String, Object>> getMenu(Integer parentId) {

        List<Map<String, Object>> menus = new ArrayList<>();

        Map<String, Object> item1 = new LinkedHashMap<>();


        for (int i = 0; i < 5; i++) {

            Map<String, Object> item = new LinkedHashMap<>();

            item.put("id", 1);
            item.put("name", "证件管理");
            item.put("index", 0);
            item.put("icon", "icon-fenxiang");
            item.put("url", "");
            item.put("parentId", i > 0 ? 1 : 0);
            item.put("desc", "证件管理xxx");
            item.put("visible", 1);

            menus.add(item);
        }

        return menus;
    }
}
