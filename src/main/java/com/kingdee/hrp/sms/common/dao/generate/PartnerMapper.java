package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.Partner;
import com.kingdee.hrp.sms.common.model.PartnerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2019/02/21
* 请不要改动此文件
* @author le.xiao
*/
public interface PartnerMapper {
    long countByExample(PartnerExample example);

    int deleteByExample(PartnerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Partner record);

    int insertSelective(Partner record);

    List<Partner> selectByExample(PartnerExample example);

    Partner selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Partner record, @Param("example") PartnerExample example);

    int updateByExample(@Param("record") Partner record, @Param("example") PartnerExample example);

    int updateByPrimaryKeySelective(Partner record);

    int updateByPrimaryKey(Partner record);

    int batchInsert(@Param("list") List<Partner> list);

    int batchInsertSelective(@Param("list") List<Partner> list, @Param("selective") Partner.Column ... selective);
}