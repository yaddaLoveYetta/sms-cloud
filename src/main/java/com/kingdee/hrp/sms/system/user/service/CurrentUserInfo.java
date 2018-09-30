package com.kingdee.hrp.sms.system.user.service;

import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.model.Role;
import com.kingdee.hrp.sms.util.SessionUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 当前用户的信息
 *
 * @author yadda
 */
public interface CurrentUserInfo extends Serializable {

    /**
     * 获取当前用户关联的组织id(供应商/医院资料内码，系统用户返回0)
     *
     * @return Long
     */
    default Long getCurrentUserLinkOrg() {

        Constants.UserRoleType userRoleType = SessionUtil.getUserRoleType();
        if (userRoleType == Constants.UserRoleType.HOSPITAL) {
            //医院角色
            return SessionUtil.getUserLinkHospital();
        } else if (userRoleType == Constants.UserRoleType.SUPPLIER) {
            //供应商角色
            return SessionUtil.getUserLinkSupplier();
        }
        return 0L;
    }

    /**
     * 获取当前用户的角色类别（1: 系统角色 2: 医院角色 3: 供应商角色）
     *
     * @return Integer
     */
    default Constants.UserRoleType getCurrentUserRoleType() {
        return SessionUtil.getUserRoleType();
    }

    /**
     * 获取当前用户的角色
     *
     * @return List<Role>
     */
    default List<Role> getCurrentUserRole() {
        return SessionUtil.getUserRole();
    }

    /**
     * 判断当前用户是否有指定角色
     *
     * @param id 角色id
     * @return boolean
     */
    default boolean isRole(Long id) {
        return SessionUtil.getUserRole().stream().anyMatch(t -> Objects.equals(t.getId(), id));
    }

    /**
     * 判断当前用户是否有指定角色
     *
     * @param role 角色
     * @return boolean
     */
    default boolean isRole(Role role) {
        return SessionUtil.getUserRole().stream().anyMatch(t -> Objects.equals(t.getId(), role.getId()));
    }

}
