package com.kingdee.hrp.sms.basedata.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.primitives.Longs;
import com.kingdee.hrp.sms.basedata.service.SupplierService;
import com.kingdee.hrp.sms.common.dao.generate.*;
import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.*;
import com.kingdee.hrp.sms.common.pojo.Qualification;
import com.kingdee.hrp.sms.common.pojo.QualificationType;
import com.kingdee.hrp.sms.common.pojo.SupplierQualificationModel;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.util.FileOperate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

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
     * 获取指定医院对供应商资质的要求且特定供应商已提供资质明细
     *
     * @param hospital 医院
     * @param supplier 供应商
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     * @return SupplierQualificationModel
     */
    @Override
    public SupplierQualificationModel getQualificationByHospital(Long supplier, Long hospital, Integer pageSize,
            Integer pageNo) {

        // 医院对供应商的资质需求类别
        List<HospitalSupplierQualificationType> hospitalSupplierQualificationTypes = getHospitalSupplierQualificationTypes(
                hospital);

        if (CollectionUtils.isEmpty(hospitalSupplierQualificationTypes)) {
            throw new BusinessLogicRunTimeException("该医院未维护供应商资质类别需求");
        }

        // 本供应商已经提供了的证件--分页
        PageInfo<HospitalSupplierQualification> pageInfo = getHospitalSupplierQualificationPageInfo(supplier, hospital,
                pageSize, pageNo);

        return assembleQualification(hospitalSupplierQualificationTypes, pageInfo);
    }

    /**
     * 获取供应商对所有合作医院提供的资质
     *
     * @param supplier 供应商
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     * @return SupplierQualificationModel
     */
    @Override
    public SupplierQualificationModel getQualification(Long supplier, Integer pageSize, Integer pageNo) {

        // 本供应商已经提供了的证件--分页
        PageInfo<HospitalSupplierQualification> pageInfo = getSupplierQualificationPageInfo(supplier, pageSize, pageNo);

        return assembleQualification(null, pageInfo);

    }

    /**
     * 供应商提交一份证件给医院(证件信息及证件附件)
     *
     * @param type                    医院要求的证件类别id (t_hospital_supplier_qualification_type)
     * @param hospital                医院
     * @param supplierQualificationId 供应商维护的自己的证件信息id (t_supplier_qualification)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferQualification(Long type, Long hospital, Long supplierQualificationId) {

        SupplierQualificationMapper supplierQualificationMapper = getMapper(SupplierQualificationMapper.class);

        // 待提交的证件信息
        SupplierQualification supplierQualification = supplierQualificationMapper
                .selectByPrimaryKey(supplierQualificationId);

        if (null == supplierQualification) {
            throw new BusinessLogicRunTimeException("供应商证件资料不存在");
        }

        SupplierQualificationAttachmentMapper supplierQualificationAttachmentMapper = getMapper(
                SupplierQualificationAttachmentMapper.class);
        SupplierQualificationAttachmentExample example = new SupplierQualificationAttachmentExample();

        example.createCriteria().andParentEqualTo(supplierQualificationId);

        // 待提交的证件附件
        List<SupplierQualificationAttachment> supplierQualificationAttachments = supplierQualificationAttachmentMapper
                .selectByExample(example);

        if (CollectionUtils.isEmpty(supplierQualificationAttachments)) {
            throw new BusinessLogicRunTimeException("该证件未维护附件不可提交给医院");
        }

        //  将证件信息同步给医院 SupplierQualification ==> HospitalSupplierQualification
        HospitalSupplierQualificationMapper hospitalSupplierQualificationMapper = getMapper(
                HospitalSupplierQualificationMapper.class);

        HospitalSupplierQualification hospitalSupplierQualification = new HospitalSupplierQualification();

        hospitalSupplierQualification.setHospital(hospital);
        //  保留下id，附件同步时parent用
        Long hospitalSupplierQualificationId = getId();
        hospitalSupplierQualification.setId(hospitalSupplierQualificationId);
        hospitalSupplierQualification.setIssue(supplierQualification.getIssue());
        hospitalSupplierQualification.setQualificationType(type);
        hospitalSupplierQualification.setSupplier(supplierQualification.getSupplier());
        hospitalSupplierQualification.setValidityPeriodBegin(supplierQualification.getValidityPeriodBegin());
        hospitalSupplierQualification.setValidityPeriodEnd(supplierQualification.getValidityPeriodEnd());
        hospitalSupplierQualification.setRemark(supplierQualification.getRemark());
        hospitalSupplierQualification.setNumber(supplierQualification.getNumber());

        hospitalSupplierQualificationMapper.insertSelective(hospitalSupplierQualification);

        // 将供应商证件附件同步到医院供应商证件附件(附件路径信息)

        HospitalSupplierQualificationAttachmentMapper hospitalSupplierQualificationAttachmentMapper = getMapper(
                HospitalSupplierQualificationAttachmentMapper.class);

        List<HospitalSupplierQualificationAttachment> attachments = supplierQualificationAttachments.stream()
                .map(attachment -> {

                    HospitalSupplierQualificationAttachment hospitalSupplierQualificationAttachment = new HospitalSupplierQualificationAttachment();

                    hospitalSupplierQualificationAttachment.setId(getId());
                    hospitalSupplierQualificationAttachment.setParent(hospitalSupplierQualificationId);
                    hospitalSupplierQualificationAttachment.setPath(attachment.getPath());

                    return hospitalSupplierQualificationAttachment;

                }).collect(Collectors.toList());

        hospitalSupplierQualificationAttachmentMapper.batchInsert(attachments);

        // 附件文件复制 TODO

        supplierQualificationAttachments.stream().map(SupplierQualificationAttachment::getPath).forEach(path -> {

            byte[] file = FileOperate.downloadPicture(path);
        });
    }

    /**
     * 指定供应商对指定医院已经提供了的证件,
     * 如果没有指定医院则查询供应商对所有医院已经提供的证件
     *
     * @param supplier 供应商
     * @param hospital 医院
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     * @return PageInfo<HospitalSupplierQualification>
     */
    private PageInfo<HospitalSupplierQualification> getHospitalSupplierQualificationPageInfo(Long supplier,
            Long hospital, Integer pageSize, Integer pageNo) {

        HospitalSupplierQualificationMapper hospitalSupplierQualificationMapper = getMapper(
                HospitalSupplierQualificationMapper.class);

        HospitalSupplierQualificationExample hospitalSupplierQualificationExample = new HospitalSupplierQualificationExample();
        HospitalSupplierQualificationExample.Criteria criteria = hospitalSupplierQualificationExample.createCriteria();

        criteria.andSupplierEqualTo(supplier);

        if (null != hospital) {
            // 指定了医院，表示查询对特定医院已经提供的证件
            criteria.andHospitalEqualTo(hospital);
        }

        PageHelper.startPage(pageNo, pageSize, true);

        List<HospitalSupplierQualification> hospitalSupplierQualifications = hospitalSupplierQualificationMapper
                .selectByExample(hospitalSupplierQualificationExample);

        return new PageInfo<>(hospitalSupplierQualifications);
    }

    /**
     * 获取供应商对所有合作医院提供的资质
     *
     * @param supplier 供应商
     * @param pageSize 分页大小
     * @param pageNo   当前页码
     * @return PageInfo<HospitalSupplierQualification>
     */
    private PageInfo<HospitalSupplierQualification> getSupplierQualificationPageInfo(Long supplier,
            Integer pageSize, Integer pageNo) {

        return getHospitalSupplierQualificationPageInfo(supplier, null, pageSize, pageNo);
    }

    /**
     * 指定医院对供应商的资质需求类别
     *
     * @param hospital 医院
     * @return List<HospitalSupplierQualificationType>
     */
    private List<HospitalSupplierQualificationType> getHospitalSupplierQualificationTypes(Long hospital) {

        HospitalSupplierQualificationTypeMapper hospitalSupplierQualificationTypeMapper = getMapper(
                HospitalSupplierQualificationTypeMapper.class);
        HospitalSupplierQualificationTypeExample hospitalSupplierQualificationTypeExample = new HospitalSupplierQualificationTypeExample();
        hospitalSupplierQualificationTypeExample.createCriteria().andHospitalEqualTo(hospital);

        return hospitalSupplierQualificationTypeMapper
                .selectByExample(hospitalSupplierQualificationTypeExample);
    }

    /**
     * 组装返回结构
     *
     * @param hospitalSupplierQualificationTypes 医院要求的证件类别
     * @param pageInfo                           供应商已经提供的证件明细
     * @return SupplierQualificationModel<br/>
     * hospitalSupplierQualificationTypes无数据时返回null
     */
    private SupplierQualificationModel assembleQualification(
            List<HospitalSupplierQualificationType> hospitalSupplierQualificationTypes,
            PageInfo<HospitalSupplierQualification> pageInfo) {

        List<HospitalSupplierQualification> hospitalSupplierQualifications = pageInfo.getList();

        SupplierQualificationModel supplierQualification = new SupplierQualificationModel();

        supplierQualification.setDetail(new ArrayList<>());
        supplierQualification.setTypes(new ArrayList<>());

        // 总记录数
        supplierQualification.setTotal(pageInfo.getTotal());

        if (!CollectionUtils.isEmpty(hospitalSupplierQualificationTypes)) {

            hospitalSupplierQualificationTypes.forEach(type -> {

                QualificationType qualificationType = new QualificationType();
                qualificationType.setName(type.getName()).setIsExist(false).setIsMust(type.getIsMust());

                boolean anyMatch = hospitalSupplierQualifications.stream().anyMatch(item ->
                        Longs.compare(item.getQualificationType(), type.getId()) == 0);

                if (anyMatch) {
                    // 已提供此类别证件
                    qualificationType.setIsExist(true);
                }

                supplierQualification.getTypes().add(qualificationType);

            });
        }

        hospitalSupplierQualifications.forEach(item -> {

            Qualification qualification = new Qualification();

            qualification.setIssue(item.getIssue()).setNumber(item.getNumber()).setType(item.getQualificationType())
                    .setValidityPeriodBegin(item.getValidityPeriodBegin())
                    .setValidityPeriodEnd(item.getValidityPeriodEnd());

            supplierQualification.getDetail().add(qualification);

        });

        // 需提供的证件按照是否必须排序
        supplierQualification.getTypes().sort((t1, t2) -> {

            if (t1.getIsMust()) {
                return 1;
            }
            if (t2.getIsMust()) {
                return -1;
            }
            return 0;
        });
        // 证件明细按到期时间排序
        supplierQualification.getDetail().sort((q1, q2) -> {

            if (q1.getValidityPeriodEnd().before(q2.getValidityPeriodEnd())) {
                return 1;
            }
            if (q2.getValidityPeriodEnd().before(q1.getValidityPeriodEnd())) {
                return -1;
            }
            return 0;
        });

        return supplierQualification;
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
