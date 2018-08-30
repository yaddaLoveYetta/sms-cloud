package com.kingdee.hrp.sms.common.enums;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.scm.enums.OrderDeliveryStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 供应商向医院申请成为供应商的申请记录状态
 * <p>
 * 值跟t_assistance表 type=43一致
 *
 * @author le.xiao
 */

public enum CooperationApplyStatus {

    /**
     * 未处理状态的申请
     */
    UN_PROCESSED(1, "待处理"),
    /**
     * 同意
     */
    AGREE(2, "同意"),
    /**
     * 拒绝
     */
    DISAGREE(3, "拒绝");

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

    public int value() {
        return this.getNumber();
    }

    CooperationApplyStatus(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public static CooperationApplyStatus getCooperationApplyStatus(int number) {

        for (CooperationApplyStatus d : CooperationApplyStatus.values()) {
            if (d.number == number) {
                return d;
            }
        }
        logger.error("CooperationApplyStatus不存状态值:" + number);
        throw new BusinessLogicRunTimeException("CooperationApplyStatus不存状态值:" + number);
    }
}
