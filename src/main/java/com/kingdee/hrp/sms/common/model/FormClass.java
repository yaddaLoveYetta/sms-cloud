package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_form_class]生成
 * @author yadda
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class FormClass implements Serializable {
    /**
     * 业务类型(每一个表单都有一个唯一的业务类型id)
     */
    private Integer classId;

    /**
     * 业务类型名称(如采购订单,用户,权限等)
     */
    private String name;

    /**
     * 数据存储物理表(主表,如用户资料存储表为t_user)
     */
    private String tableName;

    /**
     * 数据存储物理表的主键(只能设置一个)
     */
    private String primaryKey;

    /**
     * 描述
     */
    private String desc;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_form_class]列对应的枚举
     */
    public enum Column {
        classId("class_id", "classId", "INTEGER", false),
        name("name", "name", "VARCHAR", true),
        tableName("table_name", "tableName", "VARCHAR", true),
        primaryKey("primary_key", "primaryKey", "VARCHAR", false),
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