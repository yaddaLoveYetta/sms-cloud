package com.kingdee.hrp.sms.scm.model;

import com.kingdee.hrp.sms.common.model.Order;
import com.kingdee.hrp.sms.common.model.OrderEntry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author le.xiao
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class OrderModel implements Serializable {
    /**
     * 单据头
     */
    private Order header;
    /**
     * 单据体
     */
    private List<OrderEntry> entries;
}
