package com.kingdee.hrp.sms.scm.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 前端查询时返回订单模型
 *
 * @author le.xiao
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class OrderOutModel {

    /**
     * 单据头数据
     */
    OrderModel header;
    /**
     * 分录数据
     */
    List<OrderEntryModel> entries;
}
