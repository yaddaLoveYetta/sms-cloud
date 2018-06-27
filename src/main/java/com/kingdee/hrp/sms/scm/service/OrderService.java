package com.kingdee.hrp.sms.scm.service;

import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.common.model.Order;
import com.kingdee.hrp.sms.common.model.OrderExample;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.util.SessionUtil;

import java.util.List;

/**
 * 订单业务实现
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/6/27 23:41
 */
public interface OrderService {
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


    /**
     * 订单通用条件
     * 主要是所属组织的数据过滤
     * <p>
     * 如果当前用户时系统管理员类型，不加组织条件
     *
     * @param orderExample OrderExample
     * @return OrderExample
     */
    default OrderExample getOrderExample(OrderExample orderExample) {

        if (SessionUtil.getUserRoleType() != UserRoleType.SYSTEM) {
            // 供应商或医院时加org条件
            if (orderExample.getOredCriteria().size() == 1) {
                // 只有一个Criteria，将org添加加入该Criteria
                orderExample.getOredCriteria().get(0).andOrgEqualTo(SessionUtil.getUserLinkOrg());
            } else {
                // 没有或多个Criteria，创建Criteria
                if (SessionUtil.getUserRoleType() != UserRoleType.SYSTEM) {
                    orderExample.createCriteria().andOrgEqualTo(SessionUtil.getUserLinkOrg());
                }
            }
        }

        return orderExample;
    }

    /**
     * 订单通用条件
     * 主要是所属组织的数据过滤
     *
     * @return OrderExample
     */
    default OrderExample getOrderExample() {

        OrderExample orderExample = new OrderExample();

        if (SessionUtil.getUserRoleType() != UserRoleType.SYSTEM) {
            // 供应商或医院时加org条件
            orderExample.createCriteria().andOrgEqualTo(SessionUtil.getUserLinkOrg());
        }

        return orderExample;
    }
}
