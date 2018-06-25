package com.kingdee.hrp.sms.util;

import com.kingdee.hrp.sms.common.model.SystemSetting;
import com.kingdee.hrp.sms.common.service.SystemSettingService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spring容器启动后，初始化系统参数
 *
 * @author Administrator
 */
public final class SystemSettingUtils {

    private static Map<String, SystemSetting> paramsCache = new HashMap<String, SystemSetting>();


    /**
     * 将参数列表放入缓存
     *
     * @param systemSettings 参数集合
     */
    public static void set(List<SystemSetting> systemSettings) {

        if (CollectionUtils.isEmpty(systemSettings)) {
            return;
        }

        for (SystemSetting systemSetting : systemSettings) {
            set(systemSetting);
        }

    }

    /**
     * 将单个参数放入缓存
     *
     * @param systemSetting 参数集合
     */
    public static void set(SystemSetting systemSetting) {

        if (systemSetting == null) {
            return;
        }

        Long org = systemSetting.getOrg();
        String category = systemSetting.getCategory();
        String key = systemSetting.getKey();

        // 缓存key
        String mapKey = String.format("%s_%s_%s", org, category, key);

        paramsCache.put(mapKey, systemSetting);

    }

    /**
     * 获取系统参数值
     * 参数不存在时返回null
     *
     * @param category 参数分类：通常按照模块来区分
     * @param key      参数关键字
     * @return 参数值
     */
    public static Object getValue(String category, String key) {

        SystemSetting systemSetting = getSystemSetting(category, key);

        if (systemSetting != null) {
            return systemSetting.getValue();
        }

        return null;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回null
     *
     * @param category 参数分类：通常按照模块来区分
     * @param key      参数关键字
     * @return SystemSetting 对象
     */
    public static SystemSetting getSystemSetting(String category, String key) {

        Long org = SessionUtil.getUserLinkOrg();
        // 缓存key
        String mapKey = String.format("%s_%s_%s", org, category, key);

        if (paramsCache.containsKey(mapKey)) {
            return paramsCache.get(mapKey);
        }

        SystemSettingService systemSettingService = Environ.getBean(SystemSettingService.class);
        SystemSetting systemSetting = systemSettingService.getSystemSetting(org, category, key);

        if (null != systemSetting) {
            paramsCache.put(mapKey, systemSetting);
            return systemSetting;
        }

        return null;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回null
     *
     * @param category 参数分类：通常按照模块来区分
     * @param key      参数关键字
     * @return Integer
     */
    public static Integer getInt(String category, String key) {

        Object object = getValue(category, key);

        if (object != null) {
            return Integer.valueOf(object.toString());
        }
        return null;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回defaultValue
     *
     * @param category     参数分类：通常按照模块来区分
     * @param key          参数关键字
     * @param defaultValue 默认值
     * @return Integer
     */
    public static Integer getInt(String category, String key, int defaultValue) {

        Object object = getValue(category, key);
        if (object != null) {
            return Integer.valueOf(object.toString());
        }
        return defaultValue;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回null
     *
     * @param category 参数分类：通常按照模块来区分
     * @param key      参数关键字
     * @return Long
     */
    public static Long getLong(String category, String key) {

        Object object = getValue(category, key);
        if (object != null) {
            return Long.valueOf(object.toString());
        }
        return null;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回defaultValue
     *
     * @param category     参数分类：通常按照模块来区分
     * @param key          参数关键字
     * @param defaultValue 默认值
     * @return Long
     */
    public static Long getLong(String category, String key, long defaultValue) {

        Object object = getValue(category, key);
        if (object != null) {
            return Long.valueOf(object.toString());
        }
        return defaultValue;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回null
     *
     * @param category 参数分类：通常按照模块来区分
     * @param key      参数关键字
     * @return String
     */
    public static String getString(String category, String key) {
        Object object = getValue(category, key);
        if (object != null) {
            return object.toString();
        }
        return null;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回defaultValue
     *
     * @param category     参数分类：通常按照模块来区分
     * @param key          参数关键字
     * @param defaultValue 默认值
     * @return String
     */
    public static String getString(String category, String key, String defaultValue) {
        Object object = getValue(category, key);
        if (object != null) {
            return object.toString();
        }
        return defaultValue;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回null
     *
     * @param category 参数分类：通常按照模块来区分
     * @param key      参数关键字
     * @return Float
     */
    public static Float getFloat(String category, String key) {
        Object object = getValue(category, key);
        if (object != null) {
            return Float.valueOf(object.toString());
        }
        return null;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回defaultValue
     *
     * @param category     参数分类：通常按照模块来区分
     * @param key          参数关键字
     * @param defaultValue 默认值
     * @return Float
     */
    public static Float getFloat(String category, String key, float defaultValue) {
        Object object = getValue(category, key);
        if (object != null) {
            return Float.valueOf(object.toString());
        }
        return defaultValue;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回null
     *
     * @param category 参数分类：通常按照模块来区分
     * @param key      参数关键字
     * @return Double
     */
    public static Double getDouble(String category, String key) {
        Object object = getValue(category, key);
        if (object != null) {
            return Double.valueOf(object.toString());
        }
        return null;
    }

    /**
     * 获取系统参数值
     * 参数不存在时返回defaultValue
     *
     * @param category     参数分类：通常按照模块来区分
     * @param key          参数关键字
     * @param defaultValue 默认值
     * @return Double
     */
    public static Double getDouble(String category, String key, double defaultValue) {
        Object object = getValue(category, key);
        if (object != null) {
            return Double.valueOf(object.toString());
        }
        return defaultValue;
    }

}
