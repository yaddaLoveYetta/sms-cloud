package com.kingdee.hrp.sms.common.controller;

import com.kingdee.hrp.sms.common.model.SystemSetting;
import com.kingdee.hrp.sms.common.service.SystemSettingService;
import com.kingdee.hrp.sms.util.SessionUtil;
import com.kingdee.hrp.sms.util.SystemSettingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统参数
 *
 * @author le.xiao
 */
@Controller
@RequestMapping(value = "/setting/")
public class SystemSettingController {

    private static Logger logger = LoggerFactory.getLogger(SystemSettingController.class);
    @Resource
    private SystemSettingService systemSettingService;

    /**
     * 获取用户所有系统参数
     *
     * @return List<SystemSetting>
     */
    @RequestMapping(value = "getAll")
    @ResponseBody
    public List<SystemSetting> getAllSystemSetting() {

        logger.info("查询系统参数");

        return systemSettingService.getAllSystemSetting(SessionUtil.getUser().getOrg());

    }

    /**
     * 获取单个设置项
     *
     * @param category 类别
     * @param key      参数名
     * @return SystemSetting
     */
    @RequestMapping(value = "get")
    @ResponseBody
    public SystemSetting getSystemSetting(String category, String key) {

        logger.info(String.format("查询单个系统参数category=%s,key=%s", category, key));
        // 走缓存查
        return SystemSettingUtils.getSystemSetting(category, key);

    }

    /**
     * 修改参数值
     *
     * @param category 类别
     * @param key      参数名
     * @param value    参数值
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public void save(String category, String key, String value) {
        logger.info(String.format("修改参数值category=%s,key=%s,value=%s", category, key, value));
        systemSettingService.save(SessionUtil.getUser().getOrg(), category, key, value);
    }
}
