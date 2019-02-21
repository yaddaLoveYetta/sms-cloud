package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.HospitalSupplierQualification;
import com.kingdee.hrp.sms.common.model.HospitalSupplierQualificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface HospitalSupplierQualificationMapper {
    long countByExample(HospitalSupplierQualificationExample example);

    int deleteByExample(HospitalSupplierQualificationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalSupplierQualification record);

    int insertSelective(HospitalSupplierQualification record);

    List<HospitalSupplierQualification> selectByExample(HospitalSupplierQualificationExample example);

    HospitalSupplierQualification selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalSupplierQualification record, @Param("example") HospitalSupplierQualificationExample example);

    int updateByExample(@Param("record") HospitalSupplierQualification record, @Param("example") HospitalSupplierQualificationExample example);

    int updateByPrimaryKeySelective(HospitalSupplierQualification record);

    int updateByPrimaryKey(HospitalSupplierQualification record);

    int batchInsert(@Param("list") List<HospitalSupplierQualification> list);

    int batchInsertSelective(@Param("list") List<HospitalSupplierQualification> list, @Param("selective") HospitalSupplierQualification.Column ... selective);
}