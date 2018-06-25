package com.kingdee.hrp.sms.common.controller;

import com.kingdee.hrp.sms.common.model.SystemSetting;
import com.kingdee.hrp.sms.util.SessionUtil;

import java.util.List;

/**
 * 系统参数
 *
 * @author le.xiao
 */
public class SystemSettingController {

    /**
     * 获取用户所有系统参数
     *
     * @return List<SystemSetting>
     */
    public List<SystemSetting> getAllSystemSetting() {

        SessionUtil.getUser().getOrg();
        return null;
    }

    /**
     * 获取单个设置项
     *
     * @param org      归属组织
     * @param category 类别
     * @param key      参数名
     * @return SystemSetting
     */
    public SystemSetting getSystemSetting(Long org, String category, String key) {

        return null;
    }

    /**
     * 修改参数值
     *
     * @param org      归属组织
     * @param category 类别
     * @param key      参数名
     * @param value    参数值
     */
    public void edit(Long org, String category, String key, String value) {

    }
}
