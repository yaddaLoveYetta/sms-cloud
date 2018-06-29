package com.kingdee.hrp.sms.common.enums;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.scm.enums.OrderDeliveryStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息中心消息状态
 * <p>
 * 值跟t_assistance表 type=41一致
 *
 * @author le.xiao
 */
public enum MessageStatus {

    UN_PROCESSED(1, "未处理"), PROCESSED(2, "已处理");

    private static Logger logger = LoggerFactory.getLogger(OrderDeliveryStatus.class);

    private int number;
    private String name;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    MessageStatus(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public static MessageStatus getMessageStatus(int number) {

        for (MessageStatus d : MessageStatus.values()) {
            if (d.number == number) {
                return d;
            }
        }
        logger.error("MessageStatus不存状态值:" + number);
        throw new BusinessLogicRunTimeException("MessageStatus不存状态值:" + number);
    }

}