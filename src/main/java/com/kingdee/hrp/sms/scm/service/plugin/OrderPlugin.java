package com.kingdee.hrp.sms.scm.service.plugin;

import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.service.plugin.AbstractPlugInAdapter;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;

/**
 * 订单插件
 *
 * @author yadda
 */
public class OrderPlugin extends AbstractPlugInAdapter implements InitializingBean {

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
        return "订单插件";
    }

    /**
     * 插件说明</br>
     * 简单介绍下插件做了什么事情
     *
     * @return 插件说明
     */
    @Override
    public String description() {
        return "供应商平台订单业务功能插件";
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
    public Set<Integer> getClassIdSet() {
        return classIdSet;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        classIdSet = new HashSet<>();
        // 订单
        classIdSet.add(2001);

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
    public List<Condition> getConditions(int classId, Map<String, Object> formTemplate, List<Condition> conditions) {

        List<Condition> ret = new ArrayList<>();

        UserRoleType userRoleType = getUserRoleType();
        Long linkOrg = getUserLinkOrg();

        if (userRoleType == UserRoleType.SYSTEM) {
            // 系统角色类别放开所有数据查看权限
            return ret;
        } else if (userRoleType == UserRoleType.HOSPITAL) {
            // 医院只能查看本院的订单
            Condition condition = new Condition();
            condition.setLinkType(Condition.LinkType.AND);
            condition.setFieldKey("hospital");
            condition.setLogicOperator(Condition.LogicOperator.EQUAL);
            condition.setValue(linkOrg);
            condition.setNeedConvert(false);
            ret.add(condition);

        } else if (userRoleType == UserRoleType.SUPPLIER) {
            //  供应商只能查看医院发送给自己的订单
            Condition condition = new Condition();
            condition.setLinkType(Condition.LinkType.AND);
            condition.setFieldKey("supplier");
            condition.setLogicOperator(Condition.LogicOperator.EQUAL);
            condition.setValue(linkOrg);
            condition.setNeedConvert(false);
            ret.add(condition);
        }

        return ret;
    }
}
