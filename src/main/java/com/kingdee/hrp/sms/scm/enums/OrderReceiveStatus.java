package com.kingdee.hrp.sms.scm.enums;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单收货状态
 *
 * @author le.xiao
 */
@Getter
@ToString
public enum OrderReceiveStatus {

    UN_RECEIVED(1, "未收货"), PART_RECEIVED(2, "部分收货"), RECEIVED(3, "已收货");

    private static Logger logger = LoggerFactory.getLogger(OrderDeliveryStatus.class);

    private Integer number;
    private String name;

    OrderReceiveStatus(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    public static OrderReceiveStatus getOrderReceiveStatus(Integer number) {
        for (OrderReceiveStatus orderReceiveStatus : OrderReceiveStatus.values()) {
            if (orderReceiveStatus.number.equals(number)) {
                return orderReceiveStatus;
            }
        }

        logger.error("OrderReceiveStatus不存状态值:" + number);
        throw new BusinessLogicRunTimeException("OrderReceiveStatus不存状态值:" + number);
    }

}
