package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.SystemSetting;
import com.kingdee.hrp.sms.common.model.SystemSettingExample;
import com.kingdee.hrp.sms.common.model.SystemSettingKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2018/12/19
* @author le.xiao
*/
public interface SystemSettingMapper {
    long countByExample(SystemSettingExample example);

    int deleteByExample(SystemSettingExample example);

    int deleteByPrimaryKey(SystemSettingKey key);

    int insert(SystemSetting record);

    int insertSelective(SystemSetting record);

    List<SystemSetting> selectByExample(SystemSettingExample example);

    SystemSetting selectByPrimaryKey(SystemSettingKey key);

    int updateByExampleSelective(@Param("record") SystemSetting record, @Param("example") SystemSettingExample example);

    int updateByExample(@Param("record") SystemSetting record, @Param("example") SystemSettingExample example);

    int updateByPrimaryKeySelective(SystemSetting record);

    int updateByPrimaryKey(SystemSetting record);

    int batchInsert(@Param("list") List<SystemSetting> list);

    int batchInsertSelective(@Param("list") List<SystemSetting> list, @Param("selective") SystemSetting.Column ... selective);
}