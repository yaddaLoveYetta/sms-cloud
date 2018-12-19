package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.SupplierType;
import com.kingdee.hrp.sms.common.model.SupplierTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2018/12/19
* @author le.xiao
*/
public interface SupplierTypeMapper {
    long countByExample(SupplierTypeExample example);

    int deleteByExample(SupplierTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SupplierType record);

    int insertSelective(SupplierType record);

    List<SupplierType> selectByExample(SupplierTypeExample example);

    SupplierType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SupplierType record, @Param("example") SupplierTypeExample example);

    int updateByExample(@Param("record") SupplierType record, @Param("example") SupplierTypeExample example);

    int updateByPrimaryKeySelective(SupplierType record);

    int updateByPrimaryKey(SupplierType record);

    int batchInsert(@Param("list") List<SupplierType> list);

    int batchInsertSelective(@Param("list") List<SupplierType> list, @Param("selective") SupplierType.Column ... selective);
}