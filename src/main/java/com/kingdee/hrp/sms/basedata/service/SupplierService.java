package com.kingdee.hrp.sms.basedata.service;

/**
 * @author yadda
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
}
