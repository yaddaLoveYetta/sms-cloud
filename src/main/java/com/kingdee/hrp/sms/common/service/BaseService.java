package com.kingdee.hrp.sms.common.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class BaseService {

    @Resource
    protected SqlSession sqlSession;

    /**
     * 新增单据时获取单据主键</br>
     * <p>
     * 该方法不可重复调用，即任何情况下两次调用的结果都不一致
     * <p>
     * <P>
     * 参考EAS单据内码产生规则
     * </p>
     *
     * @param bosType formClass中定义的bostype 长度必须为4或8,建议统一用8位，否则返回空
     * @return String 32位唯一字符串
     * @Title getId
     * @date 2017-05-06 14:42:39 星期六
     */
    protected String getId(String bosType) {


        return UUID.randomUUID().toString();

    }

}
