package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.SupplierQualificationType;
import com.kingdee.hrp.sms.common.model.SupplierQualificationTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier_qualification_type
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<SupplierQualificationType> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier_qualification_type
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<SupplierQualificationType> list, @Param("selective") SupplierQualificationType.Column ... selective);
}