package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.SupplierQualificationType;
import com.kingdee.hrp.sms.common.model.SupplierQualificationTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface SupplierQualificationTypeMapper {
    long countByExample(SupplierQualificationTypeExample example);

    int deleteByExample(SupplierQualificationTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SupplierQualificationType record);

    int insertSelective(SupplierQualificationType record);

    List<SupplierQualificationType> selectByExample(SupplierQualificationTypeExample example);

    SupplierQualificationType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SupplierQualificationType record, @Param("example") SupplierQualificationTypeExample example);

    int updateByExample(@Param("record") SupplierQualificationType record, @Param("example") SupplierQualificationTypeExample example);

    int updateByPrimaryKeySelective(SupplierQualificationType record);

    int updateByPrimaryKey(SupplierQualificationType record);

    int batchInsert(@Param("list") List<SupplierQualificationType> list);

    int batchInsertSelective(@Param("list") List<SupplierQualificationType> list, @Param("selective") SupplierQualificationType.Column ... selective);
}