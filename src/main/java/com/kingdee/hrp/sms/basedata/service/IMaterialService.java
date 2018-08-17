package com.kingdee.hrp.sms.basedata.service;

public interface IMaterialService {

    /**
     * 物料图片,只能设置一张
     *
     * @param classId 资料类别1006
     * @param id      资料内码
     * @param picUrl  url(完整url)
     */
    void setImage(Integer classId, Long id, String picUrl);
}
