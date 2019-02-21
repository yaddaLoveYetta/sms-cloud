package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.ApprovedSupplier;
import com.kingdee.hrp.sms.common.model.ApprovedSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface ApprovedSupplierMapper {
    long countByExample(ApprovedSupplierExample example);

    int deleteByExample(ApprovedSupplierExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ApprovedSupplier record);

    int insertSelective(ApprovedSupplier record);

    List<ApprovedSupplier> selectByExample(ApprovedSupplierExample example);

    ApprovedSupplier selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ApprovedSupplier record, @Param("example") ApprovedSupplierExample example);

    int updateByExample(@Param("record") ApprovedSupplier record, @Param("example") ApprovedSupplierExample example);

    int updateByPrimaryKeySelective(ApprovedSupplier record);

    int updateByPrimaryKey(ApprovedSupplier record);

    int batchInsert(@Param("list") List<ApprovedSupplier> list);

    int batchInsertSelective(@Param("list") List<ApprovedSupplier> list, @Param("selective") ApprovedSupplier.Column ... selective);
}