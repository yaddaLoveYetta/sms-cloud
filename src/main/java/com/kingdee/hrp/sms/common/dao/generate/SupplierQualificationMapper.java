package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.SupplierQualification;
import com.kingdee.hrp.sms.common.model.SupplierQualificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2018/12/19
* @author le.xiao
*/
public interface SupplierQualificationMapper {
    long countByExample(SupplierQualificationExample example);

    int deleteByExample(SupplierQualificationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SupplierQualification record);

    int insertSelective(SupplierQualification record);

    List<SupplierQualification> selectByExample(SupplierQualificationExample example);

    SupplierQualification selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SupplierQualification record, @Param("example") SupplierQualificationExample example);

    int updateByExample(@Param("record") SupplierQualification record, @Param("example") SupplierQualificationExample example);

    int updateByPrimaryKeySelective(SupplierQualification record);

    int updateByPrimaryKey(SupplierQualification record);

    int batchInsert(@Param("list") List<SupplierQualification> list);

    int batchInsertSelective(@Param("list") List<SupplierQualification> list, @Param("selective") SupplierQualification.Column ... selective);
}