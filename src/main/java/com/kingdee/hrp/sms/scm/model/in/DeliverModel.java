package com.kingdee.hrp.sms.scm.model.in;

import java.util.List;

/**
 * 订单发货即生成发货单提交参数模型
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/7/5 21:46
 */
public class DeliverModel {

    /**
     * 待发货订单信息
     */
    List<DeliverEntryModel> orders;
}
