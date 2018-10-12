package com.kingdee.hrp.sms.basedata.service;

import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.pojo.QualificationType;
import com.kingdee.hrp.sms.common.pojo.SupplierQualificationModel;

import java.util.List;

/**
 * 医院相关功能接口
 *
 * @author yadda
 */

public interface HospitalService {

    /**
     * 保存医院logo
     *
     * @param classId 资料类别1012
     * @param id      资料内码
     * @param picUrl  logo url(完整url)
     */
    void changeLogo(Integer classId, Long id, String picUrl);

    /**
     * 医院处理供应商成为合作供应商的申请
     *
     * @param id                     申请记录id
     * @param hrpSupplier            医院指定关联的本地HRP供应商（cooperationApplyStatus=Constants.CooperationApplyStatus.AGREE 时必须有时必须有）
     * @param cooperationApplyStatus 操作类型
     */
    void processCooperationApply(Long id, Long hrpSupplier, Constants.CooperationApplyStatus cooperationApplyStatus);

    /**
     * 获取医院对供应商的所有资质需求类别
     *
     * @param hospital 医院
     * @return List<QualificationType>
     */
    List<QualificationType> getQualificationTypes(Long hospital);

    /**
     * 获取指定供应商已提供的证件明细
     *
     * @param hospital 医院
     * @param supplier 供应商
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     * @return SupplierQualificationModel
     */
    SupplierQualificationModel getSupplierQualificationsBySupplier(Long hospital, Long supplier,
            Integer pageSize,
            Integer pageNo);

    /**
     * 获取所有供应商已提供的证件明细
     *
     * @param hospital 医院
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     * @return SupplierQualificationModel
     */
    SupplierQualificationModel getSupplierQualifications(Long hospital, Integer pageSize, Integer pageNo);
}
