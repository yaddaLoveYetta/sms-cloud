package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.PurchaseOrder;
import com.kingdee.hrp.sms.common.model.PurchaseOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface PurchaseOrderMapper {
    long countByExample(PurchaseOrderExample example);

    int deleteByExample(PurchaseOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PurchaseOrder record);

    int insertSelective(PurchaseOrder record);

    List<PurchaseOrder> selectByExample(PurchaseOrderExample example);

    PurchaseOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PurchaseOrder record, @Param("example") PurchaseOrderExample example);

    int updateByExample(@Param("record") PurchaseOrder record, @Param("example") PurchaseOrderExample example);

    int updateByPrimaryKeySelective(PurchaseOrder record);

    int updateByPrimaryKey(PurchaseOrder record);

    int batchInsert(@Param("list") List<PurchaseOrder> list);

    int batchInsertSelective(@Param("list") List<PurchaseOrder> list, @Param("selective") PurchaseOrder.Column ... selective);
}