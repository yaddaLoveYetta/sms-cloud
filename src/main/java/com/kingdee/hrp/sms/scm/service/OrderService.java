package com.kingdee.hrp.sms.scm.service;

import com.kingdee.hrp.sms.common.model.Order;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.scm.model.OrderModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
     * 新增订单
     *
     * @param data 数据（严格按照单据模板匹配的数据）
     * @return 新增订单的id
     * @throws IOException
     */

    Long add(String data) throws IOException;

    /**
     * 修改订单数据
     *
     * @param id   订单内码
     * @param data 修改数据内容（严格按照单据模板匹配的数据）
     * @return 是否成功
     * @throws IOException
     */

    Boolean edit(Long id, String data) throws IOException;

    /**
     * 删除订单
     *
     * @param ids 删除的内码集合
     * @return 是否成功
     */

    Boolean delete(List<Long> ids);

    /**
     * 审核订单
     *
     * @param ids 内码集合
     * @return 是否成功
     */

    Boolean check(List<Long> ids);

    /**
     * 反审核订单
     *
     * @param ids 内码集合
     * @return 是否成功
     */

    Boolean unCheck(List<Long> ids);

}
