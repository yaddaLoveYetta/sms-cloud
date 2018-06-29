package com.kingdee.hrp.sms.scm.service;

import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.common.model.Order;
import com.kingdee.hrp.sms.common.model.OrderExample;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.util.SessionUtil;
import com.sun.jdi.PrimitiveValue;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 订单业务实现
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/6/27 23:41
 */
public interface OrderService {

    SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    /**
     * 获取一张订单信息
     *
     * @param orderId 订单id
     * @return Order
     */
    Order getOrder(Long orderId);

    /**
     * 获取订单列表
     *
     * @param conditions 过滤条件
     * @param sorts      排序条件
     * @param pageSize   分页大小
     * @param pageNo     当前页码
     */
    List<Order> getOrders(List<Condition> conditions, List<Sort> sorts, Integer pageSize, Integer pageNo);

}
