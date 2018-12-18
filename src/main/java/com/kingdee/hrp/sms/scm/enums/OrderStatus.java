package com.kingdee.hrp.sms.scm.enums;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单基本状态
 * 值参考t_assistance表 type=44
 *
 * @author le.xiao
 */

@Getter
@ToString
public enum OrderStatus {

    /**
     * 新增
     */
    ADDED(1, "新增"),
    CHECKED(2, "已审核"),
    TO_BE_CONFIRMED(3, "待确认"),
    CONFIRMED(4, "已确认"),
    CANCELLED(5, "已取消");

    private static Logger logger = LoggerFactory.getLogger(OrderDeliveryStatus.class);

    private Integer number;
    private String name;

    OrderStatus(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    public int value() {
        return this.number;
    }

    public static OrderStatus getOrderStatus(Integer number) {

        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.number.equals(number)) {
                return orderStatus;
            }
        }

        logger.error("OrderStatus不存状态值:" + number);
        throw new BusinessLogicRunTimeException("OrderStatus不存状态值:" + number);
    }
}
