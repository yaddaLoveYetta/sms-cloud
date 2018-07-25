package com.kingdee.hrp.sms.scm.service.impl;

import com.kingdee.hrp.sms.common.enums.UserRoleType;
import com.kingdee.hrp.sms.common.model.PurchaseOrderExample;
import com.kingdee.hrp.sms.common.service.BaseService;
import com.kingdee.hrp.sms.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author le.xiao
 */
public abstract class AbstractOrderService extends BaseService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 订单通用条件
     * 主要是所属组织的数据过滤
     * <p>
     * 如果当前用户时系统管理员类型，不加组织条件
     *
     * @param orderExample OrderExample
     * @return OrderExample
     */
    protected PurchaseOrderExample getPurchaseOrderExample(PurchaseOrderExample orderExample) {

        if (SessionUtil.getUserRoleType() != UserRoleType.SYSTEM) {
            // 供应商或医院时加org条件
            if (orderExample.getOredCriteria().size() == 1) {
                // 只有一个Criteria，将org添加加入该Criteria
                orderExample.getOredCriteria().get(0).andHospitalEqualTo(SessionUtil.getUserLinkOrg());
            } else {
                // 没有或多个Criteria，创建Criteria
                if (SessionUtil.getUserRoleType() != UserRoleType.SYSTEM) {
                    orderExample.createCriteria().andHospitalEqualTo(SessionUtil.getUserLinkOrg());
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
    protected PurchaseOrderExample.Criteria getPurchaseOrderExample() {

        PurchaseOrderExample orderExample = new PurchaseOrderExample();
        PurchaseOrderExample.Criteria criteria = orderExample.createCriteria();

        if (SessionUtil.getUserRoleType() == UserRoleType.HOSPITAL) {
            criteria.andHospitalEqualTo(SessionUtil.getUserLinkOrg());
        } else if (SessionUtil.getUserRoleType() == UserRoleType.SUPPLIER) {
            criteria.andSupplierEqualTo(SessionUtil.getUserLinkOrg());
        }

        return criteria;
    }

    /**
     * 生成 一个订单号
     * 使用"PO-"+当前日期yyyyMMdd +"-" +5位随机数
     *
     * @return 订单编号
     */
    public String createPurchaseOrderNumber() {
        return "PO-" + sdf.format(new Date()) + "-" + getRandomStr(5);
    }

    /**
     * 获取指定长度的随机数字串
     * <p>
     * 格式化为指定长度的字符串，不足位数高位补0
     *
     * @param length 想要生成的长度,默认5位
     * @return result String number
     */
    protected String getRandomStr(Integer length) {

        StringBuilder sb = new StringBuilder();

        Random rand = new Random();

        length = (length == null || length <= 0) ? 5 : length;

        int randInt = 0;
        for (int i = 0; i < length; i++) {
            randInt = rand.nextInt(10);
            sb.append(randInt);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        AbstractOrderService abstractOrderService = new PurchaseOrderServiceImpl();
        for (int i = 0; i < 100; i++) {
            System.out.println(abstractOrderService.createPurchaseOrderNumber());
        }

    }

}
