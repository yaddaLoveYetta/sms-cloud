package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.FormField;
import com.kingdee.hrp.sms.common.model.FormFieldExample;
import com.kingdee.hrp.sms.common.model.FormFieldKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_form_field
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<FormField> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_form_field
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<FormField> list, @Param("selective") FormField.Column ... selective);
}