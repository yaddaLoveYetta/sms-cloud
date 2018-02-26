package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.FormAction;
import com.kingdee.hrp.sms.common.model.FormActionExample;
import com.kingdee.hrp.sms.common.model.FormActionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
}