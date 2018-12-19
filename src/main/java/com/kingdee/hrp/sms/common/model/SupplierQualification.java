package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_supplier_qualification]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SupplierQualification implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 证件类别(名称) 关联classId=1018
     */
    private Long qualificationType;

    /**
     * 证件编码
     */
    private String number;

    /**
     * 发证机关
     */
    private String issue;

    /**
     * 证件有效期开始
     */
    private Date validityPeriodBegin;

    /**
     * 证件有效期结束
     */
    private Date validityPeriodEnd;

    /**
     * 归属供应商(关联t_form_class 1013 t_supplier)
     */
    private Long supplier;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    /**
     * t_supplier_qualification
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        qualificationType("qualification_type", "qualificationType", "BIGINT", false),
        number("number", "number", "VARCHAR", true),
        issue("issue", "issue", "VARCHAR", false),
        validityPeriodBegin("validity_period_begin", "validityPeriodBegin", "DATE", false),
        validityPeriodEnd("validity_period_end", "validityPeriodEnd", "DATE", false),
        supplier("supplier", "supplier", "BIGINT", false),
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