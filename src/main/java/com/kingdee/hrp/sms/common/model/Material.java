package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_material]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Material implements Serializable {
    /**
     * 
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
     * 规格型号
     */
    private String specification;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 是否高值耗材(0不是1是，默认0)
     */
    private Boolean isHighConsumable;

    /**
     * 是否批次管理(0不是1是，默认0)
     */
    private Boolean isLotNumber;

    /**
     * 所属医院(基础资料classId=1012)
     */
    private Long org;

    /**
     * 是否禁用(0可用1禁用，默认0可用)
     */
    private Boolean status;

    /**
     * 图片样张
     */
    private String image;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_material]列对应的枚举
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        number("number", "number", "VARCHAR", true),
        name("name", "name", "VARCHAR", true),
        specification("specification", "specification", "VARCHAR", false),
        manufacturer("manufacturer", "manufacturer", "VARCHAR", false),
        isHighConsumable("is_high_consumable", "isHighConsumable", "BIT", false),
        isLotNumber("is_lot_number", "isLotNumber", "BIT", false),
        org("org", "org", "BIGINT", false),
        status("status", "status", "BIT", true),
        image("image", "image", "VARCHAR", false);

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