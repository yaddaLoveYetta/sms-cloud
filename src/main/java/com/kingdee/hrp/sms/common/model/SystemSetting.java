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
public class SystemSetting extends SystemSettingKey implements Serializable {
    /**
     * 参数显示给用户的名称(尽量简短，不要太长)
     */
    private String name;

    /**
     * 参数描述，简要描述参数用途等信息
     */
    private String desc;

    /**
     * 值
     */
    private String value;

    /**
     * 参数显示顺序
     */
    private Integer index;

    /**
     * 参数选项,配置参数的类型及类型明细，前端展现时解析
{ctlType:checkBox,} 选择框类型
{ctlType:selector,list:[{0:不启用},{1:启用}]} 下拉选择
{ctlType:text,} 文本
{ctlType:number,} 数字
     */
    private String explanation;

    /**
     * 是否只读参数(只读参数不可修改)
     */
    private Boolean readOnly;

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