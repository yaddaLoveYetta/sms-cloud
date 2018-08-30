package com.kingdee.hrp.sms.common.enums;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.scm.enums.OrderDeliveryStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息中心消息类别
 * <p>
 * 值跟t_assistance表 type=40一致
 *
 * @author yadda
 */
public enum MessageType {

    /**
     * 供应商向医院发送的希望成为其供应商的申请
     */
    COOPERATION_APPLICATION(1, "供应商向医院发送的希望成为其供应商的申请"),
    /**
     * 医院处理了供应商的申请消息
     */
    COOPERATION_APPLICATION_PROCESSED(2, "医院处理了供应商发送成为其供应商的申请");

    private static Logger logger = LoggerFactory.getLogger(MessageType.class);

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

    public int value() {
        return this.number;
    }

    MessageType(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public static MessageType getMessageType(int number) {

        for (MessageType d : MessageType.values()) {
            if (d.number == number) {
                return d;
            }
        }
        logger.error("MessageType不存状态值:" + number);
        throw new BusinessLogicRunTimeException("MessageType不存状态值:" + number);
    }
}
