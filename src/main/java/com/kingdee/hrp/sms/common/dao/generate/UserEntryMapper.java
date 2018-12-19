package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.UserEntry;
import com.kingdee.hrp.sms.common.model.UserEntryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2018/12/19
* @author le.xiao
*/
public interface UserEntryMapper {
    long countByExample(UserEntryExample example);

    int deleteByExample(UserEntryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserEntry record);

    int insertSelective(UserEntry record);

    List<UserEntry> selectByExample(UserEntryExample example);

    UserEntry selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserEntry record, @Param("example") UserEntryExample example);

    int updateByExample(@Param("record") UserEntry record, @Param("example") UserEntryExample example);

    int updateByPrimaryKeySelective(UserEntry record);

    int updateByPrimaryKey(UserEntry record);

    int batchInsert(@Param("list") List<UserEntry> list);

    int batchInsertSelective(@Param("list") List<UserEntry> list, @Param("selective") UserEntry.Column ... selective);
}