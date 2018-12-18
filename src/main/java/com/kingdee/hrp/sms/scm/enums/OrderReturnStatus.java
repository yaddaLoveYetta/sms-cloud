package com.kingdee.hrp.sms.scm.enums;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单退货状态
 * 值参考t_assistance表 type=47
 *
 * @author le.xiao
 */
@Getter
@ToString
public enum OrderReturnStatus {

    UN_RETURNED(1, "未退货"), PART_RETURNED(2, "部分退货"), RETURNED(3, "已退货");

    private static Logger logger = LoggerFactory.getLogger(OrderDeliveryStatus.class);

    private Integer number;
    private String name;

    OrderReturnStatus(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    public static OrderReturnStatus getOrderReturnStatus(Integer number) {
        for (OrderReturnStatus returnStatus : OrderReturnStatus.values()) {
            if (returnStatus.number.equals(number)) {
                return returnStatus;
            }
        }

        logger.error("OrderReturnStatus不存状态值:" + number);
        throw new BusinessLogicRunTimeException("OrderReturnStatus不存状态值:" + number);
    }
}
