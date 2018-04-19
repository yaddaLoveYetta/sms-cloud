package com.kingdee.hrp.sms.basedata.service.impl;

import com.kingdee.hrp.sms.basedata.service.IItemService;
import com.kingdee.hrp.sms.common.dao.generate.ItemMapper;
import com.kingdee.hrp.sms.common.model.Item;
import com.kingdee.hrp.sms.common.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yadda(silenceisok@163.com)
 * @Title ItemService
 * @date 2018/4/19 14:04
 */

@Service
public class ItemService extends BaseService implements IItemService {
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

        ItemMapper mapper = sqlSession.getMapper(ItemMapper.class);

        Item item = new Item();
        item.setId(id);
        item.setImage(picUrl);

        mapper.updateByPrimaryKeySelective(item);
    }
}
