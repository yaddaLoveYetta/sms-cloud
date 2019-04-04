package com.kingdee.hrp.sms.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 由数据库表[t_purchase_order]生成
 *
 * @author yadda
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class PurchaseOrder implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 所属组织，标识是哪个医院的订单
     */
    private Long hospital;

    /**
     * 订单编号，sms生成的订单号
     */
    private String number;

    /**
     * 原始订单号,HRP同步的订单，此字段记录HRP系统的订单号，sms新建订单无
     */
    private String originNumber;

    /**
     * 供应商,一定是在sms新建或hrp同步到sms系统的供应商
     */
    private Long supplier;

    /**
     * 创建人,用户概念。一定是在sms系统存在的用户，可能需要设置为系统参数统一指定
     */
    private Long creater;

    /**
     * 创建时间,指的是订单在sms系统创建的日期，同步的订单用hrp日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 审核人,用户概念。一定是在sms系统存在的用户，可能需要设置为系统参数统一指定
     */
    private Long checker;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkDate;

    /**
     * 订单状态,分录状态集合,默认新增状态
     * 值参考t_assistance表 type=44
     */
    private Integer orderStatus;

    /**
     * 订单发货状态,分录状态集合，默认未发货
     * 值参考t_assistance表 type=45
     */
    private Integer deliverStatus;

    /**
     * 订单收货状态,分录状态集合，默认未收货
     * 值参考t_assistance表 type=46
     */
    private Integer receiveStatus;

    /**
     * 订单退货状态,分录状态集合
     * 发货状态，默认未退货
     * 值参考t_assistance表 type=47
     */
    private Integer returnStatus;

    /**
     * 金额,指所有分录金额汇总
     */
    private BigDecimal amount;

    /**
     * 采购模式,代销/非代销
     */
    private Integer purchaseType;

    /**
     * 采购员,职员概念。新建或同步的职员
     */
    private Long purchaser;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_purchase_order]列对应的枚举
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        hospital("hospital", "hospital", "BIGINT", false),
        number("number", "number", "VARCHAR", true),
        originNumber("origin_number", "originNumber", "VARCHAR", false),
        supplier("supplier", "supplier", "BIGINT", false),
        creater("creater", "creater", "BIGINT", false),
        createDate("create_date", "createDate", "TIMESTAMP", false),
        checker("checker", "checker", "BIGINT", false),
        checkDate("check_date", "checkDate", "TIMESTAMP", false),
        orderStatus("order_status", "orderStatus", "INTEGER", false),
        deliverStatus("deliver_status", "deliverStatus", "INTEGER", false),
        receiveStatus("receive_status", "receiveStatus", "INTEGER", false),
        returnStatus("return_status", "returnStatus", "INTEGER", false),
        amount("amount", "amount", "DECIMAL", false),
        purchaseType("purchase_type", "purchaseType", "INTEGER", false),
        purchaser("purchaser", "purchaser", "BIGINT", false);

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

        public static Column[] excludes(Column... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[] {});
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER)
                        .toString();
            } else {
                return this.column;
            }
        }
    }
}