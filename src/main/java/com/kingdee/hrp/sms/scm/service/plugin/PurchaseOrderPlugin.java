package com.kingdee.hrp.sms.scm.service.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.enums.ClassType;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;
import com.kingdee.hrp.sms.common.service.plugin.AbstractPlugInAdapter;
import com.kingdee.hrp.sms.common.service.plugin.PlugInRet;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 采购订单插件
 *
 * @author yadda
 */
@Component
public class PurchaseOrderPlugin extends AbstractPlugInAdapter implements InitializingBean {

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
        // 订单
        classIdSet.add(ClassType.PURCHASE_ORDER.classId());

    }

    /**
     * 基础资料新增前操作
     *
     * @param classId      业务类型
     * @param formTemplate 单据模板
     * @param data         业务数据
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeSave(int classId, FormTemplate formTemplate, JsonNode data) {

        if (classId != ClassType.PURCHASE_ORDER.classId()) {
            return super.beforeSave(classId, formTemplate, data);
        }

        if (SessionUtil.getUserRoleType() != UserRoleType.HOSPITAL) {
            throw new BusinessLogicRunTimeException("非医院用户不能新增订单");
        }

        // 订单新增时设置订单所属医院
        ((ObjectNode) data).put("hospital", SessionUtil.getUserLinkHospital());

        // 分录数据
        JsonNode entryData = data.path("entry").path("1");

        // 订单新增时设置行号
        for (int i = 0; i < entryData.size(); i++) {
            // 一条待操作分录数据
            JsonNode entry = entryData.path(i).path("data");
            // 设置自然顺序行号
            ((ObjectNode) entry).put("sequence", i + 1);
        }

        return super.beforeSave(classId, formTemplate, data);
    }

    /**
     * 基础资料修改前操作
     *
     * @param classId      业务类型
     * @param id           单据内码
     * @param formTemplate 模板
     * @param data         业务数据
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeModify(int classId, Long id, FormTemplate formTemplate, JsonNode data) {

        if (classId != ClassType.PURCHASE_ORDER.classId()) {
            return super.beforeSave(classId, formTemplate, data);
        }
        if (SessionUtil.getUserRoleType() != UserRoleType.HOSPITAL) {
            throw new BusinessLogicRunTimeException("非医院用户不能新增订单");
        }

        // 分录数据
        JsonNode entryData = data.path("entry").path("1");

        // 修改订单时新增或删除了分录需要重新设置行号(行号重排，保持自然有序)，单据关联用id，行号可以重排(原设计单据关联用行号，必须保证行号不改变)
        for (int i = 0; i < entryData.size(); i++) {
            // 一条待操作分录数据
            JsonNode entry = entryData.path(i).path("data");
            // 设置自然顺序行号
            ((ObjectNode) entry).put("sequence", i + 1);
        }

        return super.beforeModify(classId, id, formTemplate, data);
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
