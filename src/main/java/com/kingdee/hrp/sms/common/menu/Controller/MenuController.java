package com.kingdee.hrp.sms.common.menu.Controller;

import com.kingdee.hrp.sms.common.menu.model.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/16
 */
@Controller(value = "/manager/menu/")
public class MenuController {

    @RequestMapping(value = "getMenu")
    @ResponseBody
    public List<Menu> getMenu(Integer parentId) {
        return null;
    }
}
