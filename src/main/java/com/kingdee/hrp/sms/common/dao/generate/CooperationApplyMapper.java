package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.CooperationApply;
import com.kingdee.hrp.sms.common.model.CooperationApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator on 2018/12/24
* @author le.xiao
*/
public interface CooperationApplyMapper {
    long countByExample(CooperationApplyExample example);

    int deleteByExample(CooperationApplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CooperationApply record);

    int insertSelective(CooperationApply record);

    List<CooperationApply> selectByExample(CooperationApplyExample example);

    CooperationApply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CooperationApply record, @Param("example") CooperationApplyExample example);

    int updateByExample(@Param("record") CooperationApply record, @Param("example") CooperationApplyExample example);

    int updateByPrimaryKeySelective(CooperationApply record);

    int updateByPrimaryKey(CooperationApply record);

    int batchInsert(@Param("list") List<CooperationApply> list);

    int batchInsertSelective(@Param("list") List<CooperationApply> list, @Param("selective") CooperationApply.Column ... selective);
}