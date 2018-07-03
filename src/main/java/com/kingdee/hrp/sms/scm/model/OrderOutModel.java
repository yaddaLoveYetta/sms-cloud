package com.kingdee.hrp.sms.scm.model;

import java.util.List;

/**
 * 前端查询时返回订单模型
 * @author le.xiao
 */
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
