package com.kingdee.hrp.sms.common.service.plugin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增单据时数据必录性校验结果
 * <p>
 * 根据数据模板配置进行必录性校验
 *
 * @author yadda
 */
@Setter
@Getter
@ToString(callSuper = true)
@Accessors(chain = true)
public class MustInputCheckResult {

    /**
     * 单据头数据校验结果-默认通过(true)
     */
    private boolean headCheckSuccess = true;
    /**
     * 单据体数据校验结果-默认通过(true)
     */
    private boolean bodyCheckSuccess = true;

    /**
     * 校验不通过时单据头校验错误信息
     */
    private List<String> headCheckError = new ArrayList<>(8);

    /**
     * 校验不通过时单据体校验结果
     */
    private List<String> bodyCheckError = new ArrayList<>(8);
}
