package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.FormClass;
import com.kingdee.hrp.sms.common.model.FormClassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormClassMapper {
    long countByExample(FormClassExample example);

    int deleteByExample(FormClassExample example);

    int deleteByPrimaryKey(Integer classId);

    int insert(FormClass record);

    int insertSelective(FormClass record);

    List<FormClass> selectByExample(FormClassExample example);

    FormClass selectByPrimaryKey(Integer classId);

    int updateByExampleSelective(@Param("record") FormClass record, @Param("example") FormClassExample example);

    int updateByExample(@Param("record") FormClass record, @Param("example") FormClassExample example);

    int updateByPrimaryKeySelective(FormClass record);

    int updateByPrimaryKey(FormClass record);
}