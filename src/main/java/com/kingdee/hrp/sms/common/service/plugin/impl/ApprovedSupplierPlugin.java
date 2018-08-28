package com.kingdee.hrp.sms.common.service.plugin.impl;

import com.google.common.base.Joiner;
import com.kingdee.hrp.sms.common.dao.generate.PartnerMapper;
import com.kingdee.hrp.sms.common.enums.ClassType;
import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.common.model.Partner;
import com.kingdee.hrp.sms.common.model.PartnerExample;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;
import com.kingdee.hrp.sms.common.service.plugin.AbstractPlugInAdapter;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 中标库插件
 *
 * @author le.xiao
 */
@Component
public class ApprovedSupplierPlugin extends AbstractPlugInAdapter implements InitializingBean {

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
        return "中标库插件";
    }

    /**
     * 插件说明</br>
     * 简单介绍下插件做了什么事情
     *
     * @return 插件说明
     */
    @Override
    public String description() {
        return "主要实现中标库相关操作处理业务";
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

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        classIdSet = new HashSet<>();
        // 中标库
        classIdSet.add(ClassType.APPROVED_SUPPLIER.classId());

    }

    /**
     * 中标库查询前根据用户角色类别设置查询条件
     * 医院只能查看本医院物料的中标库
     * 供应商只能查看本供应商的中标库
     *
     * @param classId      业务类别
     * @param formTemplate 单据模板
     * @param conditions   原始过滤条件
     * @return 插件过滤条件
     */
    @Override
    public List<Condition> getConditions(int classId, FormTemplate formTemplate, List<Condition> conditions) {

        List<Condition> ret = new ArrayList<>();

        if (classId != ClassType.APPROVED_SUPPLIER.classId()) {
            // 本插件只支持中标库
            return super.getConditions(classId, formTemplate, conditions);
        }

        if (SessionUtil.getUserRoleType() == UserRoleType.HOSPITAL) {

            // 医院只能查看本院中标库
            Condition condition = new Condition();
            condition.setLinkType(Condition.LinkType.AND)
                    .setFieldKey("hospital")
                    .setLogicOperator(Condition.LogicOperator.EQUAL)
                    .setValue(SessionUtil.getUserLinkHospital())
                    .setNeedConvert(false);
            ret.add(condition);

        } else if (SessionUtil.getUserRoleType() == UserRoleType.SUPPLIER) {
            // 供应商查看自己在所有医院的中标库

            // 查询供应商的合作医院来确定此供应商用户关联到了哪些医院且在每个医院对应的HRP供应商
            PartnerMapper partnerMapper = sqlSession.getMapper(PartnerMapper.class);
            PartnerExample partnerExample = new PartnerExample();
            // 未禁用的合作关系
            partnerExample.createCriteria().andOrgEqualTo(SessionUtil.getUserLinkSupplier()).andStatusEqualTo(false);

            List<Partner> partners = partnerMapper.selectByExample(partnerExample);

            // 跟此供应商有合作关系的医院
            List<Long> hospitals = new ArrayList<>();
            // 此供应商在医院关联的HRP供应商
            List<Long> hospitalSupplies = new ArrayList<>();

            partners.forEach(partner -> {
                hospitals.add(partner.getLinkOrg());
                hospitalSupplies.add(partner.getSupplier());
            });

            if (!CollectionUtils.isEmpty(hospitals)) {
                Condition condition = new Condition();
                condition.setLinkType(Condition.LinkType.AND)
                        .setFieldKey("hospital")
                        .setLogicOperator(Condition.LogicOperator.IN)
                        .setValue(Joiner.on(",").join(hospitals))
                        .setNeedConvert(false);
                ret.add(condition);
            }

            if (!CollectionUtils.isEmpty(hospitalSupplies)) {
                Condition condition = new Condition();
                condition.setLinkType(Condition.LinkType.AND)
                        .setFieldKey("supplier")
                        .setLogicOperator(Condition.LogicOperator.IN)
                        .setValue(Joiner.on(",").join(hospitalSupplies))
                        .setNeedConvert(false);
                ret.add(condition);
            }

            if (CollectionUtils.isEmpty(hospitals) && CollectionUtils.isEmpty(hospitalSupplies)) {
                // 没有同任何医院有合作关系或者有合作关系的医院未将此供应商关联医院HRP供应商，不能看到任何中标库信息,
                // 此处设置一个条件使查询无数据
                Condition condition = new Condition();
                condition.setLinkType(Condition.LinkType.AND)
                        .setFieldKey("id")
                        .setLogicOperator(Condition.LogicOperator.EQUAL)
                        .setValue(0);
                ret.add(condition);
            }

        }

        return ret;
    }
}
