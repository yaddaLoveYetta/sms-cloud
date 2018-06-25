package com.kingdee.hrp.sms.common.service;

import com.kingdee.hrp.sms.common.model.SystemSetting;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author le.xiao
 */
public interface SystemSettingService {
    /**
     * 获取用户所有系统参数
     *
     * @param org 归属组织
     * @return List<SystemSetting>
     */
    List<SystemSetting> getAllSystemSetting(Long org);

    /**
     * 获取单个设置项
     *
     * @param org      归属组织
     * @param category 类别
     * @param key      参数名
     * @return SystemSetting
     */
    SystemSetting getSystemSetting(Long org, String category, String key);

    /**
     * 修改参数值
     *
     * @param org      归属组织
     * @param category 类别
     * @param key      参数名
     * @param value    参数值
     */
    void edit(Long org, String category, String key, String value);
}
