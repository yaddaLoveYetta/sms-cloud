package com.kingdee.hrp.sms.common.service.plugin.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.eas.hrp.sms.dao.customize.SendcargoDaoMapper;
import com.kingdee.eas.hrp.sms.dao.generate.ItemMapper;
import com.kingdee.eas.hrp.sms.dao.generate.OrderEntryMapper;
import com.kingdee.eas.hrp.sms.exception.BusinessLogicRunTimeException;
import com.kingdee.eas.hrp.sms.model.Item;
import com.kingdee.eas.hrp.sms.model.OrderEntry;
import com.kingdee.eas.hrp.sms.model.OrderEntryExample;
import com.kingdee.eas.hrp.sms.model.OrderEntryExample.Criteria;
import com.kingdee.eas.hrp.sms.service.api.ITemplateService;
import com.kingdee.eas.hrp.sms.service.api.IWebService;
import com.kingdee.eas.hrp.sms.service.plugin.PlugInAdpter;
import com.kingdee.eas.hrp.sms.service.plugin.PlugInRet;
import com.kingdee.eas.hrp.sms.util.Environ;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单据插件
 *
 * @author yadda
 * @ClassName BillPlugin
 * @date 2017-05-18 17:49:52 星期四
 */
public class BillPlugin extends PlugInAdpter {

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public PlugInRet afterSave(int classId, String id, JSONObject data) {
        if (classId == 2020) {
            ITemplateService temp = Environ.getBean(ITemplateService.class);
            ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> item = temp.getItemById(classId, id);
            Map<String, Object> entrys = (Map<String, Object>) item.get("entry");
            ArrayList<Object> arrayList = (ArrayList<Object>) entrys.get("1");
            for (int i = 0; i < arrayList.size(); i++) {
                Map<String, Object> entry = new HashMap<String, Object>();
                HashMap<String, Object> invoiceEntry = (HashMap<String, Object>) arrayList.get(i);
                if (i == 0) {
                    entry.put("seq", invoiceEntry.get("orderSeq"));
                    entry.put("parent", invoiceEntry.get("orderId"));
                    entry.put("invoiceQty", invoiceEntry.get("actualQty"));
                    list.add(entry);
                } else {
                    // 判斷
                    BigDecimal qty = (BigDecimal) invoiceEntry.get("actualQty");

                    String orderId = (String) invoiceEntry.get("orderId");
                    int seq = Integer.parseInt(invoiceEntry.get("orderSeq").toString());

                    entry = isInList(list, orderId, seq);

                    if (null != entry) {
                        // 在不在list
                        entry.put("invoiceQty", qty.add((BigDecimal) entry.get("invoiceQty")));
                    }
                    if (entry == null) {
                        Map<String, Object> entry1 = new HashMap<String, Object>();
                        entry1.put("seq", invoiceEntry.get("orderSeq"));
                        entry1.put("parent", invoiceEntry.get("orderId"));
                        entry1.put("invoiceQty", invoiceEntry.get("actualQty"));
                        list.add(entry1);
                    }

                }
            }
            OrderEntry orderEntry = new OrderEntry();
            SqlSession sqlSession = (SqlSession) Environ.getBean("sqlSession");
            OrderEntryMapper orderEntryMapper = (OrderEntryMapper) sqlSession.getMapper(OrderEntryMapper.class);
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> lists = list.get(i);
                OrderEntryExample e = new OrderEntryExample();
                Criteria c = e.createCriteria();
                c.andSeqEqualTo(Integer.parseInt(lists.get("seq").toString()));
                c.andParentEqualTo(lists.get("parent").toString());
                // 根据订单号和行号查询对应的记录
                List<OrderEntry> o = orderEntryMapper.selectByExample(e);
                if (o.size() > 0) {
                    orderEntry.setInvoiceQty(
                            new BigDecimal(lists.get("invoiceQty").toString()).add(o.get(0).getInvoiceQty()));
                    orderEntry.setId(o.get(0).getId());
                    // 根据订单ID 修改发货数量
                    orderEntryMapper.updateByPrimaryKeySelective(orderEntry);
                }
            }
        }

