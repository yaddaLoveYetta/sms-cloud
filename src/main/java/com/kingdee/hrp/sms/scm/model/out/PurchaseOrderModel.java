package com.kingdee.hrp.sms.scm.model.out;

import com.kingdee.hrp.sms.common.model.PurchaseOrder;
import com.kingdee.hrp.sms.common.model.PurchaseOrderEntry;
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
public class PurchaseOrderModel implements Serializable {
    /**
     * 单据头
     */
    private PurchaseOrder header;
    /**
     * 单据体
     */
    private List<PurchaseOrderEntry> entries;
}
