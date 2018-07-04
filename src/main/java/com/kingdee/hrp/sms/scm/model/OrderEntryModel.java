package com.kingdee.hrp.sms.scm.model;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.model.OrderEntry;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 订单单据体数据
 * <p>
 * 继承com.kingdee.hrp.sms.common.model.OrderEntry主要补充一些引用类型的属性
 *
 * @author le.xiao
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class OrderEntryModel extends OrderEntry {

    /**
     * 物料名稱
     */
    private String materialName;
    /**
     * 單位名稱
     */
    private String unitName;

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
     * 属性与模板key对应关系
     */
    @ToString
    public enum FieldKeyLinkedColumn {

        id("id", "id", "id"),
        parent("parent", "parent", "parent"),
        sequence("sequence", "sequence", "sequence"),
        material("material", "material", "material"),
        unit("unit", "unit", "unit"),
        qty("qty", "qty", "qty"),
        confirmQty("confirm_qty", "confirmQty", "confirmQty"),
        confirmDate("confirm_date", "confirmDate", "confirmDate"),
        price("price", "price", "price"),
        amount("amount", "amount", "amount"),
        deliveryDate("delivery_date", "delivery_date", "deliveryDate"),
        confirmDeliveryDate("confirm_delivery_date", "confirm_delivery_date", "confirmDeliveryDate"),
        deliverStatus("deliver_status", "deliver_status", "deliverStatus"),
        deliverQty("deliver_qty", "deliver_qty", "deliverQty"),
        receiveStatus("receive_status", "receive_status", "receiveStatus"),
        receiveQty("receive_qty", "receive_qty", "receiveQty"),
        returnStatus("return_status", "return_status", "returnStatus"),
        returnQty("return_qty", "return_qty", "returnQty"),
        remark("remark", "remark", "remark"),

        // 引用类型字段，模板中携带，不占模板记录 column为空
        materialName("material_DspName", "", "materialName"),
        unitName("unit_DspName", "", "unitName"),
        deliverStatusName("detail_deliver_status_DspName", "", "deliverStatusName"),
        receiveStatusName("detail_receive_status_DspName", "", "receiveStatusName"),
        returnStatusName("detail_return_status_DspName", "", "returnStatusName");

        private String fieldKey;
        private String column;
        private String javaProperty;

        FieldKeyLinkedColumn(String fieldKey, String column, String javaProperty) {
            this.fieldKey = fieldKey;
            this.column = column;
            this.javaProperty = javaProperty;
        }

        public static OrderEntryModel.FieldKeyLinkedColumn getFieldKeyLinkedColumn(String fieldKey) {

            for (OrderEntryModel.FieldKeyLinkedColumn column : OrderEntryModel.FieldKeyLinkedColumn.values()) {
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
