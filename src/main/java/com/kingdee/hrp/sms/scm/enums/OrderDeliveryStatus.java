package com.kingdee.hrp.sms.scm.enums;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单发货状态
 *
 * @author le.xiao
 */
@Getter
@ToString
public enum OrderDeliveryStatus {

    UN_SHIPPED(1, "未发货"), PART_SHIPPED(2, "部分发货"), SHIPPED(3, "已发货");

    private static Logger logger = LoggerFactory.getLogger(OrderDeliveryStatus.class);

    private Integer number;
    private String name;

    OrderDeliveryStatus(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    public static OrderDeliveryStatus getOrderDeliveryStatus(Integer number) {
        for (OrderDeliveryStatus deliveryStatus : OrderDeliveryStatus.values()) {
            if (deliveryStatus.number.equals(number)) {
                return deliveryStatus;
            }
        }

        logger.error("OrderDeliveryStatus不存状态值:" + number);
        throw new BusinessLogicRunTimeException("OrderDeliveryStatus不存状态值:" + number);
    }
}
