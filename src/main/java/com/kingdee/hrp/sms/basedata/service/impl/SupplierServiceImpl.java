package com.kingdee.hrp.sms.basedata.service.impl;

import com.kingdee.hrp.sms.basedata.service.SupplierService;
import com.kingdee.hrp.sms.common.dao.generate.CooperationApplyMapper;
import com.kingdee.hrp.sms.common.dao.generate.MessageMapper;
import com.kingdee.hrp.sms.common.dao.generate.PartnerMapper;
import com.kingdee.hrp.sms.common.dao.generate.SupplierMapper;
import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.*;
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
public class SupplierServiceImpl extends BaseService implements SupplierService {
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

        // 检查医院是否已经是供应商的合作医院
        PartnerMapper partnerMapper = sqlSession.getMapper(PartnerMapper.class);
        PartnerExample partnerExample = new PartnerExample();

        PartnerExample.Criteria partnerExampleCriteria = partnerExample.createCriteria();
        partnerExampleCriteria.andLinkOrgEqualTo(hospital).andOrgEqualTo(supplier);

        List<Partner> partners = partnerMapper.selectByExample(partnerExample);
        if (partners != null && partners.size() > 0) {
            throw new BusinessLogicRunTimeException("您已经是该医院的供应商，不需要申请!");
        }

        // 判断是否已经向该医院发出过申请，不能重复向医院发送申请
        CooperationApplyMapper mapper = sqlSession.getMapper(CooperationApplyMapper.class);

        CooperationApplyExample example = new CooperationApplyExample();
        CooperationApplyExample.Criteria criteria = example.createCriteria();
        // 未处理的申请
        criteria.andSupplierEqualTo(supplier).andHospitalEqualTo(hospital).andStatusEqualTo(
                Constants.CooperationApplyStatus.UN_PROCESSED.value());

        List<CooperationApply> cooperationApplies = mapper.selectByExample(example);

        if (cooperationApplies != null && cooperationApplies.size() > 0) {
            // 存在待处理的申请不允许再新增 (只有申请被拒绝后可重新提申请)
            throw new BusinessLogicRunTimeException("您已经对该医院发送过申请，请到申请列表中查看状态!");
        }

        // 新增申请
        CooperationApply apply = new CooperationApply();
        // 申请id
        Long applyId = getId();
        // 申请时间
        Date applyDate = new Date();
        apply.setId(applyId);
        apply.setSupplier(supplier);
        apply.setHospital(hospital);
        apply.setStatus(Constants.CooperationApplyStatus.UN_PROCESSED.getNumber());
        apply.setDate(applyDate);

        mapper.insert(apply);

        // 发送消息通知给医院
        sendMessage(supplier, hospital, applyId, applyDate);

        return true;
    }

    /**
     * 发送消息给医院
     *
     * @param supplier  供应商
     * @param hospital  医院
     * @param applyId   合作申请申请id
     * @param applyDate 时间
     */
    private void sendMessage(Long supplier, Long hospital, Long applyId, Date applyDate) {

        Message message = new Message();

        message.setId(getId());
        // 消息类别，参考t_assistance表type_id=40的记录
        message.setType(Constants.MessageType.COOPERATION_APPLICATION.value());
        message.setStatus(Constants.MessageStatus.UN_PROCESSED.getNumber());
        message.setDate(applyDate);
        // 扩展数据中保存了申请id
        message.setData(applyId.toString());

        message.setReceiverType(Constants.UserRoleType.HOSPITAL.getNumber());
        message.setReceiverOrg(hospital);
        message.setSenderType(Constants.UserRoleType.SUPPLIER.getNumber());
        message.setSenderOrg(supplier);

        message.setTopic("收到一个申请，希望成为您医院的供应商");

        MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);

        messageMapper.insertSelective(message);
    }
}
