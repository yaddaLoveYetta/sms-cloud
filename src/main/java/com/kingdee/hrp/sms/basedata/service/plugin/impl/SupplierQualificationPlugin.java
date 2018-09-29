package com.kingdee.hrp.sms.basedata.service.plugin.impl;

import com.kingdee.hrp.sms.common.dao.generate.SupplierQualificationAttachmentMapper;
import com.kingdee.hrp.sms.common.dao.generate.SupplierQualificationMapper;
import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.SupplierQualification;
import com.kingdee.hrp.sms.common.model.SupplierQualificationAttachmentExample;
import com.kingdee.hrp.sms.common.model.SupplierQualificationExample;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.FormTemplate;
import com.kingdee.hrp.sms.common.service.plugin.AbstractPlugInAdapter;
import com.kingdee.hrp.sms.common.service.plugin.PlugInRet;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 供应商证件插件
 *
 * @author yadda
 */
public class SupplierQualificationPlugin extends AbstractPlugInAdapter implements InitializingBean {

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
        return "供应商证件插件";
    }

    /**
     * 插件说明</br>
     * 简单介绍下插件做了什么事情
     *
     * @return 插件说明
     */
    @Override
    public String description() {
        return "供应商维护自己证件资料特殊业务";
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
        return null;
    }

    /**
     * 插件是否支持指定的业务单据
     *
     * @param classId 业务单据类型
     * @return true if support , false not support
     */
    @Override
    public boolean support(Integer classId) {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        classIdSet = new HashSet<>();
        // 供应商证件
        classIdSet.add(Constants.ClassType.SUPPLIER_QUALIFICATION.classId());

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
        } else if (userRoleType == Constants.UserRoleType.SUPPLIER) {
            // 当前是供应商角色的用户在操作
            Condition condition = new Condition();
            condition.setLinkType(Condition.LinkType.AND).setFieldKey("supplier")
                    .setLogicOperator(Condition.LogicOperator.EQUAL).setValue(linkOrg).setNeedConvert(false);

            ret.add(condition);

        } else if (userRoleType == Constants.UserRoleType.HOSPITAL) {
            throw new BusinessLogicRunTimeException("医院用户无权查看");
        }

        return ret;

    }

    /**
     * 供应商删除证件时将附件信息一起清除(附件文件不清理)
     *
     * @param classId      业务类型
     * @param formTemplate 模板
     * @param ids          删除的内码集合
     * @return PlugInRet
     */
    @Override
    public PlugInRet beforeDelete(int classId, FormTemplate formTemplate, List<Long> ids) {

        Long supplier = SessionUtil.checkSupplier();

        SupplierQualificationMapper supplierQualificationMapper = getMapper(SupplierQualificationMapper.class);
        SupplierQualificationExample example = new SupplierQualificationExample();

        example.createCriteria().andSupplierEqualTo(supplier).andIdIn(ids);

        List<SupplierQualification> supplierQualifications = supplierQualificationMapper.selectByExample(example);

        List<Long> parentIds = supplierQualifications.stream().map(SupplierQualification::getId)
                .collect(Collectors.toList());

        SupplierQualificationAttachmentMapper supplierQualificationAttachmentMapper = getMapper(
                SupplierQualificationAttachmentMapper.class);
        SupplierQualificationAttachmentExample supplierQualificationAttachmentExample = new SupplierQualificationAttachmentExample();

        supplierQualificationAttachmentExample.createCriteria().andParentIn(parentIds);
        // 删除附件信息(真实附件文件不清理)
        supplierQualificationAttachmentMapper.deleteByExample(supplierQualificationAttachmentExample);

        return super.afterDelete(classId, formTemplate, ids);
    }
}
