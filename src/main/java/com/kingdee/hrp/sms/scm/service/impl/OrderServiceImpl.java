package com.kingdee.hrp.sms.scm.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.OrderMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.Order;
import com.kingdee.hrp.sms.common.model.OrderExample;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.common.service.plugin.PlugIn;
import com.kingdee.hrp.sms.scm.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yadda(silenceisok@163.com)
 * @date 2018/6/27 23:44
 */
@Service
public class OrderServiceImpl extends AbstractOrderService implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource(name = "plugInFactory")
    private PlugIn plugInFactory;

    /**
     * 获取一张订单信息
     *
     * @param orderId 订单id
     * @return Order
     */
    @Override
    public Order getOrder(Long orderId) {

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        OrderExample.Criteria criteria = getOrderExample();

        criteria.andIdEqualTo(orderId);
        List<Order> orders = orderMapper.selectByExample(criteria.example());

        if (CollectionUtils.isEmpty(orders)) {
            logger.error("不存在id为{}的订单", orderId);
            throw new BusinessLogicRunTimeException(String.format("不存id为[%s]的订单", orderId));
        }

        logger.info("getOrder,return：" + orders);

        return orders.get(0);
    }

    /**
     * 获取订单列表
     *
     * @param conditions 过滤条件
     * @param sorts      排序条件
     * @param pageSize   分页大小
     * @param pageNo     当前页码
     */
    @Override
    public List<Order> getOrders(List<Condition> conditions, List<Sort> sorts, Integer pageSize, Integer pageNo) {
        return null;
    }
}
