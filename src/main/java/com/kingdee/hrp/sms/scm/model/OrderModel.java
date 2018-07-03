package com.kingdee.hrp.sms.scm.model;

import com.kingdee.hrp.sms.common.model.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 订单单据头数据
 * <p>
 * 继承com.kingdee.hrp.sms.common.model.Order主要补充一些引用类型的属性
 *
 * @author le.xiao
 */
@Getter
@Setter
@Accessors(chain = true)
public class OrderModel extends Order {

    /**
     * 医院名称
     */
    private String hospitalName;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 制单人名称
     */
    private String createrName;
    /**
     * 采购员名称
     */
    private String purchaserName;
    /**
     * 审核人名称
     */
    private String checkerName;
    /**
     * 订单状态名称
     */
    private String orderStatusName;
    /**
     * 发货状态名称
     */
    private String deliverStatusName;
    /**
     * 收货状态名称
     */
    private String receiveStatusName;
    /**
     * 退货状态名称
     */
    private  String returnStatusName;
    /**
     * 采购模式名称
     */
    private String purchaseTypeName;
}
