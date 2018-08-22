package com.kingdee.hrp.sms.basedata.service;

import org.springframework.stereotype.Service;

/**
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
}
