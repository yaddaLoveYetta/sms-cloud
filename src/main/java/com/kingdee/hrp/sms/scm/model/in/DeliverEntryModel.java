package com.kingdee.hrp.sms.scm.model.in;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 订单发货参数对象模型
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/7/5 21:50
 */
@Setter
@Getter
@Accessors(chain = true)
public class DeliverEntryModel {

    /**
     * 订单id（单据头id）
     */
    private Long id;
    /**
     * 订单分录id
     */
    private List<Long> childIds;
}
