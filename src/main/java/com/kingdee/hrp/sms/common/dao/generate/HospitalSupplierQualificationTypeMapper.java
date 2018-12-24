package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.HospitalSupplierQualificationType;
import com.kingdee.hrp.sms.common.model.HospitalSupplierQualificationTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2018/12/24
* @author le.xiao
*/
public interface HospitalSupplierQualificationTypeMapper {
    long countByExample(HospitalSupplierQualificationTypeExample example);

    int deleteByExample(HospitalSupplierQualificationTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalSupplierQualificationType record);

    int insertSelective(HospitalSupplierQualificationType record);

    List<HospitalSupplierQualificationType> selectByExample(HospitalSupplierQualificationTypeExample example);

    HospitalSupplierQualificationType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalSupplierQualificationType record, @Param("example") HospitalSupplierQualificationTypeExample example);

    int updateByExample(@Param("record") HospitalSupplierQualificationType record, @Param("example") HospitalSupplierQualificationTypeExample example);

    int updateByPrimaryKeySelective(HospitalSupplierQualificationType record);

    int updateByPrimaryKey(HospitalSupplierQualificationType record);

    int batchInsert(@Param("list") List<HospitalSupplierQualificationType> list);

    int batchInsertSelective(@Param("list") List<HospitalSupplierQualificationType> list, @Param("selective") HospitalSupplierQualificationType.Column ... selective);
}