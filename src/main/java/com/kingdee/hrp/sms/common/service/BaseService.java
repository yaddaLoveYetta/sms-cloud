package com.kingdee.hrp.sms.common.service;

import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.common.pojo.BillOperateType;
import com.kingdee.hrp.sms.common.pojo.DisplayType;
import com.kingdee.hrp.sms.common.pojo.MustInputType;
import com.kingdee.hrp.sms.util.SessionUtil;
import com.kingdee.hrp.sms.util.SnowFlake;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yadda
 */
@Service
public abstract class BaseService {

    private static Logger logger = LoggerFactory.getLogger(BaseService.class);

    @Resource
    protected SqlSession sqlSession;

    /**
     * 新增记录主键生成策略
     * <p>
     * SnowFlake算法，会产生一个long类型不重复数字
     *
     * @return
     */
    protected Long getId() {
        return SnowFlake.getId(0, 0);
    }

    /**
     * 获取字段模板显示性控制针对当前用户角色类别的掩码
     *
     * @param operateType 操作类型 1：查看2：新增3：编辑 null：全部(查看|新增|编辑)
     * @return 显示控制性掩码
     */
    protected Integer getCurrentDisplayMask(BillOperateType operateType) {

        UserRoleType userRoleType = SessionUtil.getUserRoleType();

        switch (userRoleType) {
        case SYSTEM:
            if (operateType == BillOperateType.VIEW) {
                return DisplayType.VIEW_SYSTEM_SHOW.value();
            } else if (operateType == BillOperateType.ADD) {
                return DisplayType.ADD_SYSTEM_SHOW.value();
            } else if (operateType == BillOperateType.EDIT) {
                return DisplayType.EDIT_SYSTEM_SHOW.value();
            } else {
                return DisplayType.VIEW_SYSTEM_SHOW.value() | DisplayType.ADD_SYSTEM_SHOW.value() |
                        DisplayType.EDIT_SYSTEM_SHOW.value();
            }
        case HOSPITAL:
            if (operateType == BillOperateType.VIEW) {
                return DisplayType.VIEW_HOSPITAL_SHOW.value();
            } else if (operateType == BillOperateType.ADD) {
                return DisplayType.ADD_HOSPITAL_SHOW.value();
            } else if (operateType == BillOperateType.EDIT) {
                return DisplayType.EDIT_HOSPITAL_SHOW.value();
            } else {
                return DisplayType.VIEW_HOSPITAL_SHOW.value() | DisplayType.ADD_HOSPITAL_SHOW.value() |
                        DisplayType.EDIT_HOSPITAL_SHOW.value();
            }
        case SUPPLIER:
            if (operateType == BillOperateType.VIEW) {
                return DisplayType.VIEW_SUPPLIER_SHOW.value();
            } else if (operateType == BillOperateType.ADD) {
                return DisplayType.ADD_SUPPLIER_SHOW.value();
            } else if (operateType == BillOperateType.EDIT) {
                return DisplayType.EDIT_SUPPLIER_SHOW.value();
            } else {
                return DisplayType.VIEW_SUPPLIER_SHOW.value() | DisplayType.ADD_SUPPLIER_SHOW.value() |
                        DisplayType.EDIT_SUPPLIER_SHOW.value();
            }
        default:
        }

        int maskAll = DisplayType.VIEW_SYSTEM_SHOW.value() | DisplayType.ADD_SYSTEM_SHOW.value() |
                DisplayType.EDIT_SYSTEM_SHOW.value() | DisplayType.VIEW_HOSPITAL_SHOW.value() |
                DisplayType.ADD_HOSPITAL_SHOW.value() | DisplayType.EDIT_HOSPITAL_SHOW.value() |
                DisplayType.VIEW_SUPPLIER_SHOW.value() | DisplayType.ADD_SUPPLIER_SHOW.value() |
                DisplayType.EDIT_SUPPLIER_SHOW.value();
        return maskAll;

    }

    /**
     * 获取字段模板必录性控制针对当前用户角色类别的掩码
     *
     * @param operateType 操作类型 2：新增3：编辑 null：全部(查看|新增|编辑)
     * @return 必录性控制掩码
     */
    protected Integer getCurrentMustInputMask(BillOperateType operateType) {

        UserRoleType userRoleType = SessionUtil.getUserRoleType();

        switch (userRoleType) {
        case SYSTEM:
            if (operateType == BillOperateType.ADD) {
                return MustInputType.ADD_SYSTEM_MUST.value();
            } else if (operateType == BillOperateType.EDIT) {
                return MustInputType.EDIT_SYSTEM_MUST.value();
            } else {
                return MustInputType.ADD_SYSTEM_MUST.value() | MustInputType.EDIT_SYSTEM_MUST.value();
            }
        case HOSPITAL:
            if (operateType == BillOperateType.ADD) {
                return MustInputType.ADD_HOSPITAL_MUST.value();
            } else if (operateType == BillOperateType.EDIT) {
                return MustInputType.EDIT_HOSPITAL_MUST.value();
            } else {
                return MustInputType.ADD_HOSPITAL_MUST.value() | MustInputType.EDIT_HOSPITAL_MUST.value();
            }
        case SUPPLIER:
            if (operateType == BillOperateType.ADD) {
                return MustInputType.ADD_SUPPLIER_MUST.value();
            } else if (operateType == BillOperateType.EDIT) {
                return MustInputType.EDIT_SUPPLIER_MUST.value();
            } else {
                return MustInputType.ADD_SUPPLIER_MUST.value() | MustInputType.EDIT_SUPPLIER_MUST.value();
            }
        default:
        }

        int maskAll = MustInputType.ADD_SYSTEM_MUST.value() | MustInputType.EDIT_SYSTEM_MUST.value() |
                MustInputType.ADD_HOSPITAL_MUST.value() | MustInputType.EDIT_HOSPITAL_MUST.value() |
                MustInputType.ADD_SUPPLIER_MUST.value() | MustInputType.EDIT_SUPPLIER_MUST.value();

        return maskAll;

    }

}
