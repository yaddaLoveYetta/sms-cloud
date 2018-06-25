package com.kingdee.hrp.sms.common.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.SystemSettingMapper;
import com.kingdee.hrp.sms.common.model.SystemSetting;
import com.kingdee.hrp.sms.common.model.SystemSettingExample;
import com.kingdee.hrp.sms.common.model.SystemSettingKey;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.common.service.SystemSettingService;
import com.kingdee.hrp.sms.util.SystemSettingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author le.xiao
 */
@Service
public class SystemSettingServiceImpl extends BaseService implements SystemSettingService {


    private static Logger logger = LoggerFactory.getLogger(SystemSettingServiceImpl.class);


    /**
     * 系统启动后将所有系统参数放入缓存中
     */
    @PostConstruct
    private void init() {

        logger.info("查询所有系统参数放入缓存开始");

        SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);

        List<SystemSetting> systemSettings = systemSettingMapper.selectByExample(null);

        // 放入工具类的缓存中
        SystemSettingUtils.set(systemSettings);

        logger.info("查询所有系统参数放入缓存结束");
    }

    /**
     * 获取用户所有系统参数
     *
     * @param org 归属组织
     * @return List<SystemSetting>
     */
    @Override
    public List<SystemSetting> getAllSystemSetting(Long org) {

        logger.info("查询所有参数开始");
        SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);
        SystemSettingExample example = new SystemSettingExample();

        example.createCriteria().andOrgEqualTo(org).example().orderBy(SystemSetting.Column.index.asc());

        List<SystemSetting> systemSettings = systemSettingMapper.selectByExample(example);


        logger.info("查询所有参数结束");

        return systemSettings;
    }

    /**
     * 获取单个设置项
     * 参数不存在时返回null
     *
     * @param org      归属组织
     * @param category 类别
     * @param key      参数名
     * @return SystemSetting
     */
    @Override
    public SystemSetting getSystemSetting(Long org, String category, String key) {

        SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);
        SystemSettingExample example = new SystemSettingExample();

        example.createCriteria().andOrgEqualTo(org).andCategoryEqualTo(category).andKeyEqualTo(key);

        List<SystemSetting> systemSettings = systemSettingMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(systemSettings)) {
            // 只可能有一个
            return systemSettings.get(0);
        }

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
    public void save(Long org, String category, String key, String value) {

        SystemSetting systemSetting = new SystemSetting();

        systemSetting.setOrg(org);
        systemSetting.setCategory(category);
        systemSetting.setKey(key);

        systemSetting.setValue(value);

        SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);

        systemSettingMapper.updateByPrimaryKeySelective(systemSetting);

        SystemSettingKey systemSettingKey = new SystemSettingKey();

        systemSettingKey.setOrg(org);
        systemSettingKey.setCategory(category);
        systemSettingKey.setKey(key);

        // 查出完整参数信息-放入工具类缓存中
        systemSetting = systemSettingMapper.selectByPrimaryKey(systemSettingKey);

        logger.info(String.format("更新参数缓存：", systemSetting));
        SystemSettingUtils.set(systemSetting);

    }
}
