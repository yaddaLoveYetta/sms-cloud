package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_form_class_entry]生成
 * @author yadda
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class FormClassEntry extends FormClassEntryKey implements Serializable {
    /**
     * 子表名称
     */
    private String name;

    /**
     * 物理表名
     */
    private String tableName;

    /**
     * 表主键
     */
    private String primaryKey;

    /**
     * 关联主表字段
     */
    private String foreignKey;

    /**
     * 与主表的连接关系(默认 INNER JOIN)
     */
    private String joinType;

    /**
     * 
     */
    private String desc;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_form_class_entry]列对应的枚举
     */
    public enum Column {
        classId("class_id", "classId", "INTEGER", false),
        entryIndex("entry_index", "entryIndex", "INTEGER", false),
        name("name", "name", "VARCHAR", true),
        tableName("table_name", "tableName", "VARCHAR", true),
        primaryKey("primary_key", "primaryKey", "VARCHAR", false),
        foreignKey("foreign_key", "foreignKey", "VARCHAR", false),
        joinType("join_type", "joinType", "VARCHAR", false),
        desc("desc", "desc", "VARCHAR", true);

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