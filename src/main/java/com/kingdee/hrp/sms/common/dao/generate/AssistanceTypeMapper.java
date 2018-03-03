package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.AssistanceType;
import com.kingdee.hrp.sms.common.model.AssistanceTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssistanceTypeMapper {
    long countByExample(AssistanceTypeExample example);

    int deleteByExample(AssistanceTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AssistanceType record);

    int insertSelective(AssistanceType record);

    List<AssistanceType> selectByExample(AssistanceTypeExample example);

    AssistanceType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AssistanceType record, @Param("example") AssistanceTypeExample example);

    int updateByExample(@Param("record") AssistanceType record, @Param("example") AssistanceTypeExample example);

    int updateByPrimaryKeySelective(AssistanceType record);

    int updateByPrimaryKey(AssistanceType record);
}