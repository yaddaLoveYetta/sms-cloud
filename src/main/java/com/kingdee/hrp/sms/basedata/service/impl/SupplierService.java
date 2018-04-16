package com.kingdee.hrp.sms.basedata.service.impl;

import com.kingdee.hrp.sms.basedata.service.ISupplierService;
import com.kingdee.hrp.sms.common.dao.generate.SupplierMapper;
import com.kingdee.hrp.sms.common.model.Supplier;
import com.kingdee.hrp.sms.common.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yadda(silenceisok@163.com)
 * @date 2018/4/13 11:18
 */
@Service
public class SupplierService extends BaseService implements ISupplierService {
    /**
     * 保存医院logo
     *
     * @param classId 资料类别1012
     * @param id      资料内码
     * @param picUrl  logo url(完整url)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeLogo(Integer classId, Long id, String picUrl) {

        SupplierMapper mapper = sqlSession.getMapper(SupplierMapper.class);

        Supplier supplier = new Supplier();
        supplier.setId(id);
        supplier.setLogo(picUrl);

        mapper.updateByPrimaryKeySelective(supplier);
    }
}
