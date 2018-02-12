package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.RoleType;
import com.kingdee.hrp.sms.common.model.RoleTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
}