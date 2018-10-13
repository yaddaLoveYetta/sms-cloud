package com.kingdee.hrp.sms.basedata.service.plugin.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;
import com.kingdee.hrp.sms.common.service.plugin.AbstractPlugInAdapter;
import com.kingdee.hrp.sms.common.service.plugin.PlugInRet;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 供应商维护的证件类别插件
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/10/13 13:57
 */
@Component
public class SupplierQualificationTypePlugin extends AbstractPlugInAdapter implements InitializingBean {

    @Override
    public PlugInRet beforeSave(int classId, FormTemplate formTemplate, JsonNode data) {

        // 单位，职员新增时，设置归属医院
        if (SessionUtil.getUserLinkSupplier() == -1) {
            throw new BusinessLogicRunTimeException("当前登录用户非供应商用户，不能进行此操作!");
        }

        ((ObjectNode) data).put("supplier", SessionUtil.getUserLinkSupplier());

        return super.beforeSave(classId, formTemplate, data);
    }

    /**
     * 插件支持的业务类别id集合，推荐的做法是一个插件只支持一个业务类别
     * 如只支持订单业务
     */
    private Set<Integer> classIdSet;

    @Override
    public String name() {
        return "供应商维护的证件类别插件";
    }

    @Override
    public String description() {
        return "供应商维护的证件类别插件";
    }

    @Override
    public Integer index() {
        return 1;
    }

    @Override
    public Set<Integer> getSupports() {
        return classIdSet;
    }

    @Override
    public boolean support(Integer classId) {
        return classIdSet.contains(classId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        classIdSet = new HashSet<>();
        // 供应商证件
        classIdSet.add(Constants.ClassType.SUPPLIER_QUALIFICATION_TYPE.classId());

    }

    @Override
    public List<Condition> getConditions(int classId, FormTemplate formTemplate, List<Condition> conditions) {

        List<Condition> ret = new ArrayList<>();

        Constants.UserRoleType userRoleType = getCurrentUserRoleType();
        Long linkOrg = getCurrentUserLinkOrg();

        if (userRoleType == Constants.UserRoleType.SYSTEM) {
            // 系统角色类别放开所有数据查看权限
            return ret;
        } else if (userRoleType == Constants.UserRoleType.SUPPLIER) {
            // 当前是供应商角色的用户在操作
            Condition condition = new Condition();
            condition.setLinkType(Condition.LinkType.AND).setFieldKey("supplier")
                    .setLogicOperator(Condition.LogicOperator.EQUAL).setValue(linkOrg).setNeedConvert(false);

            ret.add(condition);

        } else {
            throw new BusinessLogicRunTimeException("你无权查看");
        }

        return ret;

    }

}
