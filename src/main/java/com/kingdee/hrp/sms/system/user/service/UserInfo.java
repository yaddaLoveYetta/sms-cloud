package com.kingdee.hrp.sms.system.user.service;

import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.model.Hospital;
import com.kingdee.hrp.sms.common.model.Role;
import com.kingdee.hrp.sms.common.model.Supplier;

import java.io.Serializable;
import java.util.List;

/**
 * 用户个人信息相关服务
 *
 * @author yadda
 */
public interface UserInfo extends CurrentUserInfo {

    /**
     * 获取用户的角色
     *
     * @param userId id
     * @return 拥有的所有角色List<Role>
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
}
