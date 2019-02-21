package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.MaterialDocumentType;
import com.kingdee.hrp.sms.common.model.MaterialDocumentTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface MaterialDocumentTypeMapper {
    long countByExample(MaterialDocumentTypeExample example);

    int deleteByExample(MaterialDocumentTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MaterialDocumentType record);

    int insertSelective(MaterialDocumentType record);

    List<MaterialDocumentType> selectByExample(MaterialDocumentTypeExample example);

    MaterialDocumentType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MaterialDocumentType record, @Param("example") MaterialDocumentTypeExample example);

    int updateByExample(@Param("record") MaterialDocumentType record, @Param("example") MaterialDocumentTypeExample example);

    int updateByPrimaryKeySelective(MaterialDocumentType record);

    int updateByPrimaryKey(MaterialDocumentType record);

    int batchInsert(@Param("list") List<MaterialDocumentType> list);

    int batchInsertSelective(@Param("list") List<MaterialDocumentType> list, @Param("selective") MaterialDocumentType.Column ... selective);
}