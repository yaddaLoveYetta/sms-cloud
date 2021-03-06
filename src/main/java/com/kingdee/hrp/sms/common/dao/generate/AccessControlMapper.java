package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.AccessControl;
import com.kingdee.hrp.sms.common.model.AccessControlExample;
import com.kingdee.hrp.sms.common.model.AccessControlKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface AccessControlMapper {
    long countByExample(AccessControlExample example);

    int deleteByExample(AccessControlExample example);

    int deleteByPrimaryKey(AccessControlKey key);

    int insert(AccessControl record);

    int insertSelective(AccessControl record);

    List<AccessControl> selectByExample(AccessControlExample example);

    AccessControl selectByPrimaryKey(AccessControlKey key);

    int updateByExampleSelective(@Param("record") AccessControl record, @Param("example") AccessControlExample example);

    int updateByExample(@Param("record") AccessControl record, @Param("example") AccessControlExample example);

    int updateByPrimaryKeySelective(AccessControl record);

    int updateByPrimaryKey(AccessControl record);

    int batchInsert(@Param("list") List<AccessControl> list);

    int batchInsertSelective(@Param("list") List<AccessControl> list, @Param("selective") AccessControl.Column ... selective);
}