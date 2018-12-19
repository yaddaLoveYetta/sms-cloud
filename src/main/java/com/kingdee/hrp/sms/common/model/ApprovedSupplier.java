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
 * 由数据库表[t_approved_supplier]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ApprovedSupplier implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 归属医院
     */
    private Long hospital;

    /**
     * 供应商id(医院维护的供应商)
     */
    private Long supplier;

    /**
     * 物料id
     */
    private Long item;

    /**
     * 含税单价
     */
    private BigDecimal price;

    /**
     * 生效时间
     */
    private Date effectualDate;

    /**
     * 失效时间
     */
    private Date uneffectualDate;

    /**
     * 是否禁用(0可用1禁用，默认0可用)
     */
    private Boolean status;

    private static final long serialVersionUID = 1L;

    /**
     * t_approved_supplier
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        hospital("hospital", "hospital", "BIGINT", false),
        supplier("supplier", "supplier", "BIGINT", false),
        item("item", "item", "BIGINT", false),
        price("price", "price", "DECIMAL", false),
        effectualDate("effectual_date", "effectualDate", "TIMESTAMP", false),
        uneffectualDate("uneffectual_date", "uneffectualDate", "TIMESTAMP", false),
        status("status", "status", "BIT", true);

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