package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.FormAction;
import com.kingdee.hrp.sms.common.model.FormActionExample;
import com.kingdee.hrp.sms.common.model.FormActionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2018/12/24
* @author le.xiao
*/
public interface FormActionMapper {
    long countByExample(FormActionExample example);

    int deleteByExample(FormActionExample example);

    int deleteByPrimaryKey(FormActionKey key);

    int insert(FormAction record);

    int insertSelective(FormAction record);

    List<FormAction> selectByExample(FormActionExample example);

    FormAction selectByPrimaryKey(FormActionKey key);

    int updateByExampleSelective(@Param("record") FormAction record, @Param("example") FormActionExample example);

    int updateByExample(@Param("record") FormAction record, @Param("example") FormActionExample example);

    int updateByPrimaryKeySelective(FormAction record);

    int updateByPrimaryKey(FormAction record);

    int batchInsert(@Param("list") List<FormAction> list);

    int batchInsertSelective(@Param("list") List<FormAction> list, @Param("selective") FormAction.Column ... selective);
}