        return super.afterSave(classId, id, data);
    }

    private Map<String, Object> isInList(List<Map<String, Object>> list, String currentOrderId, int currentSeq) {

        for (Map<String, Object> item : list) {

            String orderId = (String) item.get("parent");
            int seq = Integer.parseInt(item.get("seq").toString());

            if (orderId.equals(currentOrderId) && seq == currentSeq) {
                return item;
            }
        }
        return null;
    }

    @Override
    public PlugInRet beforeSave(int classId, Map<String, Object> formData, JSONObject data) {

        if (classId == 2020) {
            String logistics = data.getString("logistics");
            String logisticsNo = data.getString("logisticsNo");
            // modify by yadda--此校验无意义--快递100?
            // if (!logistics.equals("") && logistics != null) {
            // if (logistics.matches("^[0-9]*$")) {
            // throw new BusinessLogicRunTimeException("物流公司名称格式错误");
            // }
            // }
            if (!logisticsNo.equals("") && logisticsNo != null) {
                if (logisticsNo != null && !logisticsNo.equals("")) {
                    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                    Matcher m = p.matcher(logisticsNo);
                    if (m.find()) {
                        throw new BusinessLogicRunTimeException("物流单号不能包含中文，请重新输入");
                    }
                }
            }
            JSONObject entry = data.getJSONObject("entry");
            JSONArray array = entry.getJSONArray("1");
            if (array == null || array.equals("")) {
                throw new BusinessLogicRunTimeException("发货单列表中缺少需要发货的商品");
            }
            System.out.println(array.size());
            for (int i = 0; i < array.size(); i++) {
                JSONObject entrys = array.getJSONObject(i);
                JSONObject datas = entrys.getJSONObject("data");
                if (datas == null || datas.equals("")) {
                    throw new BusinessLogicRunTimeException("发货单列表中缺少需要发货的商品");
                }
                String actualQty = datas.getString("actualQty");// 实发数量
                if (actualQty.matches("^[-\\+]?[.\\d]*$")) {
                    System.out.println(actualQty.matches("^[-\\+]?[.\\d]*$"));
                } else {
                    throw new BusinessLogicRunTimeException("发货数量格式不正确");
                }
                String orderSeq = datas.getString("orderSeq");
                String orderId = datas.getString("orderId");
                String lot = datas.getString("lot");// 批次
                String dyBatchNum = datas.getString("dyBatchNum");// 批号
                Date effectiveDate = datas.getDate("effectiveDate");// 有效期
                Date dyProDate = datas.getDate("dyProDate");// 生产日期
                SqlSession sqlSession = (SqlSession) Environ.getBean("sqlSession");
                ItemMapper itemMapper = (ItemMapper) sqlSession.getMapper(ItemMapper.class);
                Item items = itemMapper.selectByPrimaryKey(datas.getString("material"));
                OrderEntryMapper orderEntryMapper = sqlSession.getMapper(OrderEntryMapper.class);
                OrderEntryExample e = new OrderEntryExample();
                Criteria c = e.createCriteria();
                c.andSeqEqualTo(Integer.parseInt(orderSeq));
                c.andParentEqualTo(orderId);
                List<OrderEntry> o = orderEntryMapper.selectByExample(e);
                if (dyProDate != null && !dyProDate.equals("") && effectiveDate != null && !effectiveDate.equals("")) {
                    if (dyProDate.after(effectiveDate)) {
                        throw new BusinessLogicRunTimeException("生产日期不能大于有效期");
                    }
                }
                String code = datas.getString("code");
                if (actualQty.equals("") || actualQty == null) {
                    throw new BusinessLogicRunTimeException("实发数量不能为空");
                }
                if (actualQty.equals("0")) {
                    throw new BusinessLogicRunTimeException("实发数量不能0");
                }
                if (new BigDecimal(actualQty).compareTo(o.get(0).getQty().subtract(o.get(0).getInvoiceQty())) > 0) {
                    throw new BusinessLogicRunTimeException("发货数量不能大于应发数量");
                }
                if (items.getIsLotNumber() != null) {
                    if (items.getIsLotNumber().equals("1") || items.getIsLotNumber() == 1) {
                        if (lot.equals("") || lot == null) {
                            throw new BusinessLogicRunTimeException("批次不能为空");
                        }
                        if (dyBatchNum.equals("") || dyBatchNum == null) {
                            throw new BusinessLogicRunTimeException("批号不能为空");
                        }
                    }
                }
                if (items.getHighConsumable() != null) {
                    if (items.getHighConsumable().equals("1") || items.getHighConsumable() == 1) {
                        if (code.equals("") || code == null) {
                            throw new BusinessLogicRunTimeException("高值物料个体码不能为空");
                        }
                    }
                }
                if (effectiveDate.equals("") || effectiveDate == null) {
                    throw new BusinessLogicRunTimeException("有效期不能为空");
                }
            }
        }
        PlugInRet ret = new PlugInRet();
        ret.setCode(200);
        ret.setData(data);
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public PlugInRet beforeModify(int classId, String id, Map<String, Object> formData, JSONObject data) {
        if (classId == 2020) {
            String logisticsNo = data.getString("logisticsNo");
            if (logisticsNo != null && !logisticsNo.equals("")) {
                Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(logisticsNo);
                if (m.find()) {
                    throw new BusinessLogicRunTimeException("物流单号不能包含中文，请重新输入");
                }
            }
            JSONObject entry = data.getJSONObject("entry");
            JSONArray array = entry.getJSONArray("1");
            if (array == null || array.equals("")) {
                throw new BusinessLogicRunTimeException("发货单列表中缺少需要发货的商品");
            }
            if (array == null || array.equals("")) {
                throw new BusinessLogicRunTimeException("发货单列表中缺少需要发货的商品");
            }
            System.out.println(array.size());
            for (int i = 0; i < array.size(); i++) {
                JSONObject entrys = array.getJSONObject(i);
                JSONObject datas = entrys.getJSONObject("data");
                if (datas == null || datas.equals("")) {
                    throw new BusinessLogicRunTimeException("发货单列表中缺少需要发货的商品");
                }
                String actualQty = datas.getString("actualQty");// 实发数量
                if (!actualQty.matches("^[-\\+]?[.\\d]*$")) {
                    throw new BusinessLogicRunTimeException("发货数量格式不正确");
                }
                String orderSeq = datas.getString("orderSeq");
                String orderId = datas.getString("orderId");
                String lot = datas.getString("lot");// 批次
                String dyBatchNum = datas.getString("dyBatchNum");// 批号
                Date effectiveDate = datas.getDate("effectiveDate");// 有效期
                Date dyProDate = datas.getDate("dyProDate");// 生产日期
                SqlSession sqlSession = (SqlSession) Environ.getBean("sqlSession");
                ItemMapper itemMapper = (ItemMapper) sqlSession.getMapper(ItemMapper.class);
                OrderEntryMapper orderEntryMapper = sqlSession.getMapper(OrderEntryMapper.class);
                SendcargoDaoMapper sendcargoDaoMapper = sqlSession.getMapper(SendcargoDaoMapper.class);
                Map<String, Object> sendcargo = sendcargoDaoMapper.selectEntryByParent(datas.getString("entryId"));
                OrderEntryExample e = new OrderEntryExample();
                Criteria c = e.createCriteria();
                c.andSeqEqualTo(Integer.parseInt(orderSeq));
                c.andParentEqualTo(orderId);
                List<OrderEntry> o = orderEntryMapper.selectByExample(e);
                Item items = itemMapper.selectByPrimaryKey(datas.getString("material"));
                if (dyProDate != null && !dyProDate.equals("") && effectiveDate != null && !effectiveDate.equals("")) {
                    if (dyProDate.after(effectiveDate)) {
                        throw new BusinessLogicRunTimeException("生产日期不能大于有效期");
                    }
                }
                String code = datas.getString("code");
                if (actualQty.equals("") || actualQty == null) {
                    throw new BusinessLogicRunTimeException("实发数量不能为空");
                }
                if (actualQty.equals("0")) {
                    throw new BusinessLogicRunTimeException("实发数量不能0");
                }

                if (o.get(0).getQty().compareTo(
                        o.get(0).getInvoiceQty().subtract(new BigDecimal(sendcargo.get("actualQty").toString()))
                                .add(new BigDecimal(actualQty))) < 0) {
                    throw new BusinessLogicRunTimeException("发货数量不能大于应发数量");
                }

                if (items.getIsLotNumber() != null) {
                    if (items.getIsLotNumber().equals("1") || items.getIsLotNumber() == 1) {
                        if (lot.equals("") || lot == null) {
                            throw new BusinessLogicRunTimeException("批次不能为空");
                        }
                        if (dyBatchNum.equals("") || dyBatchNum == null) {
                            throw new BusinessLogicRunTimeException("有批次的物料,批号不能为空");
                        }
                    }
                }
                if (items.getHighConsumable() != null) {
                    if (items.getHighConsumable().equals("1") || items.getHighConsumable() == 1) {
                        if (code.equals("") || code == null) {
                            throw new BusinessLogicRunTimeException("高值物料个体码不能为空");
                        }
                    }
                }
                if (effectiveDate.equals("") || effectiveDate == null) {
                    throw new BusinessLogicRunTimeException("有效期不能为空");
                }
            }

            boolean fl = false; // 表示有没有可用分录
            for (int i = 0; i < array.size(); i++) {
                JSONObject entrys = array.getJSONObject(i);
                if (entrys.get("flag").equals("1") || entrys.get("flag").equals("2")) {
                    fl = true;
                    break;
                }
            }
            if (!fl) {
                throw new BusinessLogicRunTimeException("没有可用分录");
            }
            ITemplateService temp = Environ.getBean(ITemplateService.class);
            ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> item = temp.getItemById(classId, id);
            // 判断发货单是否已发送到医院
            if (item.get("type") != null) {
                if (item.get("type").equals("1") || Integer.parseInt(item.get("type").toString()) == 1) {
                    throw new BusinessLogicRunTimeException("已发送到医院的发货单不能修改");
                }
            }
            Map<String, Object> entrys = (Map<String, Object>) item.get("entry");
            ArrayList<Object> arrayList = (ArrayList<Object>) entrys.get("1");
            // 遍历发货单子表数据，取出采购订单号，采购订单行号和应发数量
            for (int i = 0; i < arrayList.size(); i++) {
                HashMap<String, Object> sendcargoEntry = (HashMap<String, Object>) arrayList.get(i);
                Map<String, Object> entry1 = new HashMap<String, Object>();
                entry1.put("orderId", sendcargoEntry.get("orderId"));
                entry1.put("orderSeq", sendcargoEntry.get("orderSeq"));
                entry1.put("actualQty", sendcargoEntry.get("actualQty"));
                list.add(entry1);
            }
            OrderEntry orderEntry = new OrderEntry();
            SqlSession sqlSession = (SqlSession) Environ.getBean("sqlSession");
            OrderEntryMapper orderEntryMapper = sqlSession.getMapper(OrderEntryMapper.class);
            // 根据采购订单号和采购订单行号修改发货数量
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> lists = list.get(i);
                int seq = Integer.parseInt(lists.get("orderSeq").toString());
                String parent = lists.get("orderId").toString();
                BigDecimal actualQty = new BigDecimal(lists.get("actualQty").toString());
                OrderEntryExample e = new OrderEntryExample();
                Criteria c = e.createCriteria();
                c.andSeqEqualTo(seq);
                c.andParentEqualTo(parent);
                // 根据订单号和行号查询对应的记录
                List<OrderEntry> o = orderEntryMapper.selectByExample(e);
                if (o.size() > 0) {
                    orderEntry.setInvoiceQty(o.get(0).getInvoiceQty().subtract(actualQty));
                    orderEntry.setId(o.get(0).getId());
                    // 根据订单ID 修改发货数量
                    orderEntryMapper.updateByPrimaryKeySelective(orderEntry);
                }
            }
        }
        PlugInRet ret = new PlugInRet();
        ret.setCode(200);
        ret.setData(data);
        return ret;
    }

    @Override
    @Transactional
    public PlugInRet afterModify(int classId, JSONObject data) {
        if (classId == 2020) {
            JSONObject entry = (JSONObject) data.get("entry");
            JSONArray array = JSONArray.parseArray(entry.getString("1"));
            OrderEntry orderEntry = new OrderEntry();
            SqlSession sqlSession = (SqlSession) Environ.getBean("sqlSession");
            OrderEntryMapper orderEntryMapper = sqlSession.getMapper(OrderEntryMapper.class);
            for (int i = 0; i < array.size(); i++) {
                JSONObject entry1 = array.getJSONObject(i);
                JSONObject datas = entry1.getJSONObject("data");
                int seq = Integer.parseInt(datas.get("orderSeq").toString());
                String parent = datas.get("orderId").toString();
                BigDecimal actualQty = new BigDecimal(datas.get("actualQty").toString());
                OrderEntryExample e = new OrderEntryExample();
                Criteria c = e.createCriteria();
                c.andSeqEqualTo(seq);
                c.andParentEqualTo(parent);
                // 根据订单号和行号查询对应的记录
                List<OrderEntry> o = orderEntryMapper.selectByExample(e);
                if (o.size() > 0) {
                    if (o.get(0).getInvoiceQty().add(actualQty).compareTo(o.get(0).getConfirmQty()) == 1) {
                        throw new BusinessLogicRunTimeException("发货总数不能大于接单数量");
                    }
                    if (entry1.getString("flag").equals("2")) {
                        orderEntry.setInvoiceQty(o.get(0).getInvoiceQty().add(actualQty));
                        orderEntry.setId(o.get(0).getId());
                        // 根据订单ID 修改发货数量
                        orderEntryMapper.updateByPrimaryKeySelective(orderEntry);
                    }
                }
            }
        }
        return super.afterModify(classId, data);
    }

    @Override
    public PlugInRet beforeDelete(int classId, Map<String, Object> formData, String data) {
        if (classId == 2020) {
            // 发货单
            String[] split = data.split("\\,");
            for (int i = 0; i < split.length; i++) {
                ITemplateService temp = Environ.getBean(ITemplateService.class);
                Map<String, Object> item = temp.getItemById(classId, split[i]);
                if (item.get("type") != null) {
                    if (item.get("type").equals("1") || Integer.parseInt(item.get("type").toString()) == 1) {
                        throw new BusinessLogicRunTimeException("已发送到医院的发货单不能删除");
                    }
                }
            }
        }

        if (classId == 2019) {
            // 采购订单删除校验-->已经发货的订单不能删除
            String[] ids = data.split("\\,");
            for (int i = 0; i < ids.length; i++) {
                ITemplateService temp = Environ.getBean(ITemplateService.class);
                Map<String, Object> item = temp.getItemById(classId, ids[i]);

                JSONArray page1 = JSONArray.parseArray(JSON.toJSONString(((Map<String, Object>) item.get("entry")).get("1")));

                for (int j = 0; j < page1.size(); j++) {
                    JSONObject entry = page1.getJSONObject(j);
                    if (entry.getDoubleValue("invoiceQty") > 0) {
                        throw new BusinessLogicRunTimeException(String.format("订单[%s]有发货不可删除!", item.get("number")));
                    }
                }
            }

        }

        return super.beforeDelete(classId, formData, data);

    }

    @Override
    @Transactional
    public PlugInRet afterDelete(int classId, List<Map<String, Object>> data, String items) {


        if (classId == 2020) {
            SqlSession sqlSession = (SqlSession) Environ.getBean("sqlSession");
            OrderEntryMapper orderEntryMapper = sqlSession.getMapper(OrderEntryMapper.class);
            OrderEntry orderEntry = new OrderEntry();
            for (int i = 0; i < data.size(); i++) {
                Map<String, Object> entry = (Map<String, Object>) data.get(i);
                Map<String, Object> entrys = (Map<String, Object>) entry.get("entry");
                ArrayList<Object> arrayList = (ArrayList<Object>) entrys.get("1");
                for (int k = 0; k < arrayList.size(); k++) {
                    Map<String, Object> array = (Map<String, Object>) arrayList.get(k);
                    String parent = array.get("orderId").toString();
                    int seq = Integer.parseInt(array.get("orderSeq").toString());
                    BigDecimal actualQty = new BigDecimal(array.get("actualQty").toString());
                    OrderEntryExample e = new OrderEntryExample();
                    Criteria c = e.createCriteria();
                    c.andSeqEqualTo(seq);
                    c.andParentEqualTo(parent);
                    List<OrderEntry> o = orderEntryMapper.selectByExample(e);
                    for (int j = 0; j < o.size(); j++) {
                        orderEntry.setParent(parent);
                        orderEntry.setSeq(seq);
                        orderEntry.setInvoiceQty(o.get(0).getInvoiceQty().subtract(actualQty));
                        orderEntry.setId(o.get(0).getId());
                        orderEntryMapper.updateByPrimaryKeySelective(orderEntry);
                    }

                }
            }
        }

        if (classId == 2019) {
            // 采购订单删除后将删除消息发送给HRP

            IWebService hrpWebService = Environ.getBean(IWebService.class);

            JSONObject json = new JSONObject();
            //删除的内码集合
            json.put("ids", items);
            String response = hrpWebService.webService(json.toString(), "delSms2hrpPurOrder");
            JSONObject rps = JSONObject.parseObject(response);

            if (null == rps || "".equals(rps)) {
                throw new BusinessLogicRunTimeException("删除订单信息发送到HRP时网络出错!操作失败，请稍后再试!");
            }

            if (rps.getIntValue("code") != 200) {
                throw new BusinessLogicRunTimeException("HRP处理删除订单失败，请重试!");
            }

        }


        return super.afterDelete(classId, data, items);
    }

}
