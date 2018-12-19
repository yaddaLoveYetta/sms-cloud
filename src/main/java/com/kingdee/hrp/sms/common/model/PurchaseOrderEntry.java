package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_purchase_order_entry]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PurchaseOrderEntry implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 主表id,t_order表主键
     */
    private Long parent;

    /**
     * 行号,分录的序号
     */
    private Integer sequence;

    /**
     * 物料基础资料id
     */
    private Long material;

    /**
     * 单位,计量单位
     */
    private Long unit;

    /**
     * 数量,小数位数由系统参数设定，最多3位
     */
    private Float qty;

    /**
     * confirm_qty
     */
    private Float confirmQty;

    /**
     * 确认日期，供应商确认订单时填的日期
     */
    private Date confirmDate;

    /**
     * 单价，小数位数由系统参数设定，最多3位
     */
    private BigDecimal price;

    /**
     * 金额，两位小数
     */
    private BigDecimal amount;

    /**
     * 交货日期，医院下订单时要求的交货日期
     */
    private Date deliveryDate;

    /**
     * 确认交货日期，供应商对医院订单确认时填入的交货日期
     */
    private Date confirmDeliveryDate;

    /**
     * 发货状态，默认未发货
值参考t_assistance表 type=45
     */
    private Integer deliverStatus;

    /**
     * 发货数量
     */
    private Float deliverQty;

    /**
     * 收货状态
发货状态，默认未收货
值参考t_assistance表 type=46
     */
    private Integer receiveStatus;

    /**
     * 收货数量
     */
    private Float receiveQty;

    /**
     * 退货状态
发货状态，默认未退货
值参考t_assistance表 type=47
     */
    private Integer returnStatus;

    /**
     * 退货数量
     */
    private Float returnQty;

    /**
     * 备注,其他备注说明信息
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    /**
     * t_purchase_order_entry
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        parent("parent", "parent", "BIGINT", false),
        sequence("sequence", "sequence", "INTEGER", true),
        material("material", "material", "BIGINT", false),
        unit("unit", "unit", "BIGINT", false),
        qty("qty", "qty", "REAL", false),
        confirmQty("confirm_qty", "confirmQty", "REAL", false),
        confirmDate("confirm_date", "confirmDate", "DATE", false),
        price("price", "price", "DECIMAL", false),
        amount("amount", "amount", "DECIMAL", false),
        deliveryDate("delivery_date", "deliveryDate", "DATE", false),
        confirmDeliveryDate("confirm_delivery_date", "confirmDeliveryDate", "DATE", false),
        deliverStatus("deliver_status", "deliverStatus", "INTEGER", false),
        deliverQty("deliver_qty", "deliverQty", "REAL", false),
        receiveStatus("receive_status", "receiveStatus", "INTEGER", false),
        receiveQty("receive_qty", "receiveQty", "REAL", false),
        returnStatus("return_status", "returnStatus", "INTEGER", false),
        returnQty("return_qty", "returnQty", "REAL", false),
        remark("remark", "remark", "VARCHAR", false);

        private static final String BEGINNING_DELIMITER = "`";

        private static final String ENDING_DELIMITER = "`";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}