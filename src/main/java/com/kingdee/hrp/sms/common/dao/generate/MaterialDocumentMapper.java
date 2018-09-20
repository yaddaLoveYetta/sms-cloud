package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.MaterialDocument;
import com.kingdee.hrp.sms.common.model.MaterialDocumentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MaterialDocumentMapper {
    long countByExample(MaterialDocumentExample example);

    int deleteByExample(MaterialDocumentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MaterialDocument record);

    int insertSelective(MaterialDocument record);

    List<MaterialDocument> selectByExample(MaterialDocumentExample example);

    MaterialDocument selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MaterialDocument record, @Param("example") MaterialDocumentExample example);

    int updateByExample(@Param("record") MaterialDocument record, @Param("example") MaterialDocumentExample example);

    int updateByPrimaryKeySelective(MaterialDocument record);

    int updateByPrimaryKey(MaterialDocument record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_material_document
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<MaterialDocument> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_material_document
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<MaterialDocument> list, @Param("selective") MaterialDocument.Column ... selective);
}