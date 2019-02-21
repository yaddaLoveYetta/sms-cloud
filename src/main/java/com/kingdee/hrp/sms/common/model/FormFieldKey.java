package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_form_field]生成
 * @author yadda
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class FormFieldKey implements Serializable {
    /**
     * 业务类型id，对应t_form_class表class_id
     */
    private Integer classId;

    /**
     * 字段唯一标示(用于关联表显示字段名与本表字段名同名的情况，同一个class_id中key是唯一的)
     */
    private String key;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_form_field]列对应的枚举
     */
    public enum Column {
        classId("class_id", "classId", "INTEGER", false),
        key("key", "key", "VARCHAR", true),
        page("page", "page", "INTEGER", false),
        name("name", "name", "VARCHAR", true),
        sqlColumnName("sql_column_name", "sqlColumnName", "VARCHAR", false),
        dataType("data_type", "dataType", "INTEGER", false),
        ctrlType("ctrl_type", "ctrlType", "INTEGER", false),
        ctrlIndex("ctrl_index", "ctrlIndex", "INTEGER", false),
        index("index", "index", "INTEGER", true),
        display("display", "display", "INTEGER", false),
        showWidth("show_width", "showWidth", "INTEGER", false),
        lookUpType("look_up_type", "lookUpType", "INTEGER", false),
        lookUpClassId("look_up_class_id", "lookUpClassId", "INTEGER", false),
        srcTable("src_table", "srcTable", "VARCHAR", false),
        srcTableAlis("src_table_alis", "srcTableAlis", "VARCHAR", false),
        srcField("src_field", "srcField", "VARCHAR", false),
        displayField("display_field", "displayField", "VARCHAR", false),
        displayExt("display_ext", "displayExt", "VARCHAR", false),
        joinType("join_type", "joinType", "VARCHAR", false),
        filter("filter", "filter", "VARCHAR", true),
        defaultValue("default_value", "defaultValue", "VARCHAR", false),
        maxValue("max_value", "maxValue", "DOUBLE", false),
        minValue("min_value", "minValue", "DOUBLE", false),
        mustInput("must_input", "mustInput", "INTEGER", false),
        needSave("need_save", "needSave", "BIT", false),
        length("length", "length", "INTEGER", true),
        lock("lock", "lock", "INTEGER", true),
        isCondition("is_condition", "isCondition", "INTEGER", false),
        isCount("is_count", "isCount", "INTEGER", false);

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