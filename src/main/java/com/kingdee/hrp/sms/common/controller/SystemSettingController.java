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
     * @return
     */
    public List<SystemSetting> getSystemSetting() {

        SessionUtil.getUser().getOrg();
        return null;
    }
}
