package com.kingdee.hrp.sms.scm.controller;

import com.kingdee.hrp.sms.common.model.PurchaseOrder;
import com.kingdee.hrp.sms.scm.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单业务控制器
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/6/27 23:41
 */
@Controller
@RequestMapping(value = "/order/")
public class PurchaseOrderController {

    private static Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    private PurchaseOrderService orderService;

    /**
     * 获取一张订单信息
     *
     * @param orderId 订单内码
     * @return Order
     */
    @RequestMapping(value = "getOrder")
    @ResponseBody
    public PurchaseOrder getOrder(Long orderId) {

        Assert.notNull(orderId, "请提交正确的订单id");

/*        if (orderId == null || orderId <= 0) {
            throw new BusinessLogicRunTimeException("请提交正确的订单id");
        }
        return orderService.getPurchaseOrderModel(orderId);*/

        return null;

    }

    /**
     * 订单新增时获取订单编号
     *
     * @return 订单编号
     */
    @RequestMapping(value = "createOrderNumber")
    @ResponseBody
    public String createOrderNumber() {
        return orderService.createBillNumber();
    }
}
