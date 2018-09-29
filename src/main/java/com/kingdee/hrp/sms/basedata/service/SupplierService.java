package com.kingdee.hrp.sms.basedata.service;

import com.kingdee.hrp.sms.common.pojo.Qualification;
import com.kingdee.hrp.sms.common.pojo.SupplierQualificationModel;

import java.io.File;
import java.util.List;

/**
 * @author yadda(silenceisok@163.com)
 * @date 2018/09/26 11:01
 */

public interface SupplierService {

    /**
     * 保存医院logo
     *
     * @param classId 资料类别1013
     * @param id      资料内码
     * @param picUrl  logo url(完整url)
     */
    void changeLogo(Integer classId, Long id, String picUrl);

    /**
     * 供应商发送申请成为医院合作供应商
     *
     * @param supplier 供应商id
     * @param hospital 医院id
     * @return Boolean
     */
    Boolean addCooperationApply(Long supplier, Long hospital);

    /**
     * 获取指定医院对供应商资质的要求且特定供应商已提供资质明细
     *
     * @param hospital 医院
     * @param supplier 供应商
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     * @return SupplierQualificationModel
     */
    SupplierQualificationModel getQualificationByHospital(Long supplier, Long hospital, Integer pageSize,
            Integer pageNo);

    /**
     * 获取供应商对所有合作医院提供的资质
     *
     * @param supplier 供应商
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     * @return SupplierQualificationModel
     */
    SupplierQualificationModel getQualification(Long supplier, Integer pageSize, Integer pageNo);

    /**
     * 供应商提交一份证件给医院(证件信息及证件附件)
     *
     * @param type                    医院要求的证件类别id (t_hospital_supplier_qualification_type)
     * @param hospital                医院
     * @param supplierQualificationId 供应商维护的自己的证件信息id (t_supplier_qualification)
     */
    void transferQualification(Long type, Long hospital, Long supplierQualificationId);

    /**
     * 供应商新增一个证件资料
     *
     * @param supplier      供应商
     * @param qualification 证件信息
     * @param files         证件附件
     */
    void addQualification(Long supplier, Qualification qualification, List<File> files);

    /**
     * 供应商为证件增加附件
     *
     * @param supplier  供应商
     * @param qualificationId        证件id
     * @param files     附件
     * @param overwrite 替换、追加附件(1:覆盖0:追加，默认0)
     */
    void addQualificationAttachment(Long supplier, Long qualificationId, List<File> files, Integer overwrite);

    /**
     * 供应商删除证件附件
     *  @param supplier        供应商
     * @param qualificationId 证件id
     * @param attachmentIds    附件id列表
     */
    void delQualificationAttachment(Long supplier, Long qualificationId, List<Long> attachmentIds);
}
