package com.kingdee.hrp.sms.scm.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.OrderEntryMapper;
import com.kingdee.hrp.sms.common.dao.generate.OrderMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.Order;
import com.kingdee.hrp.sms.common.model.OrderEntry;
import com.kingdee.hrp.sms.common.model.OrderEntryExample;
import com.kingdee.hrp.sms.common.model.OrderExample;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.common.service.TemplateService;
import com.kingdee.hrp.sms.common.service.plugin.PlugIn;
import com.kingdee.hrp.sms.scm.enums.OrderDeliveryStatus;
import com.kingdee.hrp.sms.scm.enums.OrderStatus;
import com.kingdee.hrp.sms.scm.model.out.OrderEntryModel;
import com.kingdee.hrp.sms.scm.model.out.OrderHeaderModel;
import com.kingdee.hrp.sms.scm.model.out.OrderModel;
import com.kingdee.hrp.sms.scm.model.out.OrderOutModel;
import com.kingdee.hrp.sms.scm.service.OrderService;
import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
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
    /**
     * 订单业务模板id
     */
    private static Integer CLASS_ID = 2001;

    @Resource(name = "plugInFactory")
    private PlugIn plugInFactory;

    @Resource
    private TemplateService templateService;

    /**
     * 根据模板获取一张订单信息
     *
     * @param orderId 订单id
     * @return Order
     */
    @Override
    public Map<String, Object> getOrderByTemplate(Long orderId) {

        Map<String, Object> item = templateService.getItemById(2001, orderId, null);

        // item转成order返回模型
        //OrderOutModel order = convert(item);

        return item;
    }

    /**
     * 获取一张订单信息
     * 没有引用类型字段的关联信息
     *
     * @param orderId 订单id
     * @return Order
     */
    @Override
    public OrderModel getOrder(Long orderId) {

        OrderModel orderModel = new OrderModel();

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        OrderExample.Criteria criteria = getOrderExample();
        criteria.andIdEqualTo(orderId);

        List<Order> orders = orderMapper.selectByExample(criteria.example());

        if (CollectionUtils.isEmpty(orders)) {
            logger.error("不存在id为{}的订单");
            throw new BusinessLogicRunTimeException(String.format("不存在id为%s的订单!", orderId));
        }
        // 只可能有一单
        Order order = orders.get(0);

        OrderEntryMapper orderEntryMapper = sqlSession.getMapper(OrderEntryMapper.class);
        OrderEntryExample orderEntryExample = new OrderEntryExample();
        orderEntryExample.createCriteria().andParentEqualTo(orderId);
        // 按照行号排序
        orderEntryExample.setOrderByClause(OrderEntry.Column.sequence.asc());

        List<OrderEntry> entries = orderEntryMapper.selectByExample(orderEntryExample);

        orderModel.setHeader(order).setEntries(entries);

        return orderModel;
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

        OrderHeaderModel header = new OrderHeaderModel();
        List<OrderEntryModel> entries = new ArrayList<>();

        for (OrderHeaderModel.FieldKeyLinkedColumn column : OrderHeaderModel.FieldKeyLinkedColumn.values()) {
            String fieldName = column.getJavaProperty();
            String fieldKey = column.getFieldKey();

            // 数据库值
            Object value = item.get(fieldKey);

            Field field = FieldUtils.getField(Order.class, fieldName, true);

            try {
                FieldUtils.writeField(field, header, value, true);
            } catch (IllegalAccessException e) {
                logger.error("通用查询结果转换OrderHeaderModel对象出错:" + e.getMessage(), e);
                throw new BusinessLogicRunTimeException("通用查询结果转换OrderHeaderModel对象出错:" + e.getMessage(), e);
            }
        }

        // 打包分錄
        List<Map<String, Object>> entryList = (List<Map<String, Object>>) ((Map<String, Object>) item.get("entry"))
                .get("1");

        for (Map<String, Object> entry : entryList) {
            // entry是一条订单分录数据

            OrderEntryModel orderEntryModel = new OrderEntryModel();

            for (Map.Entry<String, Object> entryItem : entry.entrySet()) {
                // entryItem是一个字段信息

                // 模板key
                String fieldKey = entryItem.getKey();
                // 数据库值
                Object value = entryItem.getValue();

                // 模板fieldKey转换成OrderEntryModel属性信息
                OrderEntryModel.FieldKeyLinkedColumn column = OrderEntryModel.FieldKeyLinkedColumn
                        .getFieldKeyLinkedColumn(fieldKey);

                // OrderEntryModel属性名
                String fieldName = column.getJavaProperty();

                Field field = FieldUtils.getField(Order.class, fieldName, true);

                try {
                    FieldUtils.writeField(field, orderEntryModel, value, true);
                } catch (IllegalAccessException e) {
                    logger.error("通用查询结果转换OrderEntryModel对象出错:" + e.getMessage(), e);
                    throw new BusinessLogicRunTimeException("通用查询结果转换OrderEntryModel对象出错:" + e.getMessage(), e);
                }
            }

            entries.add(orderEntryModel);
        }

        orderOutModel.setHeader(header).setEntries(entries);

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
    public Map<String, Object> getOrdersByTemplate(List<Condition> conditions, List<Sort> sorts, Integer pageSize,
                                                   Integer pageNo) {

        return templateService.getItems(CLASS_ID, conditions, sorts, pageSize, pageNo);
    }

    /**
     * 新增订单
     *
     * @param data 数据（严格按照单据模板匹配的数据）
     * @return 新增订单的id
     * @throws IOException
     */
    @Override
    public Long add(String data) throws IOException {

        return templateService.addItem(CLASS_ID, data);

    }

    /**
     * 修改订单数据
     *
     * @param id   订单内码
     * @param data 修改数据内容（严格按照单据模板匹配的数据）
     * @return 是否成功
     * @throws IOException
     */
    @Override
    public Boolean edit(Long id, String data) throws IOException {

        // 校验订单状态是否是新增状态，只有新增的订单可以修改 TODO

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        Order order = orderMapper.selectByPrimaryKey(id);

        if (null == order) {
            logger.error("id：{}订单不存在", id);
            throw new BusinessLogicRunTimeException("订单不存在" + id);
        }

        if (OrderStatus.getOrderStatus(order.getOrderStatus()) != OrderStatus.ADDED) {
            logger.error("订单{}非新增状态，不可编辑", id);
            throw new BusinessLogicRunTimeException("只可编辑新增状态的新单");
        }

        return templateService.editItem(CLASS_ID, id, data);
    }

    /**
     * 删除订单
     *
     * @param ids 删除的内码集合
     * @return 是否成功
     */
    @Override
    public Boolean delete(List<Long> ids) {

        // 校验订单状态是否是新增状态，只有新增的订单可以删除
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        OrderExample orderExample = new OrderExample();
        // 只有新增状态的订单可删除
        orderExample.createCriteria().andIdIn(ids).andOrderStatusEqualTo(OrderStatus.ADDED.getNumber());

        List<Order> orders = orderMapper.selectByExample(orderExample);

        if (orders.size() != ids.size()) {
            // 选择要删除的订单中有非新增状态的，本次删除操作不处理
            logger.error("只有新增状态的订单可删除，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有新增状态的订单可删除，本次操作失败，请检查选中订单的状态");
        }

        return templateService.delItem(CLASS_ID, ids);
    }

    /**
     * 审核订单
     *
     * @param ids 内码集合
     * @return 是否成功
     */
    @Override
    public Boolean check(List<Long> ids) {

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        // 校验订单状态是否是新增状态，只有新增的订单可以审核
        OrderExample orderExample = new OrderExample();
        // 只有新增状态的订单可删除
        orderExample.createCriteria().andIdIn(ids).andOrderStatusEqualTo(OrderStatus.ADDED.getNumber());

        List<Order> orders = orderMapper.selectByExample(orderExample);

        if (orders.size() != ids.size()) {
            // 选择要审核的订单中有非新增状态的，本次审核操作不处理
            logger.error("只有新增状态的订单可审核，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有新增状态的订单可审核，本次操作失败，请检查选中订单的状态");
        }


        Order order = new Order();

        for (Long id : ids) {
            order.setId(id);
            order.setOrderStatus(OrderStatus.CHECKED.getNumber());
            orderMapper.updateByPrimaryKeySelective(order);
        }

        return true;
    }

    /**
     * 反审核订单
     *
     * @param ids 内码集合
     * @return 是否成功
     */
    @Override
    public Boolean unCheck(List<Long> ids) {

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        // 校验订单状态是否是已审核，只有已审核的订单可以反审核
        OrderExample orderExample = new OrderExample();
        // 只有新增状态的订单可删除
        orderExample.createCriteria().andIdIn(ids).andOrderStatusEqualTo(OrderStatus.CHECKED.getNumber());

        List<Order> orders = orderMapper.selectByExample(orderExample);

        if (orders.size() != ids.size()) {
            // 选择要审核的订单中有非新增状态的，本次审核操作不处理
            logger.error("只有已审核状态的订单可反审核，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有已审核状态的订单可审核，本次操作失败，请检查选中订单的状态");
        }

        Order order = new Order();

        for (Long id : ids) {
            order.setId(id);
            // 反审核后订单回到新增状态
            order.setOrderStatus(OrderStatus.ADDED.getNumber());
            orderMapper.updateByPrimaryKeySelective(order);
        }

        return true;
    }

    /**
     * make the order see by supplier
     * <p>
     * change the order status to pre confirmed
     *
     * @param ids list of orderId
     * @return true if send success else false
     */
    @Override
    public Boolean sendToSupplier(List<Long> ids) {

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        // 校验订单状态是否是已审核，只有已审核的订单可以发送给供应商
        OrderExample orderExample = new OrderExample();
        // 只有新增状态的订单可删除
        orderExample.createCriteria().andIdIn(ids).andOrderStatusEqualTo(OrderStatus.CHECKED.getNumber());

        List<Order> orders = orderMapper.selectByExample(orderExample);

        if (orders.size() != ids.size()) {
            // 选择要审核的订单中有非新增状态的，本次审核操作不处理
            logger.error("只有已审核状态的订单可发送给供应商，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有已审核状态的订单可发送给供应商，本次操作失败，请检查选中订单的状态");
        }

        Order order = new Order();

        for (Long id : ids) {
            order.setId(id);
            // 反审核后订单回到新增状态
            order.setOrderStatus(OrderStatus.TO_BE_CONFIRMED.getNumber());
            orderMapper.updateByPrimaryKeySelective(order);
        }

        return true;

    }

    /**
     * change the order status to confirmed
     *
     * @param ids list of orderId
     * @return true if send success else false
     */
    @Override
    public Boolean confirm(List<Long> ids) {

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        // 校验订单状态是否是待确认，只有待确认的订单可以确认
        OrderExample orderExample = new OrderExample();
        // 只有待确认状态的订单可确认
        orderExample.createCriteria().andIdIn(ids).andOrderStatusEqualTo(OrderStatus.TO_BE_CONFIRMED.getNumber());

        List<Order> orders = orderMapper.selectByExample(orderExample);

        if (orders.size() != ids.size()) {
            // 选择要审核的订单中有非新增状态的，本次审核操作不处理
            logger.error("只有待确认状态的订单可确认，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有待确认状态的订单可确认，本次操作失败，请检查选中订单的状态");
        }

        Order order = new Order();

        for (Long id : ids) {
            order.setId(id);
            // 确认后订单状态到已确认
            order.setOrderStatus(OrderStatus.CONFIRMED.getNumber());
            orderMapper.updateByPrimaryKeySelective(order);
        }

        return true;
    }

    /**
     * change the order status back to checked
     *
     * @param ids list of orderId
     * @return true if send success else false
     */
    @Override
    public Boolean unConfirm(List<Long> ids) {

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);

        // 校验订单状态是否是已确认且未发货，只有已确认且未发货的订单可以反确认
        OrderExample orderExample = new OrderExample();
        // 只有已确认且未发货状态的订单可反确认
        orderExample.createCriteria().andIdIn(ids).andOrderStatusEqualTo(OrderStatus.CONFIRMED.getNumber())
                .andDeliverStatusEqualTo(OrderDeliveryStatus.UN_SHIPPED.getNumber());

        List<Order> orders = orderMapper.selectByExample(orderExample);

        if (orders.size() != ids.size()) {
            // 选择要反确认的订单中有非确认状态或已发货的，本次审核操作不处理
            logger.error("只有已确认状态且未发货的订单可反确认，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有已确认状态且未发货的订单可反确认，本次操作失败，请检查选中订单的状态");
        }

        Order order = new Order();

        for (Long id : ids) {
            order.setId(id);
            // 确认后订单状态到已确认
            order.setOrderStatus(OrderStatus.CONFIRMED.getNumber());
            orderMapper.updateByPrimaryKeySelective(order);
        }

        return true;
    }

    /**
     * create deliver order from order
     *
     * @param deliverOrder order which deal to create deliver order
     * @return a temp deliver order info which is not save in database yet
     */
    @Override
    public Map<String, Object> deliver(Map<String, Object> deliverOrder) {
        return null;
    }
}
