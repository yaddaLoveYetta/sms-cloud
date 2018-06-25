package com.kingdee.hrp.sms.common.service.impl;

import com.kingdee.hrp.sms.common.model.SystemSetting;
import com.kingdee.hrp.sms.common.service.SystemSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author le.xiao
 */
@Service
public class SystemSettingServiceImpl implements SystemSettingService {
    /**
     * 获取用户所有系统参数
     *
     * @param org 归属组织
     * @return List<SystemSetting>
     */
    @Override
    public List<SystemSetting> getAllSystemSetting(Long org) {
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
    @Override
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(Long org, String category, String key, String value) {

    }
}
