package com.kingdee.hrp.sms.scm.service;

import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.scm.model.out.OrderModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 订单业务实现
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/6/27 23:41
 */
public interface OrderService {

    /**
     * 获取一张订单信息
     * <p>
     * 只查询order与orderEntry表数据，不获取引用类型字段的关联信息
     *
     * @param orderId 订单id
     * @return OrderModel
     */
    OrderModel getOrder(Long orderId);

    /**
     * 通过模板查询一张订单信息
     *
     * @param orderId 订单id
     * @return 根据模板构建的返回结构
     */
    Map<String, Object> getOrderByTemplate(Long orderId);

    /**
     * 通过模板获取订单列表
     *
     * @param conditions 过滤条件
     * @param sorts      排序条件
     * @param pageSize   分页大小
     * @param pageNo     当前页码
     * @return Map<String, Object>
     */
    Map<String, Object> getOrdersByTemplate(List<Condition> conditions, List<Sort> sorts, Integer pageSize,
                                            Integer pageNo);

    /**
     * add a new order
     *
     * @param data order data of json string type（严格按照单据模板匹配的数据）
     * @return orderId of the new order
     * @throws IOException
     */

    Long add(String data) throws IOException;

    /**
     * modify order info
     *
     * @param id   one orderId
     * @param data order data of json string type（严格按照单据模板匹配的数据）
     * @return true if send success else false
     * @throws IOException
     */

    Boolean edit(Long id, String data) throws IOException;

    /**
     * delete order
     * <p>
     * remove the order from database
     *
     * @param ids list of orderId
     * @return true if send success else false
     */

    Boolean delete(List<Long> ids);

    /**
     * check the order
     * <p>
     * change the order status to checked
     *
     * @param ids list of orderId
     * @return true if send success else false
     */

    Boolean check(List<Long> ids);

    /**
     * uncheck the order
     * <p>
     * change the order status back to added
     *
     * @param ids list of orderId
     * @return true if send success else false
     */

    Boolean unCheck(List<Long> ids);

    /**
     * 发送订单给供应商
     *
     * @param ids list of orderId
     * @return true if send success else false
     */
    Boolean sendToSupplier(List<Long> ids);

    /**
     * change the order status to confirmed
     *
     * @param ids list of orderId
     * @return true if send success else false
     */
    Boolean confirm(List<Long> ids);

    /**
     * change the order status back to checked
     *
     * @param ids list of orderId
     * @return true if send success else false
     */
    Boolean unConfirm(List<Long> ids);

    /**
     * create deliver order from order
     *
     * @param deliverOrder order which deal to create deliver order
     * @return a temp deliver order info which is not save in database yet
     */
    Map<String, Object> deliver(Map<String, Object> deliverOrder);

}
