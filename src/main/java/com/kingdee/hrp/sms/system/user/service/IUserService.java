package com.kingdee.hrp.sms.system.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kingdee.hrp.sms.common.model.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/2/5
 */
public interface IUserService extends Serializable {

    /**
     * 用户注册
     *
     * @param registerInfo 用户注册信息
     */
    void register(Map<String, Object> registerInfo) throws JsonProcessingException, IOException;

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码 (MD5)
     * @return User
     */
    User login(String userName, String password);

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    List<Role> getUserRole(Long userId);

    /**
     * 获取当前用户关联的医院信息
     *
     * @param id 当前用户所属医院id
     * @return Hospital
     */
    Hospital getUserLinkHospital(Long id);

    /**
     * 获取当前用户关联的供应商信息
     *
     * @param id 当前用户所属供应商id
     * @return Supplier
     */
    Supplier getUserLinkSupplier(Long id);

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
     * @param status      消息状态 （0未处理，1已处理，其他值全部）
     * @return List<Message>
     */
    Map<String, Object> getMessage(Integer userRoleType, Long org, Integer status);
}
