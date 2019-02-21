package com.kingdee.hrp.sms.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 由数据库表[t_material_document]生成
 * @author yadda
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class MaterialDocument implements Serializable {
    /**
     * 内码
     */
    private Long id;

    /**
     * 物料
     */
    private Long material;

    /**
     * 供应商
     */
    private Long supplier;

    /**
     * 证件 类型
     */
    private Long type;

    /**
     * 证件代码
     */
    private String number;

    /**
     * 发证机关
     */
    private String issue;

    /**
     * 有效期开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validityPeriodBegin;

    /**
     * 有效期结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validityPeriodEnd;

    /**
     * 备注
     */
    private String remark;

    /**
     * 归属医院(关联t_form_class 1012 t_hospital)
     */
    private Long hospital;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_material_document]列对应的枚举
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        material("material", "material", "BIGINT", false),
        supplier("supplier", "supplier", "BIGINT", false),
        type("type", "type", "BIGINT", true),
        number("number", "number", "VARCHAR", true),
        issue("issue", "issue", "VARCHAR", false),
        validityPeriodBegin("validity_period_begin", "validityPeriodBegin", "DATE", false),
        validityPeriodEnd("validity_period_end", "validityPeriodEnd", "DATE", false),
        remark("remark", "remark", "VARCHAR", false),
        hospital("hospital", "hospital", "BIGINT", false);

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