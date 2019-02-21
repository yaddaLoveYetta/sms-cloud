package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.SupplierQualificationAttachment;
import com.kingdee.hrp.sms.common.model.SupplierQualificationAttachmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface SupplierQualificationAttachmentMapper {
    long countByExample(SupplierQualificationAttachmentExample example);

    int deleteByExample(SupplierQualificationAttachmentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SupplierQualificationAttachment record);

    int insertSelective(SupplierQualificationAttachment record);

    List<SupplierQualificationAttachment> selectByExample(SupplierQualificationAttachmentExample example);

    SupplierQualificationAttachment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SupplierQualificationAttachment record, @Param("example") SupplierQualificationAttachmentExample example);

    int updateByExample(@Param("record") SupplierQualificationAttachment record, @Param("example") SupplierQualificationAttachmentExample example);

    int updateByPrimaryKeySelective(SupplierQualificationAttachment record);

    int updateByPrimaryKey(SupplierQualificationAttachment record);

    int batchInsert(@Param("list") List<SupplierQualificationAttachment> list);

    int batchInsertSelective(@Param("list") List<SupplierQualificationAttachment> list, @Param("selective") SupplierQualificationAttachment.Column ... selective);
}