package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.FormFields;
import com.kingdee.hrp.sms.common.model.FormFieldsExample;
import com.kingdee.hrp.sms.common.model.FormFieldsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormFieldsMapper {
    long countByExample(FormFieldsExample example);

    int deleteByExample(FormFieldsExample example);

    int deleteByPrimaryKey(FormFieldsKey key);

    int insert(FormFields record);

    int insertSelective(FormFields record);

    List<FormFields> selectByExample(FormFieldsExample example);

    FormFields selectByPrimaryKey(FormFieldsKey key);

    int updateByExampleSelective(@Param("record") FormFields record, @Param("example") FormFieldsExample example);

    int updateByExample(@Param("record") FormFields record, @Param("example") FormFieldsExample example);

    int updateByPrimaryKeySelective(FormFields record);

    int updateByPrimaryKey(FormFields record);
}