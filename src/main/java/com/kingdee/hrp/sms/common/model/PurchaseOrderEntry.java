package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PurchaseOrderEntry implements Serializable {
    private Long id;

    private Long parent;

    private Integer sequence;

    private Long material;

    private Long unit;

    private Float qty;

    private Float confirmQty;

    private Date confirmDate;

    private BigDecimal price;

    private BigDecimal amount;

    private Date deliveryDate;

    private Date confirmDeliveryDate;

    private Integer deliverStatus;

    private Float deliverQty;

    private Integer receiveStatus;

    private Float receiveQty;

    private Integer returnStatus;

    private Float returnQty;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getMaterial() {
        return material;
    }

    public void setMaterial(Long material) {
        this.material = material;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public Float getConfirmQty() {
        return confirmQty;
    }

    public void setConfirmQty(Float confirmQty) {
        this.confirmQty = confirmQty;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDeliveryDate() {
        return confirmDeliveryDate;
    }

    public void setConfirmDeliveryDate(Date confirmDeliveryDate) {
        this.confirmDeliveryDate = confirmDeliveryDate;
    }

    public Integer getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(Integer deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public Float getDeliverQty() {
        return deliverQty;
    }

    public void setDeliverQty(Float deliverQty) {
        this.deliverQty = deliverQty;
    }

    public Integer getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Integer receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public Float getReceiveQty() {
        return receiveQty;
    }

    public void setReceiveQty(Float receiveQty) {
        this.receiveQty = receiveQty;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Float getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Float returnQty) {
        this.returnQty = returnQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parent=").append(parent);
        sb.append(", sequence=").append(sequence);
        sb.append(", material=").append(material);
        sb.append(", unit=").append(unit);
        sb.append(", qty=").append(qty);
        sb.append(", confirmQty=").append(confirmQty);
        sb.append(", confirmDate=").append(confirmDate);
        sb.append(", price=").append(price);
        sb.append(", amount=").append(amount);
        sb.append(", deliveryDate=").append(deliveryDate);
        sb.append(", confirmDeliveryDate=").append(confirmDeliveryDate);
        sb.append(", deliverStatus=").append(deliverStatus);
        sb.append(", deliverQty=").append(deliverQty);
        sb.append(", receiveStatus=").append(receiveStatus);
        sb.append(", receiveQty=").append(receiveQty);
        sb.append(", returnStatus=").append(returnStatus);
        sb.append(", returnQty=").append(returnQty);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table t_purchase_order_entry
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
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

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_purchase_order_entry
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}