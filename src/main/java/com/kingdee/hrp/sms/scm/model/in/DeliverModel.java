package com.kingdee.hrp.sms.scm.model.in;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 订单发货即生成发货单提交参数模型
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/7/5 21:46
 */
@Getter
@Setter
@Accessors(chain = true)
public class DeliverModel {

    /**
     * 待发货订单信息
     */
    List<DeliverEntryModel> orders;
}
