package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.RoleType;
import com.kingdee.hrp.sms.common.model.RoleTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface RoleTypeMapper {
    long countByExample(RoleTypeExample example);

    int deleteByExample(RoleTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoleType record);

    int insertSelective(RoleType record);

    List<RoleType> selectByExample(RoleTypeExample example);

    RoleType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RoleType record, @Param("example") RoleTypeExample example);

    int updateByExample(@Param("record") RoleType record, @Param("example") RoleTypeExample example);

    int updateByPrimaryKeySelective(RoleType record);

    int updateByPrimaryKey(RoleType record);

    int batchInsert(@Param("list") List<RoleType> list);

    int batchInsertSelective(@Param("list") List<RoleType> list, @Param("selective") RoleType.Column ... selective);
}