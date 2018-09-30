package com.kingdee.hrp.sms.basedata.service.plugin.impl;

import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;
import com.kingdee.hrp.sms.common.service.plugin.AbstractPlugInAdapter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 医院合作供应商插件
 *
 * @author yadda
 */
@Component
public class PartnerHospitalPlugin extends AbstractPlugInAdapter implements InitializingBean {

    /**
     * 插件支持的业务类别id集合，推荐的做法是一个插件只支持一个业务类别
     * 如只支持订单业务
     */
    private Set<Integer> classIdSet;

    /**
     * 插件名称
     *
     * @return 插件名称
     */
    @Override
    public String name() {
        return "医院合作供应商插件";
    }

    /**
     * 插件说明</br>
     * 简单介绍下插件做了什么事情
     *
     * @return 插件说明
     */
    @Override
    public String description() {
        return "医院合作供应商插件";
    }

    /**
     * 插件序号-同一个业务上绑定多插件时确定插件的执行顺序
     *
     * @return 插件序号，值越小越先执行
     */
    @Override
    public Integer index() {
        return 1;
    }

    /**
     * 获取当前插件支持的业务类型集合
     *
     * @return 插件支持的业务类型classId集合
     */
    @Override
    public Set<Integer> getSupports() {
        return classIdSet;
    }

    /**
     * 插件是否支持指定的业务单据
     *
     * @param classId 业务单据类型
     * @return true if support , false not support
     */
    @Override
    public boolean support(Integer classId) {
        return classIdSet.contains(classId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        classIdSet = new HashSet<>();
        // 供应商证件
        classIdSet.add(Constants.ClassType.PARTNER_HOSPITAL.classId());

    }

    /**
     * 查询前插件查询条件
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditions   原始过滤条件
     * @return 插件过滤条件
     */
    @Override
    public List<Condition> getConditions(int classId, FormTemplate formTemplate, List<Condition> conditions) {

        List<Condition> ret = new ArrayList<>();

        Constants.UserRoleType userRoleType = getCurrentUserRoleType();
        Long linkOrg = getCurrentUserLinkOrg();

        if (userRoleType == Constants.UserRoleType.SYSTEM) {
            // 系统角色类别放开所有数据查看权限
            return ret;
        } else if (userRoleType == Constants.UserRoleType.HOSPITAL) {
            // 当前是医院角色的用户在操作
            ret.add(new Condition().setLinkType(Condition.LinkType.AND).setFieldKey("org")
                    .setLogicOperator(Condition.LogicOperator.EQUAL).setValue(linkOrg).setNeedConvert(false));

            ret.add(new Condition().setFieldKey("type").setLogicOperator(Condition.LogicOperator.EQUAL).setValue(0));

        } else {
            throw new BusinessLogicRunTimeException("你无权查看");
        }

        return ret;

    }
}
