package com.kingdee.hrp.sms.common.service;

import com.kingdee.hrp.sms.util.SnowFlake;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author yadda
 */
@Service
public class BaseService {

    @Resource
    protected SqlSession sqlSession;

    /**
     * 新增记录主键生成策略
     * <p>
     * SnowFlake算法，会产生一个long类型不重复数字
     *
     * @return
     */
    protected Long getId() {
        return SnowFlake.getId(0, 0);
    }

}
