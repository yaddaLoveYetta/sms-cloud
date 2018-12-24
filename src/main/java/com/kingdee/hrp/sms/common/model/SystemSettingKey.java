package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_system_setting]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SystemSettingKey implements Serializable {
    /**
     * 所属组织
     */
    private Long org;

    /**
     * 参数类别(通常按模块来分参数，记录模块标示)
     */
    private String category;

    /**
     * 参数名
     */
    private String key;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_system_setting]列对应的枚举
     */
    public enum Column {
        org("org", "org", "BIGINT", false),
        category("category", "category", "VARCHAR", false),
        key("key", "key", "VARCHAR", true),
        name("name", "name", "VARCHAR", true),
        desc("desc", "desc", "VARCHAR", true),
        value("value", "value", "VARCHAR", true),
        index("index", "index", "INTEGER", true),
        explanation("explanation", "explanation", "VARCHAR", false),
        readOnly("read_only", "readOnly", "BIT", false);

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