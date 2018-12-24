package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_role]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Role implements Serializable {
    /**
     * 内码
     */
    private Long id;

    /**
     * 代码
     */
    private String number;

    /**
     * 名称
     */
    private String name;

    /**
     * 角色类别(关联t_role_type表id)
     */
    private Integer type;

    /**
     * 是否用户自定义(0:系统内置 1:用户自定义)
系统内置角色不可修改删除
     */
    private Boolean userDefine;

    /**
     * 角色所属组织(属于哪一个医院或者供应商，用户自定义的角色一定要有所属组织
默认0:系统管理员，
属于供应商类别时，关联t_supplier表id，即归属于某一个供应商，
属于医院类别时，关联t_hospital表id，即归属于某一家医院
     */
    private Long org;

    /**
     * 是否禁用(0可用1禁用，默认0可用)
     */
    private Boolean status;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_role]列对应的枚举
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        number("number", "number", "VARCHAR", true),
        name("name", "name", "VARCHAR", true),
        type("type", "type", "INTEGER", true),
        userDefine("user_define", "userDefine", "BIT", false),
        org("org", "org", "BIGINT", false),
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