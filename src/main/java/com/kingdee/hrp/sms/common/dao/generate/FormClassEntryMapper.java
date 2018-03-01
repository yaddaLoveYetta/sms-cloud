package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.FormClassEntry;
import com.kingdee.hrp.sms.common.model.FormClassEntryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormClassEntryMapper {
    long countByExample(FormClassEntryExample example);

    int deleteByExample(FormClassEntryExample example);

    int deleteByPrimaryKey(Integer classId);

    int insert(FormClassEntry record);

    int insertSelective(FormClassEntry record);

    List<FormClassEntry> selectByExample(FormClassEntryExample example);

    FormClassEntry selectByPrimaryKey(Integer classId);

    int updateByExampleSelective(@Param("record") FormClassEntry record, @Param("example") FormClassEntryExample example);

    int updateByExample(@Param("record") FormClassEntry record, @Param("example") FormClassEntryExample example);

    int updateByPrimaryKeySelective(FormClassEntry record);

    int updateByPrimaryKey(FormClassEntry record);
}