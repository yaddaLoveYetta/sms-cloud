package com.kingdee.hrp.sms.common.service;

import com.kingdee.hrp.sms.common.model.AccessControl;
import com.kingdee.hrp.sms.common.model.Role;
import com.kingdee.hrp.sms.system.menu.service.impl.MenuService;
import com.kingdee.hrp.sms.system.user.service.IUserService;
import com.kingdee.hrp.sms.util.Environ;
import com.kingdee.hrp.sms.util.SessionUtil;
import com.kingdee.hrp.sms.util.SnowFlake;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yadda
 */
@Service
public abstract class BaseService {

    private static Logger logger = LoggerFactory.getLogger(BaseService.class);

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
