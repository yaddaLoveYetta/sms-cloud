package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.FormClassEntry;
import com.kingdee.hrp.sms.common.model.FormClassEntryExample;
import com.kingdee.hrp.sms.common.model.FormClassEntryKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface FormClassEntryMapper {
    long countByExample(FormClassEntryExample example);

    int deleteByExample(FormClassEntryExample example);

    int deleteByPrimaryKey(FormClassEntryKey key);

    int insert(FormClassEntry record);

    int insertSelective(FormClassEntry record);

    List<FormClassEntry> selectByExample(FormClassEntryExample example);

    FormClassEntry selectByPrimaryKey(FormClassEntryKey key);

    int updateByExampleSelective(@Param("record") FormClassEntry record, @Param("example") FormClassEntryExample example);

    int updateByExample(@Param("record") FormClassEntry record, @Param("example") FormClassEntryExample example);

    int updateByPrimaryKeySelective(FormClassEntry record);

    int updateByPrimaryKey(FormClassEntry record);

    int batchInsert(@Param("list") List<FormClassEntry> list);

    int batchInsertSelective(@Param("list") List<FormClassEntry> list, @Param("selective") FormClassEntry.Column ... selective);
}