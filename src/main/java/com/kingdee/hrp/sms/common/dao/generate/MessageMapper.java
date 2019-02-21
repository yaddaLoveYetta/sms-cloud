package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.Message;
import com.kingdee.hrp.sms.common.model.MessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface MessageMapper {
    long countByExample(MessageExample example);

    int deleteByExample(MessageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExample(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    int batchInsert(@Param("list") List<Message> list);

    int batchInsertSelective(@Param("list") List<Message> list, @Param("selective") Message.Column ... selective);
}