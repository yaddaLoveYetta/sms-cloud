package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.PurchaseOrderEntry;
import com.kingdee.hrp.sms.common.model.PurchaseOrderEntryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface PurchaseOrderEntryMapper {
    long countByExample(PurchaseOrderEntryExample example);

    int deleteByExample(PurchaseOrderEntryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PurchaseOrderEntry record);

    int insertSelective(PurchaseOrderEntry record);

    List<PurchaseOrderEntry> selectByExample(PurchaseOrderEntryExample example);

    PurchaseOrderEntry selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PurchaseOrderEntry record, @Param("example") PurchaseOrderEntryExample example);

    int updateByExample(@Param("record") PurchaseOrderEntry record, @Param("example") PurchaseOrderEntryExample example);

    int updateByPrimaryKeySelective(PurchaseOrderEntry record);

    int updateByPrimaryKey(PurchaseOrderEntry record);

    int batchInsert(@Param("list") List<PurchaseOrderEntry> list);

    int batchInsertSelective(@Param("list") List<PurchaseOrderEntry> list, @Param("selective") PurchaseOrderEntry.Column ... selective);
}