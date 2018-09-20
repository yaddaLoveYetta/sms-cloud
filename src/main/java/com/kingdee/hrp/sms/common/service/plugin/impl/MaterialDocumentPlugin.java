package com.kingdee.hrp.sms.common.service.plugin.impl;

import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;
import com.kingdee.hrp.sms.common.service.plugin.AbstractPlugInAdapter;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 物料证件插件
 *
 * @author le.xiao
 */
@Component
public class MaterialDocumentPlugin extends AbstractPlugInAdapter implements InitializingBean {

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
        return "供应商资质插件";
    }

    /**
     * 插件说明</br>
     * 简单介绍下插件做了什么事情
     *
     * @return 插件说明
     */
    @Override
    public String description() {
        return "供应商资质插件";
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
        // 物料证件类别
        classIdSet.add(Constants.ClassType.MATERIAL_DOCUMENT_TYPE.classId());
        // 物料证件
        classIdSet.add(Constants.ClassType.MATERIAL_DOCUMENT.classId());
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

        if (classId != Constants.ClassType.MATERIAL_DOCUMENT.classId() &&
                classId != Constants.ClassType.MATERIAL_DOCUMENT_TYPE.classId()) {
            // 插件不支持该业务类别
            return super.getConditions(classId, formTemplate, conditions);
        }

        if (SessionUtil.getUserRoleType() == Constants.UserRoleType.HOSPITAL) {
            // 医院只能查看本院的
            Condition condition = new Condition();
            condition.setLinkType(Condition.LinkType.AND)
                    .setFieldKey("hospital")
                    .setLogicOperator(Condition.LogicOperator.EQUAL)
                    .setValue(SessionUtil.getUserLinkHospital())
                    .setNeedConvert(false);
            ret.add(condition);
        }

        return ret;
    }
}
