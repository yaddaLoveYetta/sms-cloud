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
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public SupplierQualificationModel getHospitalSupplierQualificationsByHospital(Long supplier, Long hospital,
                                                                                  Integer pageSize,
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
    public SupplierQualificationModel getHospitalSupplierQualifications(Long supplier, Integer pageSize,
                                                                        Integer pageNo) {

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
        //  保留下id，附件同步时作为其parent
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

        // 将供应商证件附件同步到医院供应商证件附件(附件信息及附件文件)
        List<HospitalSupplierQualificationAttachment> attachments = supplierQualificationAttachments.parallelStream()
                .map(attachment -> {

                    String path = attachment.getPath();

                    // 附件文件复制
                    String newPath = attachmentCopy(path);

                    HospitalSupplierQualificationAttachment hospitalSupplierQualificationAttachment = new HospitalSupplierQualificationAttachment();

                    hospitalSupplierQualificationAttachment.setId(getId());
                    hospitalSupplierQualificationAttachment.setParent(hospitalSupplierQualificationId);
                    hospitalSupplierQualificationAttachment.setPath(newPath);

                    return hospitalSupplierQualificationAttachment;

                }).collect(Collectors.toList());

        HospitalSupplierQualificationAttachmentMapper hospitalSupplierQualificationAttachmentMapper = getMapper(
                HospitalSupplierQualificationAttachmentMapper.class);

        hospitalSupplierQualificationAttachmentMapper.batchInsert(attachments);

    }

    /**
     * 供应商新增一个证件资料
     *
     * @param supplier      供应商
     * @param qualification 证件信息
     * @param files         证件附件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addQualification(Long supplier, Qualification qualification, List<File> files) {

        SupplierQualificationMapper supplierQualificationMapper = getMapper(SupplierQualificationMapper.class);

        SupplierQualification supplierQualification = new SupplierQualification();

        Long supplierQualificationId = getId();

        supplierQualification.setId(supplierQualificationId);
        supplierQualification.setQualificationType(qualification.getType());
        supplierQualification.setNumber(qualification.getNumber());
        supplierQualification.setIssue(qualification.getIssue());
        supplierQualification.setSupplier(supplier);
        supplierQualification.setValidityPeriodBegin(qualification.getValidityPeriodBegin());
        supplierQualification.setValidityPeriodEnd(qualification.getValidityPeriodEnd());
        supplierQualification.setRemark(qualification.getRemark());

        supplierQualificationMapper.insertSelective(supplierQualification);

        if (!CollectionUtils.isEmpty(files)) {
            // 记录附件保存后的路径
            List<SupplierQualificationAttachment> attachments = new ArrayList<>();
            // 保存附件
            files.forEach(file -> {
                // 保存附件到文件服务器，接收保存全路径
                String uploadPath = FileOperate
                        .upload(file, Constants.FilePath.SUPPLIER_QUALIFICATION_ATTACHMENT.path());

                SupplierQualificationAttachment attachment = new SupplierQualificationAttachment();

                attachment.setId(getId());
                attachment.setParent(supplierQualificationId);
                attachment.setPath(uploadPath);

                attachments.add(attachment);
            });

            if (!CollectionUtils.isEmpty(attachments)) {
                // 保存附件信息
                SupplierQualificationAttachmentMapper attachmentMapper = getMapper(
                        SupplierQualificationAttachmentMapper.class);
                attachmentMapper.batchInsert(attachments);
            }

        }

        return supplierQualificationId;

    }

    /**
     * 供应商为证件增加附件
     *
     * @param supplier        供应商
     * @param qualificationId 证件id
     * @param files           附件
     * @param overwrite       替换、追加附件(1:覆盖0:追加，默认0)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addQualificationAttachment(Long supplier, Long qualificationId, List<File> files, Integer overwrite) {

        SupplierQualificationMapper supplierQualificationMapper = getMapper(SupplierQualificationMapper.class);
        SupplierQualificationExample supplierQualificationExample = new SupplierQualificationExample();

        supplierQualificationExample.createCriteria().andIdEqualTo(qualificationId).andSupplierEqualTo(supplier);

        List<SupplierQualification> supplierQualifications = supplierQualificationMapper
                .selectByExample(supplierQualificationExample);

        if (CollectionUtils.isEmpty(supplierQualifications)) {
            throw new BusinessLogicRunTimeException("证件不存在,请查证!");
        }

        SupplierQualificationAttachmentMapper supplierQualificationAttachmentMapper = getMapper(
                SupplierQualificationAttachmentMapper.class);

        if (1 == overwrite) {
            // 替换原来的证件->删除原来的证件
            SupplierQualificationAttachmentExample supplierQualificationAttachmentExample = new SupplierQualificationAttachmentExample();
            supplierQualificationAttachmentExample.createCriteria().andParentEqualTo(qualificationId);

            supplierQualificationAttachmentMapper.deleteByExample(supplierQualificationAttachmentExample);
        }

        if (!CollectionUtils.isEmpty(files)) {
            // 保存附件
            files.forEach(file -> {
                FileOperate.upload(file, Constants.FilePath.SUPPLIER_QUALIFICATION_ATTACHMENT.path());
            });
        }

    }

    /**
     * 供应商删除证件附件<br/>
     * (只是删除附件绑定资料，非物理清楚附件文件)
     *
     * @param supplier        供应商
     * @param qualificationId 证件id
     * @param attachmentIds   附件id列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delQualificationAttachment(Long supplier, Long qualificationId, List<Long> attachmentIds) {

        SupplierQualificationMapper supplierQualificationMapper = getMapper(SupplierQualificationMapper.class);
        SupplierQualificationExample supplierQualificationExample = new SupplierQualificationExample();

        supplierQualificationExample.createCriteria().andIdEqualTo(qualificationId).andSupplierEqualTo(supplier);

        List<SupplierQualification> supplierQualifications = supplierQualificationMapper
                .selectByExample(supplierQualificationExample);

        if (CollectionUtils.isEmpty(supplierQualifications)) {
            throw new BusinessLogicRunTimeException("证件不存在,请查证!");
        }

        SupplierQualificationAttachmentMapper supplierQualificationAttachmentMapper = getMapper(
                SupplierQualificationAttachmentMapper.class);

        SupplierQualificationAttachmentExample supplierQualificationAttachmentExample = new SupplierQualificationAttachmentExample();
        supplierQualificationAttachmentExample.createCriteria().andIdIn(attachmentIds);
        supplierQualificationAttachmentMapper.deleteByExample(supplierQualificationAttachmentExample);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delQualificationAttachment(Long supplier, Long qualificationId, Long attachmentId) {

        List<Long> ids = new ArrayList<Long>() {{
            add(attachmentId);
        }};

        delQualificationAttachment(supplier, qualificationId, ids);
    }

    /**
     * 供应商资质获取附件列表
     *
     * @param supplier        供应商
     * @param qualificationId 证件
     * @return 附件列表
     */
    @Override
    public List<SupplierQualificationAttachment> getQualificationAttachment(Long supplier, Long qualificationId) {

        SupplierQualificationAttachmentMapper mapper = getMapper(SupplierQualificationAttachmentMapper.class);
        SupplierQualificationAttachmentExample example = new SupplierQualificationAttachmentExample();

        example.createCriteria().andParentEqualTo(qualificationId);

        return mapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editQualification(Qualification qualification) {

        if (null == qualification.getId()) {
            throw new BusinessLogicRunTimeException("请选择证件修改");
        }

        SupplierQualificationMapper mapper = getMapper(SupplierQualificationMapper.class);

        SupplierQualification supplierQualification = new SupplierQualification();

        supplierQualification.setId(qualification.getId());

        supplierQualification.setIssue(qualification.getIssue());
        supplierQualification.setNumber(qualification.getNumber());
        supplierQualification.setQualificationType(qualification.getType());
        supplierQualification.setValidityPeriodBegin(qualification.getValidityPeriodBegin());
        supplierQualification.setValidityPeriodEnd(qualification.getValidityPeriodEnd());
        supplierQualification.setRemark(qualification.getRemark());

        mapper.updateByPrimaryKeySelective(supplierQualification);
    }

    /**
     * 将供应商证件附件复制一份给医院
     *
     * @param path 供应商证件附件地址
     * @return 复制生成的医院供应商资质附件路径
     */
    private String attachmentCopy(String path) {
        try {

            File file = FileUtils.toFile(new URL(path));

            if (null == file) {
                logger.error("证件附件不存在");
                throw new BusinessLogicRunTimeException("供应商证件附件不正确，请检查!");
            }

            return FileOperate.upload(file, Constants.FilePath.HOSPITAL_SUPPLIER_QUALIFICATION_ATTACHMENT.path());

        } catch (Exception e) {
            logger.error("获取供应商证件附件错误", e.getMessage());
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }
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
     * 指定医院指定类别的资质类别信息
     *
     * @param hospital 医院
     * @return List<HospitalSupplierQualificationType>
     */
    private HospitalSupplierQualificationType getHospitalSupplierQualificationTypes(Long hospital, Long type) {

        HospitalSupplierQualificationTypeMapper hospitalSupplierQualificationTypeMapper = getMapper(
                HospitalSupplierQualificationTypeMapper.class);
        HospitalSupplierQualificationTypeExample hospitalSupplierQualificationTypeExample = new HospitalSupplierQualificationTypeExample();
        hospitalSupplierQualificationTypeExample.createCriteria().andHospitalEqualTo(hospital).andIdEqualTo(type);

        List<HospitalSupplierQualificationType> types = hospitalSupplierQualificationTypeMapper
                .selectByExample(hospitalSupplierQualificationTypeExample);

        if (!CollectionUtils.isEmpty(types)) {
            // 只可能是一个
            return types.get(0);
        }
        // 理论上不可能执行到此(有且仅有一个)
        return null;
    }

    /**
     * 组装返回结构
     *
     * @param hospitalSupplierQualificationTypes 医院要求的证件类别
     * @param pageInfo                           供应商已经提供的证件明细
     * @return SupplierQualificationModel<br>
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

        hospitalSupplierQualifications.forEach((HospitalSupplierQualification item) -> {

            Qualification qualification = new Qualification();

            qualification.setIssue(item.getIssue()).setNumber(item.getNumber()).setType(item.getQualificationType())
                    .setValidityPeriodBegin(item.getValidityPeriodBegin())
                    .setValidityPeriodEnd(item.getValidityPeriodEnd()).setRemark(item.getRemark());

            // 类型名称
            if (CollectionUtils.isEmpty(hospitalSupplierQualificationTypes)) {
                // 不区分医院获取证件信息时不能从类型里面拿，因为此时不会获取证件类别信息
                HospitalSupplierQualificationType hospitalSupplierQualificationType = getHospitalSupplierQualificationTypes(
                        item.getHospital(), item.getQualificationType());

                if (null != hospitalSupplierQualificationType) {
                    qualification.setTypeName(hospitalSupplierQualificationType.getName());
                }

            } else {
                // 指定医院获取证件信息时从类型里面过滤拿
                hospitalSupplierQualificationTypes.stream()
                        .filter(type -> type.getId() == item.getQualificationType().intValue()).findFirst().ifPresent(
                        hospitalSupplierQualificationType -> qualification
                                .setTypeName(hospitalSupplierQualificationType.getName()));
            }

            // 附件
            HospitalSupplierQualificationAttachmentMapper mapper = getMapper(
                    HospitalSupplierQualificationAttachmentMapper.class);
            HospitalSupplierQualificationAttachmentExample example = new HospitalSupplierQualificationAttachmentExample();
            example.createCriteria().andParentEqualTo(item.getId());
            List<HospitalSupplierQualificationAttachment> attachments = mapper.selectByExample(example);

            qualification.setAttachments(attachments);

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
