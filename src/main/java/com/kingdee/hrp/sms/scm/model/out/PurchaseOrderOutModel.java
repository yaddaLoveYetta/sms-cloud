package com.kingdee.hrp.sms.scm.model.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 前端查询时返回订单模型
 *
 * @author le.xiao
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class PurchaseOrderOutModel implements Serializable {

    /**
     * 单据头数据
     */
    PurchaseOrderHeaderModel header;
    /**
     * 分录数据
     */
    List<PurchaseOrderEntryModel> entries;
}
