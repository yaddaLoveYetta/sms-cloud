package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.HospitalSupplier;
import com.kingdee.hrp.sms.common.model.HospitalSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2018/12/24
* @author le.xiao
*/
public interface HospitalSupplierMapper {
    long countByExample(HospitalSupplierExample example);

    int deleteByExample(HospitalSupplierExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HospitalSupplier record);

    int insertSelective(HospitalSupplier record);

    List<HospitalSupplier> selectByExample(HospitalSupplierExample example);

    HospitalSupplier selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HospitalSupplier record, @Param("example") HospitalSupplierExample example);

    int updateByExample(@Param("record") HospitalSupplier record, @Param("example") HospitalSupplierExample example);

    int updateByPrimaryKeySelective(HospitalSupplier record);

    int updateByPrimaryKey(HospitalSupplier record);

    int batchInsert(@Param("list") List<HospitalSupplier> list);

    int batchInsertSelective(@Param("list") List<HospitalSupplier> list, @Param("selective") HospitalSupplier.Column ... selective);
}