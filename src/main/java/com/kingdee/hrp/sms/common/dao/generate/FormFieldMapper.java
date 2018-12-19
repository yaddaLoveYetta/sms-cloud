package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.FormField;
import com.kingdee.hrp.sms.common.model.FormFieldExample;
import com.kingdee.hrp.sms.common.model.FormFieldKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2018/12/19
* @author le.xiao
*/
public interface FormFieldMapper {
    long countByExample(FormFieldExample example);

    int deleteByExample(FormFieldExample example);

    int deleteByPrimaryKey(FormFieldKey key);

    int insert(FormField record);

    int insertSelective(FormField record);

    List<FormField> selectByExample(FormFieldExample example);

    FormField selectByPrimaryKey(FormFieldKey key);

    int updateByExampleSelective(@Param("record") FormField record, @Param("example") FormFieldExample example);

    int updateByExample(@Param("record") FormField record, @Param("example") FormFieldExample example);

    int updateByPrimaryKeySelective(FormField record);

    int updateByPrimaryKey(FormField record);

    int batchInsert(@Param("list") List<FormField> list);

    int batchInsertSelective(@Param("list") List<FormField> list, @Param("selective") FormField.Column ... selective);
}