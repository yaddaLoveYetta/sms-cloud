package com.kingdee.hrp.sms.scm.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.OrderMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.Order;
import com.kingdee.hrp.sms.common.model.OrderExample;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.common.service.TemplateService;
import com.kingdee.hrp.sms.common.service.plugin.PlugIn;
import com.kingdee.hrp.sms.scm.model.OrderEntryModel;
import com.kingdee.hrp.sms.scm.model.OrderModel;
import com.kingdee.hrp.sms.scm.model.OrderOutModel;
import com.kingdee.hrp.sms.scm.service.OrderService;
import com.kingdee.hrp.sms.util.DBFieldFormatHelper;
import com.kingdee.hrp.sms.util.Environ;
import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

/*        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        OrderExample.Criteria criteria = getOrderExample();

        criteria.andIdEqualTo(orderId);
        List<Order> orders = orderMapper.selectByExample(criteria.example());

        if (CollectionUtils.isEmpty(orders)) {
            logger.error("不存在id为{}的订单", orderId);
            throw new BusinessLogicRunTimeException(String.format("不存id为[%s]的订单", orderId));
        }

        logger.info("getOrder,return：" + orders);*/

        TemplateService templateService = Environ.getBean(TemplateService.class);
        Map<String, Object> item = templateService.getItemById(2001, orderId, null);

        // item转成order返回模型
        OrderOutModel order = convert(item);

        return null;
    }

    /**
     * 通用查询订单转成OrderOutModel对象
     *
     * @param item Map<String, Object>
     * @return OrderOutModel
     */
    @SuppressWarnings("unchecked")
    private OrderOutModel convert(Map<String, Object> item) {

        OrderOutModel orderOutModel = new OrderOutModel();

        OrderModel header = new OrderModel();
        List<OrderEntryModel> entries = new ArrayList<>();

        orderOutModel.setHeader(header).setEntries(entries);

        for (OrderModel.FieldKeyLinkedColumn column : OrderModel.FieldKeyLinkedColumn.values()) {
            String fieldName = column.getJavaProperty();
            String fieldKey = column.getFieldKey();

            // 数据库值
            Object value = item.get(fieldKey);

            Field field = FieldUtils.getField(Order.class, fieldName, true);

            try {
                FieldUtils.writeField(field, header, value, true);
            } catch (IllegalAccessException e) {
                logger.error("通用查询结果转换Order对象出错:" + e.getMessage(), e);
                throw new BusinessLogicRunTimeException("通用查询结果转换Order对象出错:" + e.getMessage(), e);
            }
        }

        // 打包分錄
        List<Map<String, Object>> entryList = (List<Map<String, Object>>) ((Map<String, Object>) item.get("entry")).get("1");

        for (Map<String, Object> entryItem : entryList) {
            // entryItem是一條分錄
            for (Map.Entry<String, Object> field : entryItem.entrySet()) {

                String fieldKey = field.getKey();
                Object value = field.getValue();

                OrderModel.FieldKeyLinkedColumn column = OrderModel.FieldKeyLinkedColumn.getFieldKeyLinkedColumn(fieldKey);
            }
        }

        return orderOutModel;
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

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        OrderExample.Criteria criteria = getOrderExample();

        // 解析条件
        for (Condition condition : conditions) {

            Condition.LinkType linkType = condition.getLinkType();
            String leftParenTheses = condition.getLeftParenTheses();
            String fieldKey = condition.getFieldKey();
            Condition.LogicOperator logicOperator = condition.getLogicOperator();
            Object value = condition.getValue();
            String rightParenTheses = condition.getRightParenTheses();
            Boolean needConvert = condition.getNeedConvert();

            String fieldName = DBFieldFormatHelper.changeColumnToFieldName(fieldKey);

            if ("numer".equalsIgnoreCase(fieldKey)) {
                // 订单号过滤
                switch (logicOperator) {
                    case EQUAL:
                        criteria.andNumberEqualTo(value.toString());
                        break;
                    default:
                        criteria.andNumberEqualTo(value.toString());
                }
            }
            //Order.Column
        }


        //parseConditions(criteria);
        return null;
    }
}
