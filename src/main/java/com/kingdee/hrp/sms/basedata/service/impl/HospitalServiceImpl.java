package com.kingdee.hrp.sms.basedata.service.impl;

import com.kingdee.hrp.sms.basedata.service.HospitalService;
import com.kingdee.hrp.sms.common.dao.generate.CooperationApplyMapper;
import com.kingdee.hrp.sms.common.dao.generate.HospitalMapper;
import com.kingdee.hrp.sms.common.dao.generate.PartnerMapper;
import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.CooperationApply;
import com.kingdee.hrp.sms.common.model.Hospital;
import com.kingdee.hrp.sms.common.model.Message;
import com.kingdee.hrp.sms.common.model.Partner;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.common.service.MessageService;
import com.kingdee.hrp.sms.util.Environ;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 医院相关功能操作
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/4/13 11:18
 */
@Service
public class HospitalServiceImpl extends BaseService implements HospitalService {

    private static Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

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

        HospitalMapper mapper = sqlSession.getMapper(HospitalMapper.class);

        Hospital hospital = new Hospital();
        hospital.setId(id);
        hospital.setLogo(picUrl);

        mapper.updateByPrimaryKeySelective(hospital);
    }

    /**
     * 医院处理供应商成为合作供应商的申请
     *
     * @param id                     申请记录id
     * @param hrpSupplier            医院指定关联的本地HRP供应商（cooperationApplyStatus=Constants.CooperationApplyStatus.AGREE 时必须有）
     * @param cooperationApplyStatus 操作类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processCooperationApply(Long id, Long hrpSupplier,
            Constants.CooperationApplyStatus cooperationApplyStatus) {

        if (null == id) {
            throw new BusinessLogicRunTimeException("请选择记录进行操作!");
        }
        if (null == hrpSupplier && cooperationApplyStatus == Constants.CooperationApplyStatus.AGREE) {
            // 同意申请是必须关联hrp供应商
            throw new BusinessLogicRunTimeException("必须关联HRP供应商!");
        }

        CooperationApplyMapper cooperationApplyMapper = sqlSession.getMapper(CooperationApplyMapper.class);

        CooperationApply cooperationApply = cooperationApplyMapper.selectByPrimaryKey(id);

        if (null == cooperationApply) {
            logger.error("不存在id为{}的供应商申请记录!", id);
            throw new BusinessLogicRunTimeException(String.format("不存在id为%d的供应商申请记录!", id));
        }

        if (cooperationApply.getStatus() != Constants.CooperationApplyStatus.UN_PROCESSED.value()) {
            logger.error("该记录已操作，不可重复操作,id:{}", id);
            throw new BusinessLogicRunTimeException(String.format("该记录已操作，不可重复操作!", id));
        }

        // 更新申请记录状态为已处理

        cooperationApply.setStatus(cooperationApplyStatus.value());
        cooperationApply.setProcessDate(new Date());

        cooperationApplyMapper.updateByPrimaryKeySelective(cooperationApply);

        // 发一条消息给供应商
        sendMessage(cooperationApply);

        if (cooperationApplyStatus == Constants.CooperationApplyStatus.DISAGREE) {
            return;
        }

        if (cooperationApplyStatus == Constants.CooperationApplyStatus.AGREE) {
            // 如果是同意供应商的申请-将供应商加入到医院的合作供应商中，将医院加入到供应商的合作医院中，即互成对方partner
            generatePartner(hrpSupplier, cooperationApply);
        }

    }

    /**
     * 新增合作伙伴(医院与供应商互成对方partner)
     *
     * @param hrpSupplier      医院HRP供应商
     * @param cooperationApply 合作申请
     */
    private void generatePartner(Long hrpSupplier, CooperationApply cooperationApply) {

        // 医院的合作供应商
        Partner hospitalPartner = new Partner();

        hospitalPartner.setId(getId());
        hospitalPartner.setOrg(SessionUtil.getUserLinkHospital());
        hospitalPartner.setLinkOrg(cooperationApply.getSupplier());
        hospitalPartner.setHrpSupplier(hrpSupplier);
        // 医院的合作供应商
        hospitalPartner.setType(false);
        // 非禁用
        hospitalPartner.setStatus(false);
        // 已审核
        hospitalPartner.setCheckStatus(2);

        // 供应商的合作医院
        Partner supplierPartner = new Partner();

        supplierPartner.setId(getId());
        supplierPartner.setOrg(cooperationApply.getSupplier());
        supplierPartner.setLinkOrg(SessionUtil.getUserLinkHospital());

        // 供应商的合作医院
        supplierPartner.setType(true);
        // 非禁用
        supplierPartner.setStatus(false);
        // 已审核
        supplierPartner.setCheckStatus(2);

        PartnerMapper partnerMapper = sqlSession.getMapper(PartnerMapper.class);

        partnerMapper.insert(hospitalPartner);
        partnerMapper.insert(supplierPartner);

    }

    /**
     * 发一天申请已处理的消息给供应商
     *
     * @param cooperationApply 合作申请
     */
    private void sendMessage(CooperationApply cooperationApply) {

        Message message = new Message();
        message.setId(getId());
        message.setType(Constants.MessageType.COOPERATION_APPLICATION_PROCESSED.value());
        message.setStatus(Constants.MessageStatus.UN_PROCESSED.getNumber());
        message.setDate(new Date());
        // 扩展数据中保存了申请id
        message.setData(cooperationApply.getId().toString());
        message.setReceiverType(Constants.UserRoleType.SUPPLIER.value());
        message.setReceiverOrg(cooperationApply.getSupplier());
        message.setSenderType(Constants.UserRoleType.HOSPITAL.value());
        message.setSenderOrg(SessionUtil.getUserLinkHospital());
        message.setTopic("你的申请医院已审批，点击查看审批结果!");

        MessageService messageService = Environ.getBean(MessageService.class);

        if (messageService != null) {
            messageService.add(message);
        }

    }

}
