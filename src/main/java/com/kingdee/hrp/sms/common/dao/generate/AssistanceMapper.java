package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.Assistance;
import com.kingdee.hrp.sms.common.model.AssistanceExample;
import com.kingdee.hrp.sms.common.model.AssistanceKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssistanceMapper {
    long countByExample(AssistanceExample example);

    int deleteByExample(AssistanceExample example);

    int deleteByPrimaryKey(AssistanceKey key);

    int insert(Assistance record);

    int insertSelective(Assistance record);

    List<Assistance> selectByExample(AssistanceExample example);

    Assistance selectByPrimaryKey(AssistanceKey key);

    int updateByExampleSelective(@Param("record") Assistance record, @Param("example") AssistanceExample example);

    int updateByExample(@Param("record") Assistance record, @Param("example") AssistanceExample example);

    int updateByPrimaryKeySelective(Assistance record);

    int updateByPrimaryKey(Assistance record);
}