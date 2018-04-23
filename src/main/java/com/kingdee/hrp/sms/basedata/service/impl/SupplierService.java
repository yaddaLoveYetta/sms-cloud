package com.kingdee.hrp.sms.basedata.service.impl;

import com.kingdee.hrp.sms.basedata.service.ISupplierService;
import com.kingdee.hrp.sms.common.dao.generate.CooperationApplyMapper;
import com.kingdee.hrp.sms.common.dao.generate.SupplierMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.CooperationApply;
import com.kingdee.hrp.sms.common.model.CooperationApplyExample;
import com.kingdee.hrp.sms.common.model.Supplier;
import com.kingdee.hrp.sms.common.pojo.BaseStatusEnum;
import com.kingdee.hrp.sms.common.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    /**
     * 供应商发送申请成为医院合作供应商
     *
     * @param supplier 供应商id
     * @param hospital 医院id
     * @return Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addCooperationApply(Long supplier, Long hospital) {

        CooperationApplyMapper mapper = sqlSession.getMapper(CooperationApplyMapper.class);

        CooperationApplyExample example = new CooperationApplyExample();
        CooperationApplyExample.Criteria criteria = example.createCriteria();
        criteria.andSupplierEqualTo(supplier);
        criteria.andHospitalEqualTo(hospital);

        List<CooperationApply> cooperationApplies = mapper.selectByExample(example);

        if (cooperationApplies != null && cooperationApplies.size() > 0) {
            throw new BusinessLogicRunTimeException("您已经对该医院发送过申请，请到申请列表中查看状态!");
        }

        CooperationApply apply = new CooperationApply();

        apply.setId(getId());
        apply.setSupplier(supplier);
        apply.setHospital(hospital);
        apply.setStatus(BaseStatusEnum.UN_PROCESSED.getNumber());
        apply.setDate(new Date());

        mapper.insert(apply);

        return true;
    }
}
