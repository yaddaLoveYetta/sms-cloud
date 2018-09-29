package com.kingdee.hrp.sms.system.user.service;

import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.pojo.RegisterModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/2/5
 */
public interface UserService extends UserInfo {

    /**
     * 用户注册
     *
     * @param registerModel 用户注册信息
     */
    void register(RegisterModel registerModel) throws IOException;

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码 (MD5)
     * @return User
     */
    User login(String userName, String password);

    /**
     * 用户修改密码
     *
     * @param userId 用户ID
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @return boolean
     */
    Boolean editPwd(Long userId, String oldPwd, String newPwd);

    /**
     * 获取角色所有权限
     *
     * @param roleId 角色id
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getRolePermissions(Long roleId);

    /**
     * 根据角色id获取授权结果
     *
     * @param roleId 角色id
     * @return Map<Integer, AccessControl>
     */
    Map<Integer, AccessControl> getAccessControl(Long roleId);

    /**
     * 获取用户所有权限集合（用户有多个角色时将权限做和，||运算）
     *
     * @return 权限授权结果
     */
    Map<Integer, Integer> getAccessControl();

    /**
     * 保存角色权限
     *
     * @param roleId            角色id
     * @param accessControlList 角色授权结果
     */
    void saveRolePermissions(Long roleId, List<AccessControl> accessControlList);

    /**
     * 获取消息通知
     *
     * @param userRoleType 角色类别
     * @param org          组织id
     * @param type         消息类别  （0未处理，1已处理，其他值全部）
     * @param pageSize     分页大小
     * @param pageNo       当前页码
     * @return List<Message>
     */
    Map<String, Object> getMessage(Integer userRoleType, Long org, Integer type, Integer pageSize, Integer pageNo);

    /**
     * 设置消息为已读状态
     *
     * @param id 消息id
     */
    void setMessageProcessed(Long id);
}
