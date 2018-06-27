package com.kingdee.hrp.sms.scm.enums;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 采购模式
 *
 * @author yadda
 */

public enum PurchaseType {

    SALE_PROXY(1, "代销"), NO_SALE_PROXY(2, "非代销");

    private static Logger logger = LoggerFactory.getLogger(PurchaseType.class);

    private Integer number;
    private String name;

    PurchaseType(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    public static PurchaseType getPurchaseType(Integer number) {

        for (PurchaseType purchaseType : PurchaseType.values()) {
            if (purchaseType.number.equals(number)) {
                return purchaseType;
            }
        }

        logger.error("PurchaseType不存状态值:" + number);
        throw new BusinessLogicRunTimeException("PurchaseType不存状态值:" + number);
    }
}
