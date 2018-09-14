package com.kingdee.hrp.sms.scm.service.impl;

import com.kingdee.hrp.sms.common.dao.generate.PurchaseOrderEntryMapper;
import com.kingdee.hrp.sms.common.dao.generate.PurchaseOrderMapper;
import com.kingdee.hrp.sms.common.enums.Constants;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.PurchaseOrder;
import com.kingdee.hrp.sms.common.model.PurchaseOrderEntry;
import com.kingdee.hrp.sms.common.model.PurchaseOrderEntryExample;
import com.kingdee.hrp.sms.common.model.PurchaseOrderExample;
import com.kingdee.hrp.sms.common.pojo.Condition;
import com.kingdee.hrp.sms.common.pojo.Sort;
import com.kingdee.hrp.sms.common.service.TemplateService;
import com.kingdee.hrp.sms.common.service.plugin.PlugIn;
import com.kingdee.hrp.sms.scm.enums.OrderDeliveryStatus;
import com.kingdee.hrp.sms.scm.enums.OrderStatus;
import com.kingdee.hrp.sms.scm.model.in.DeliverEntryModel;
import com.kingdee.hrp.sms.scm.model.in.DeliverModel;
import com.kingdee.hrp.sms.scm.model.out.PurchaseOrderEntryModel;
import com.kingdee.hrp.sms.scm.model.out.PurchaseOrderHeaderModel;
import com.kingdee.hrp.sms.scm.model.out.PurchaseOrderModel;
import com.kingdee.hrp.sms.scm.model.out.PurchaseOrderOutModel;
import com.kingdee.hrp.sms.scm.service.PurchaseOrderService;
import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author yadda(silenceisok@163.com)
 * @date 2018/6/27 23:44
 */
@Service
public class PurchaseOrderServiceImpl extends AbstractOrderService implements PurchaseOrderService {

