package com.kingdee.hrp.sms.basedata.service;

import com.kingdee.hrp.sms.common.enums.CooperationApplyStatus;
import org.springframework.stereotype.Service;

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
     * @param hrpSupplier            医院指定关联的本地HRP供应商（operateType=0时必须有）
     * @param cooperationApplyStatus 操作类型
     */
    void processCooperationApply(Long id, Long hrpSupplier, CooperationApplyStatus cooperationApplyStatus);
}
