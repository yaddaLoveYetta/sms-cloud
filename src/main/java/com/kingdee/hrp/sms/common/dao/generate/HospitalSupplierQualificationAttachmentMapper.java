package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.HospitalSupplierQualificationAttachment;
import com.kingdee.hrp.sms.common.model.HospitalSupplierQualificationAttachmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface HospitalSupplierQualificationAttachmentMapper {
    long countByExample(HospitalSupplierQualificationAttachmentExample example);

    int deleteByExample(HospitalSupplierQualificationAttachmentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalSupplierQualificationAttachment record);

    int insertSelective(HospitalSupplierQualificationAttachment record);

    List<HospitalSupplierQualificationAttachment> selectByExample(HospitalSupplierQualificationAttachmentExample example);

    HospitalSupplierQualificationAttachment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalSupplierQualificationAttachment record, @Param("example") HospitalSupplierQualificationAttachmentExample example);

    int updateByExample(@Param("record") HospitalSupplierQualificationAttachment record, @Param("example") HospitalSupplierQualificationAttachmentExample example);

    int updateByPrimaryKeySelective(HospitalSupplierQualificationAttachment record);

    int updateByPrimaryKey(HospitalSupplierQualificationAttachment record);

    int batchInsert(@Param("list") List<HospitalSupplierQualificationAttachment> list);

    int batchInsertSelective(@Param("list") List<HospitalSupplierQualificationAttachment> list, @Param("selective") HospitalSupplierQualificationAttachment.Column ... selective);
}