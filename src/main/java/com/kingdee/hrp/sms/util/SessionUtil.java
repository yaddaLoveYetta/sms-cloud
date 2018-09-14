package com.kingdee.hrp.sms.util;

import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.SessionLostRuntimeException;
import com.kingdee.hrp.sms.common.model.Role;
import com.kingdee.hrp.sms.common.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yadda
 */
public final class SessionUtil {

    /**
     * InheritableThreadLocal,多线程时子线程可访问主线程ThreadLocal
     */
    private static InheritableThreadLocal<Map<String, Object>> LOCAL = new InheritableThreadLocal<Map<String, Object>>();

    /**
     * 新增
     *
     * @param key
     * @param value
     * @return void
     * @date 2017-05-30 01:03:36 星期二
     */
    public static void set(String key, Object value) {

        Map<String, Object> map = LOCAL.get();

        if (map == null) {
            map = new HashMap<String, Object>(16);
            LOCAL.set(map);
        }

        map.put(key, value);
    }

    /**
     * 删除
     *
     * @param key
     * @return void
     * @date 2017-05-30 01:03:05 星期二
     */
    public static void remove(String key) {

        Map<String, Object> map = LOCAL.get();

        if (map == null) {
            return;
        }

        map.remove(key);
    }

    /**
     * 获取
     *
     * @param key
     * @return Object
     * @date 2017-05-30 01:03:50 星期二
     */
    public static Object get(String key) {

        Map<String, Object> map = (Map<String, Object>) LOCAL.get();

        if (null == map) {
            return null;
        }

        return map.get(key);

    }

    /**
     * 获得线程中保存的属性，使用指定类型进行转型.
     *
     * @param key   属性名称
     * @param clazz 类型
     * @param <T>   自动转型
     * @return 属性值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        return (T) get(key);
    }

    /**
     * 获取当前线程用户-同session中保存的用户
     *
     * @return User
     * @date 2017-05-30 01:04:05 星期二
     */
    public static User getUser() {

        Object object = get("user");

        if (null == object) {
            throw new SessionLostRuntimeException("用户未登录，请重新登录！");
        }

        return (User) object;

    }

    /**
     * 获取当前线程用户角色列表
     * 一个用户可能有多个角色
     *
     * @return Long
     * @date 2017-05-30 01:04:05 星期二
     */
    public static List<Role> getUserRole() {

        Object object = get("roles");

        if (null == object) {
            throw new SessionLostRuntimeException("用户未登录，请重新登录！");
        }

        return (List<Role>) object;

    }

    /**
     * 获取当前线程用户角色类别
     * 一个用户可能有多个角色，但多个角色都对应同一个类别(1: 系统角色 2: 医院角色 3: 供应商角色)
     * 且归属于同一家供应商/医院
     *
     * @return 用户角色类别
     */

    public static Constants.UserRoleType getUserRoleType() {

        Object object = get("roles");

        if (null == object) {
            throw new SessionLostRuntimeException("用户未登录，请重新登录！");
        }
        List<Role> roles = (List<Role>) object;

        Integer type = roles.get(0).getType();

        return Constants.UserRoleType.getUserRoleType(type);

    }

    /**
     * 获取当前线程用户角色类别代码
     * 一个用户可能有多个角色，但多个角色都对应同一个类别(1: 系统角色 2: 医院角色 3: 供应商角色)
     * 且归属于同一家供应商/医院
     *
     * @return 用户角色类别代码
     */
    public static Integer getUserRoleTypeNumber() {
        return getUserRoleType().getNumber();
    }

    /**
     * 获取用户关联的供应商
     * <p>
     * 如果该用户所述角色不是供应商角色，返回-1
     *
     * @return String
     * @date 2017-06-02 17:37:44 星期五
     */
    public static Long getUserLinkSupplier() {
        // 1: 系统角色 2: 医院角色 3: 供应商角色
        if (getUserRoleType() != Constants.UserRoleType.SUPPLIER) {
            return -1L;
        }
        return getUserRole().get(0).getOrg();
    }

    /**
     * 获取用户关联的医院内码
     * <p>
     * 如果该用户所述角色不是供应商角色，返回-1
     *
     * @return String
     * @date 2017-06-02 17:37:44 星期五
     */
    public static Long getUserLinkHospital() {
        // 1: 系统角色 2: 医院角色 3: 供应商角色
        if (getUserRoleType() != Constants.UserRoleType.HOSPITAL) {
            return -1L;
        }
        return getUserRole().get(0).getOrg();
    }

    /**
     * 获取当前线程用户名
     *
     * @return String
     * @date 2017-05-30 01:05:11 星期二
     */
    public static String getUserName() {

        return getUser().getName();

    }

    /**
     * 获取当前线程用户id
     *
     * @return Long
     * @date 2017-05-30 01:05:24 星期二
     */
    public static Long getUserId() {

        return getUser().getId();

    }

    /**
     * 获取当前用户所属组织
     *
     * @return
     */
    public static Long getUserLinkOrg() {
        return getUser().getOrg();
    }

}