    private static Logger logger = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);
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
    public Map<String, Object> getPurchaseOrderByTemplate(Long orderId) {

        Map<String, Object> item = templateService.getItemById(2001, orderId, null);

        // item转成order返回模型
        //PurchaseOrderOutModel order = convert(item);

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
    public PurchaseOrderModel getPurchaseOrderModel(Long orderId) {

        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();

        PurchaseOrderMapper purchaseOrderMapper = sqlSession.getMapper(PurchaseOrderMapper.class);

        PurchaseOrderExample.Criteria criteria = getPurchaseOrderExample();
        criteria.andIdEqualTo(orderId);

        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByExample(criteria.example());

        if (CollectionUtils.isEmpty(purchaseOrders)) {
            logger.error("不存在id为{}的订单");
            throw new BusinessLogicRunTimeException(String.format("不存在id为%s的订单!", orderId));
        }
        // 只可能有一单
        PurchaseOrder purchaseOrder = purchaseOrders.get(0);

        PurchaseOrderEntryMapper purchaseOrderEntryMapper = sqlSession.getMapper(PurchaseOrderEntryMapper.class);
        PurchaseOrderEntryExample purchaseOrderEntryExample = new PurchaseOrderEntryExample();
        purchaseOrderEntryExample.createCriteria().andParentEqualTo(orderId);
        // 按照行号排序
        purchaseOrderEntryExample.setOrderByClause(PurchaseOrderEntry.Column.sequence.asc());

        List<PurchaseOrderEntry> entries = purchaseOrderEntryMapper.selectByExample(purchaseOrderEntryExample);

        purchaseOrderModel.setHeader(purchaseOrder).setEntries(entries);

        return purchaseOrderModel;
    }

    /**
     * 获取一张订单信息
     * <p>
     * 可指定获取的分录
     *
     * @param orderId  订单id
     * @param detailId 订单分录id，可多个
     * @return PurchaseOrderModel
     */
    @Override
    public PurchaseOrderModel getPurchaseOrderModel(Long orderId, Long... detailId) {

        return getPurchaseOrderModel(orderId, Arrays.asList(detailId));
    }

    /**
     * 获取一张订单信息
     * <p>
     * 可指定获取的分录
     *
     * @param orderId   订单id
     * @param detailIds 订单分录id，可多个
     * @return PurchaseOrderModel
     */
    @Override
    public PurchaseOrderModel getPurchaseOrderModel(Long orderId, List<Long> detailIds) {

        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();

        PurchaseOrderMapper purchaseOrderMapper = sqlSession.getMapper(PurchaseOrderMapper.class);

        PurchaseOrderExample.Criteria criteria = getPurchaseOrderExample();
        criteria.andIdEqualTo(orderId);

        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByExample(criteria.example());

        if (CollectionUtils.isEmpty(purchaseOrders)) {
            logger.error("不存在id为{}的订单");
            throw new BusinessLogicRunTimeException(String.format("不存在id为%s的订单!", detailIds));
        }
        // 只可能有一单
        PurchaseOrder purchaseOrder = purchaseOrders.get(0);

        PurchaseOrderEntryMapper purchaseOrderEntryMapper = sqlSession.getMapper(PurchaseOrderEntryMapper.class);
        PurchaseOrderEntryExample purchaseOrderEntryExample = new PurchaseOrderEntryExample();
        purchaseOrderEntryExample.createCriteria().andParentEqualTo(orderId).andIdIn(detailIds);
        // 按照行号排序
        purchaseOrderEntryExample.setOrderByClause(PurchaseOrderEntry.Column.sequence.asc());

        List<PurchaseOrderEntry> entries = purchaseOrderEntryMapper.selectByExample(purchaseOrderEntryExample);

        purchaseOrderModel.setHeader(purchaseOrder).setEntries(entries);

        return purchaseOrderModel;
    }

    /**
     * 订单表头信息
     * 不存在返回null
     *
     * @param orderId 订单id
     * @return Order
     */
    @Override
    public PurchaseOrder getPurchaseOrderHeader(Long orderId) {

        PurchaseOrderMapper purchaseOrderMapper = sqlSession.getMapper(PurchaseOrderMapper.class);
        return purchaseOrderMapper.selectByPrimaryKey(orderId);

    }

    /**
     * 订单表体信息
     * 不存在返回null
     *
     * @param orderId 订单id
     * @return List<OrderEntry>
     */
    @Override
    public List<PurchaseOrderEntry> getPurchaseOrderEntries(Long orderId) {

        PurchaseOrderEntryMapper purchaseOrderEntryMapper = sqlSession.getMapper(PurchaseOrderEntryMapper.class);

        PurchaseOrderEntryExample purchaseOrderEntryExample = new PurchaseOrderEntryExample();

        purchaseOrderEntryExample.createCriteria().andParentEqualTo(orderId);

        List<PurchaseOrderEntry> purchaseOrderEntries = purchaseOrderEntryMapper.selectByExample(purchaseOrderEntryExample);

        if (CollectionUtils.isEmpty(purchaseOrderEntries)) {
            return null;
        }

        return purchaseOrderEntries;
    }

    /**
     * 一条订单分录信息
     * 不存在返回null
     *
     * @param orderId  订单id
     * @param detailId 订单分录id
     * @return OrderEntry
     */
    @Override
    public PurchaseOrderEntry getPurchaseOrderEntry(Long orderId, Long detailId) {

        PurchaseOrderEntryMapper purchaseOrderEntryMapper = sqlSession.getMapper(PurchaseOrderEntryMapper.class);

        PurchaseOrderEntryExample purchaseOrderEntryExample = new PurchaseOrderEntryExample();

        purchaseOrderEntryExample.createCriteria().andParentEqualTo(orderId).andIdEqualTo(detailId);

        List<PurchaseOrderEntry> purchaseOrderEntries = purchaseOrderEntryMapper.selectByExample(purchaseOrderEntryExample);

        if (!CollectionUtils.isEmpty(purchaseOrderEntries)) {
            return purchaseOrderEntries.get(0);
        }

        return null;

    }

    /**
     * 一条订单分录信息
     * 不存在返回null
     *
     * @param detailId 订单分录id
     * @return OrderEntry
     */
    @Override
    public PurchaseOrderEntry getPurchaseOrderEntry(Long detailId) {

        PurchaseOrderEntryMapper purchaseOrderEntryMapper = sqlSession.getMapper(PurchaseOrderEntryMapper.class);

        return purchaseOrderEntryMapper.selectByPrimaryKey(detailId);

    }

    /**
     * 通用查询订单转成OrderOutModel对象
     *
     * @param item Map<String, Object>
     * @return PurchaseOrderOutModel
     */
    @SuppressWarnings("unchecked")
    private PurchaseOrderOutModel convert(Map<String, Object> item) {

        PurchaseOrderOutModel purchaseOrderOutModel = new PurchaseOrderOutModel();

        PurchaseOrderHeaderModel header = new PurchaseOrderHeaderModel();
        List<PurchaseOrderEntryModel> entries = new ArrayList<>();

        for (PurchaseOrderHeaderModel.FieldKeyLinkedColumn column : PurchaseOrderHeaderModel.FieldKeyLinkedColumn.values()) {
            String fieldName = column.getJavaProperty();
            String fieldKey = column.getFieldKey();

            // 数据库值
            Object value = item.get(fieldKey);

            Field field = FieldUtils.getField(PurchaseOrder.class, fieldName, true);

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

            PurchaseOrderEntryModel orderEntryModel = new PurchaseOrderEntryModel();

            for (Map.Entry<String, Object> entryItem : entry.entrySet()) {
                // entryItem是一个字段信息

                // 模板key
                String fieldKey = entryItem.getKey();
                // 数据库值
                Object value = entryItem.getValue();

                // 模板fieldKey转换成OrderEntryModel属性信息
                PurchaseOrderEntryModel.FieldKeyLinkedColumn column = PurchaseOrderEntryModel.FieldKeyLinkedColumn
                        .getFieldKeyLinkedColumn(fieldKey);

                // OrderEntryModel属性名
                String fieldName = column.getJavaProperty();

                Field field = FieldUtils.getField(PurchaseOrder.class, fieldName, true);

                try {
                    FieldUtils.writeField(field, orderEntryModel, value, true);
                } catch (IllegalAccessException e) {
                    logger.error("通用查询结果转换OrderEntryModel对象出错:" + e.getMessage(), e);
                    throw new BusinessLogicRunTimeException("通用查询结果转换OrderEntryModel对象出错:" + e.getMessage(), e);
                }
            }

            entries.add(orderEntryModel);
        }

        purchaseOrderOutModel.setHeader(header).setEntries(entries);

        return purchaseOrderOutModel;
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
    public Map<String, Object> getPurchaseOrdersByTemplate(List<Condition> conditions, List<Sort> sorts, Integer pageSize,
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

        PurchaseOrderMapper purchaseOrderMapper = sqlSession.getMapper(PurchaseOrderMapper.class);

        PurchaseOrder purchaseOrder = purchaseOrderMapper.selectByPrimaryKey(id);

        if (null == purchaseOrder) {
            logger.error("id：{}订单不存在", id);
            throw new BusinessLogicRunTimeException("订单不存在" + id);
        }

        if (OrderStatus.getOrderStatus(purchaseOrder.getOrderStatus()) != OrderStatus.ADDED) {
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
        PurchaseOrderMapper purchaseOrderMapper = sqlSession.getMapper(PurchaseOrderMapper.class);

        PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();
        // 只有新增状态的订单可删除
        purchaseOrderExample.createCriteria().andIdIn(ids).andOrderStatusEqualTo(OrderStatus.ADDED.getNumber());

        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByExample(purchaseOrderExample);

        if (purchaseOrders.size() != ids.size()) {
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

        PurchaseOrderMapper purchaseOrderMapper = sqlSession.getMapper(PurchaseOrderMapper.class);

        // 校验订单状态是否是新增状态，只有新增的订单可以审核
        PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();
        // 只有新增状态的订单可删除
        purchaseOrderExample.createCriteria().andIdIn(ids).andOrderStatusEqualTo(OrderStatus.ADDED.getNumber());

        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByExample(purchaseOrderExample);

        if (purchaseOrders.size() != ids.size()) {
            // 选择要审核的订单中有非新增状态的，本次审核操作不处理
            logger.error("只有新增状态的订单可审核，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有新增状态的订单可审核，本次操作失败，请检查选中订单的状态");
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        for (Long id : ids) {
            purchaseOrder.setId(id);
            purchaseOrder.setOrderStatus(OrderStatus.CHECKED.getNumber());
            purchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
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

        PurchaseOrderMapper purchaseOrderMapper = sqlSession.getMapper(PurchaseOrderMapper.class);

        // 校验订单状态是否是已审核，只有已审核的订单可以反审核
        PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();
        // 只有新增状态的订单可删除
        purchaseOrderExample.createCriteria().andIdIn(ids).andOrderStatusEqualTo(OrderStatus.CHECKED.getNumber());

        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByExample(purchaseOrderExample);

        if (purchaseOrders.size() != ids.size()) {
            // 选择要审核的订单中有非新增状态的，本次审核操作不处理
            logger.error("只有已审核状态的订单可反审核，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有已审核状态的订单可审核，本次操作失败，请检查选中订单的状态");
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        for (Long id : ids) {
            purchaseOrder.setId(id);
            // 反审核后订单回到新增状态
            purchaseOrder.setOrderStatus(OrderStatus.ADDED.getNumber());
            purchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
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

        PurchaseOrderMapper purchaseOrderMapper = sqlSession.getMapper(PurchaseOrderMapper.class);

        // 校验订单状态是否是已审核，只有已审核的订单可以发送给供应商
        PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();

        PurchaseOrderExample.Criteria criteria = purchaseOrderExample.createCriteria();

        if (getUserRoleType() != Constants.UserRoleType.SYSTEM && debug) {
            criteria.andHospitalEqualTo(getUserLinkOrg());
        }

        // 只有新增状态的订单可发送给供应商
        criteria.andIdIn(ids).andOrderStatusEqualTo(OrderStatus.CHECKED.getNumber());

        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByExample(purchaseOrderExample);

        if (purchaseOrders.size() != ids.size()) {
            // 选择要审核的订单中有非新增状态的，本次审核操作不处理
            logger.error("只有已审核状态的订单可发送给供应商，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有已审核状态的订单可发送给供应商，本次操作失败，请检查选中订单的状态");
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        for (Long id : ids) {
            purchaseOrder.setId(id);
            // 反审核后订单回到新增状态
            purchaseOrder.setOrderStatus(OrderStatus.TO_BE_CONFIRMED.getNumber());
            purchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
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

        PurchaseOrderMapper purchaseOrderMapper = sqlSession.getMapper(PurchaseOrderMapper.class);

        // 校验订单状态是否是待确认，只有待确认的订单可以确认
        PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();

        PurchaseOrderExample.Criteria criteria = purchaseOrderExample.createCriteria();

        if (getUserRoleType() != Constants.UserRoleType.SYSTEM && debug) {
            criteria.andHospitalEqualTo(getUserLinkOrg());
        }

        // 只有待确认状态的订单可确认
        criteria.andIdIn(ids).andOrderStatusEqualTo(OrderStatus.TO_BE_CONFIRMED.getNumber());

        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByExample(purchaseOrderExample);

        if (purchaseOrders.size() != ids.size()) {
            // 选择要审核的订单中有非新增状态的，本次审核操作不处理
            logger.error("只有待确认状态的订单可确认，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有待确认状态的订单可确认，本次操作失败，请检查选中订单的状态");
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        for (Long id : ids) {
            purchaseOrder.setId(id);
            // 确认后订单状态到已确认
            purchaseOrder.setOrderStatus(OrderStatus.CONFIRMED.getNumber());
            purchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
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

        PurchaseOrderMapper purchaseOrderMapper = sqlSession.getMapper(PurchaseOrderMapper.class);

        // 校验订单状态是否是已确认且未发货，只有已确认且未发货的订单可以反确认
        PurchaseOrderExample purchaseOrderExample = new PurchaseOrderExample();

        PurchaseOrderExample.Criteria criteria = purchaseOrderExample.createCriteria();

        if (getUserRoleType() != Constants.UserRoleType.SYSTEM && debug) {
            criteria.andHospitalEqualTo(getUserLinkOrg());
        }

        // 只有已确认且未发货状态的订单可反确认
        criteria.andIdIn(ids).andOrderStatusEqualTo(OrderStatus.CONFIRMED.getNumber())
                .andDeliverStatusEqualTo(OrderDeliveryStatus.UN_SHIPPED.getNumber());

        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByExample(purchaseOrderExample);

        if (purchaseOrders.size() != ids.size()) {
            // 选择要反确认的订单中有非确认状态或已发货的，本次审核操作不处理
            logger.error("只有已确认状态且未发货的订单可反确认，本次操作失败，请检查选中订单的状态ids:{}", ids);
            throw new BusinessLogicRunTimeException("只有已确认状态且未发货的订单可反确认，本次操作失败，请检查选中订单的状态");
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        for (Long id : ids) {
            purchaseOrder.setId(id);
            // 确认后订单状态到已确认
            purchaseOrder.setOrderStatus(OrderStatus.CONFIRMED.getNumber());
            purchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
        }

        return true;
    }

    /**
     * create deliver order from order
     *
     * @param deliverModel order which deal to create deliver order
     * @return a temp deliver order info which is not save in database yet
     */
    @Override
    public Map<String, Object> deliver(DeliverModel deliverModel) {

        // 需要发货的订单详细信息
        List<PurchaseOrderModel> toBeDeliverOrders = new ArrayList<>();

        // 查询出待发货的订单信息
        for (DeliverEntryModel deliverEntryModel : deliverModel.getOrders()) {
            //deliverEntryModel 是一张待发货的订单信息

            Long orderId = deliverEntryModel.getId();
            List<Long> childIds = deliverEntryModel.getChildId();

            PurchaseOrderModel purchaseOrderModel = getPurchaseOrderModel(orderId, childIds);

            toBeDeliverOrders.add(purchaseOrderModel);
        }

        if (CollectionUtils.isEmpty(toBeDeliverOrders)) {
            throw new BusinessLogicRunTimeException("没有待发货的订单");
        }

        // 一条订单分录生成一条发货单分录--不支持相同物料合并逻辑(复杂度高 TODO)
        for (PurchaseOrderModel toBeDeliverOrder : toBeDeliverOrders) {

        }
        return null;
    }

    /**
     * 创建订单编号
     *
     * @return 订单编号
     */
    @Override
    public String createBillNumber() {
        return createPurchaseOrderNumber();
    }
}
