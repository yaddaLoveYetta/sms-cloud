package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.Material;
import com.kingdee.hrp.sms.common.model.MaterialExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MaterialMapper {
    long countByExample(MaterialExample example);

    int deleteByExample(MaterialExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Material record);

    int insertSelective(Material record);

    List<Material> selectByExample(MaterialExample example);

    Material selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Material record, @Param("example") MaterialExample example);

    int updateByExample(@Param("record") Material record, @Param("example") MaterialExample example);

    int updateByPrimaryKeySelective(Material record);

    int updateByPrimaryKey(Material record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_material
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<Material> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_material
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<Material> list, @Param("selective") Material.Column ... selective);
}