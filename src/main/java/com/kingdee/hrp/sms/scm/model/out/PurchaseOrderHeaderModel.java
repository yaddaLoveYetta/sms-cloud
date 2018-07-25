package com.kingdee.hrp.sms.scm.model.out;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.PurchaseOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 订单单据头数据
 * <p>
 * 继承com.kingdee.hrp.sms.common.model.Order主要补充一些引用类型的属性
 *
 * @author le.xiao
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PurchaseOrderHeaderModel extends PurchaseOrder {

    /**
     * 医院名称
     */
    private String hospitalName;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 制单人名称
     */
    private String createrName;
    /**
     * 采购员名称
     */
    private String purchaserName;
    /**
     * 审核人名称
     */
    private String checkerName;
    /**
     * 订单状态名称
     */
    private String orderStatusName;
    /**
     * 发货状态名称
     */
    private String deliverStatusName;
    /**
     * 收货状态名称
     */
    private String receiveStatusName;
    /**
     * 退货状态名称
     */
    private String returnStatusName;
    /**
     * 采购模式名称
     */
    private String purchaseTypeName;

    /**
     * 属性与模板key对应关系
     */
    @ToString
    public enum FieldKeyLinkedColumn {

        id("id", "id", "id"),
        hospital("hospital", "hospital", "hospital"),
        number("number", "number", "number"),
        originNumber("origin_number", "origin_number", "originNumber"),
        supplier("supplier", "supplier", "supplier"),
        creater("creater", "creater", "creater"),
        createDate("create_date", "create_date", "createDate"),
        checker("checker", "checker", "checker"),
        checkDate("check_date", "check_date", "checkDate"),
        orderStatus("order_status", "order_status", "orderStatus"),
        deliverStatus("deliver_status", "deliver_status", "deliverStatus"),
        receiveStatus("receive_status", "receive_status", "receiveStatus"),
        returnStatus("return_status", "return_status", "returnStatus"),
        amount("total_amount", "amount", "amount"),
        purchaseType("purchase_type", "purchase_type", "purchaseType"),
        purchaser("purchaser", "purchaser", "purchaser"),

        // 引用类型字段，模板中携带，不占模板记录 column为空
        hospitalName("hospital_DspName", "", "hospitalName"),
        supplierName("supplier_DspName", "", "supplierName"),
        createrName("creater_DspName", "", "createrName"),
        purchaserName("purchaser_DspName", "", "purchaserName"),
        checkerName("checker_DspName", "", "checkerName"),
        orderStatusName("order_status_DspName", "", "orderStatusName"),
        deliverStatusName("deliver_status_DspName", "", "deliverStatusName"),
        receiveStatusName("receive_status_DspName", "", "receiveStatusName"),
        returnStatusName("return_status_DspName", "", "returnStatusName"),
        purchaseTypeName("purchase_type_DspName", "", "purchaseTypeName");


        private String fieldKey;
        private String column;
        private String javaProperty;


        FieldKeyLinkedColumn(String fieldKey, String column, String javaProperty) {
            this.fieldKey = fieldKey;
            this.column = column;
            this.javaProperty = javaProperty;
        }

        public static FieldKeyLinkedColumn getFieldKeyLinkedColumn(String fieldKey) {

            for (FieldKeyLinkedColumn column : FieldKeyLinkedColumn.values()) {
                if (column.getFieldKey().equals(fieldKey)) {
                    return column;
                }
            }

            throw new BusinessLogicRunTimeException("FieldKeyLinkedColumn不存fieldKey值:" + fieldKey);
        }

        public String getFieldKey() {
            return fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getJavaProperty() {
            return javaProperty;
        }

        public void setJavaProperty(String javaProperty) {
            this.javaProperty = javaProperty;
        }

    }
}
