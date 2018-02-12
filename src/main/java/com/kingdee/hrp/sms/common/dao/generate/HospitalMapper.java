package com.kingdee.hrp.sms.common.dao.generate;

import com.kingdee.hrp.sms.common.model.Hospital;
import com.kingdee.hrp.sms.common.model.HospitalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HospitalMapper {
    long countByExample(HospitalExample example);

    int deleteByExample(HospitalExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Hospital record);

    int insertSelective(Hospital record);

    List<Hospital> selectByExample(HospitalExample example);

    Hospital selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Hospital record, @Param("example") HospitalExample example);

    int updateByExample(@Param("record") Hospital record, @Param("example") HospitalExample example);

    int updateByPrimaryKeySelective(Hospital record);

    int updateByPrimaryKey(Hospital record);
}