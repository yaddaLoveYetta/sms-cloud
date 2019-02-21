package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_partner]生成
 * @author yadda
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class Partner implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 关系所属组织(医院或者供应商id，数据所有者)
     */
    private Long org;

    /**
     * 关系关联对象(医院关联供应商/供应商关联医院)
     */
    private Long linkOrg;

    /**
     * 审核状态(关联classId=1080 typeId=42默认未审核)
     */
    private Integer checkStatus;

    /**
     * 是否禁用(0可用1禁用，默认0可用)
     */
    private Boolean status;

    /**
     * 关系类型(0:医院的合作供应商 1:供应商的合作医院)
     */
    private Boolean type;

    /**
     * 关联的HRP供应商(classId=1005)医院关联供应商时需指定关联HRP供应商
     */
    private Long hrpSupplier;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_partner]列对应的枚举
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        org("org", "org", "BIGINT", false),
        linkOrg("link_org", "linkOrg", "BIGINT", false),
        checkStatus("check_status", "checkStatus", "INTEGER", false),
        status("status", "status", "BIT", true),
        type("type", "type", "BIT", true),
        hrpSupplier("hrp_supplier", "hrpSupplier", "BIGINT", false);

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