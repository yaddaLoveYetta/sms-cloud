package com.kingdee.hrp.sms.basedata.service.impl;

import com.kingdee.hrp.sms.basedata.service.IMaterialService;
import com.kingdee.hrp.sms.common.dao.generate.MaterialMapper;
import com.kingdee.hrp.sms.common.model.Material;
import com.kingdee.hrp.sms.common.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yadda(silenceisok@163.com)
 * @Title ItemService
 * @date 2018/4/19 14:04
 */

@Service
public class MaterialService extends BaseService implements IMaterialService {
    /**
     * 物料图片,只能设置一张
     *
     * @param classId 资料类别1006
     * @param id      资料内码
     * @param picUrl  url(完整url)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setImage(Integer classId, Long id, String picUrl) {

        MaterialMapper mapper = sqlSession.getMapper(MaterialMapper.class);

        Material material = new Material();
        material.setId(id);
        material.setImage(picUrl);

        mapper.updateByPrimaryKeySelective(material);
    }
}
