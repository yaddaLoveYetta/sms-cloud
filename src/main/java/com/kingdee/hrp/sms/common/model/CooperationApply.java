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
 * 由数据库表[t_cooperation_apply]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CooperationApply implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 供应商
     */
    private Long supplier;

    /**
     * 医院
     */
    private Long hospital;

    /**
     * 提交申请的时间
     */
    private Date date;

    /**
     * 记录状态(已处理，未处理等关联classId=1080,tipyId=43)
     */
    private Integer status;

    /**
     * 处理时间
     */
    private Date processDate;

    private static final long serialVersionUID = 1L;

    /**
     * t_cooperation_apply
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        supplier("supplier", "supplier", "BIGINT", false),
        hospital("hospital", "hospital", "BIGINT", false),
        date("date", "date", "TIMESTAMP", true),
        status("status", "status", "INTEGER", true),
        processDate("process_date", "processDate", "TIMESTAMP", false);

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