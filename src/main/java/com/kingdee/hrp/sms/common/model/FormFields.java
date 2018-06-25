package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class FormFields extends FormFieldsKey implements Serializable {
    private Integer page;

    private String name;

    private String sqlColumnName;

    private Integer dataType;

    private Integer ctrlType;

    private Integer ctrlIndex;

    private Integer index;

    private Integer display;

    private Integer showWidth;

    private Integer lookUpType;

    private Integer lookUpClassId;

    private String srcTable;

    private String srcTableAlis;

    private String srcField;

    private String displayField;

    private String displayExt;

    private String joinType;

    private String filter;

    private String defaultValue;

    private Double maxValue;

    private Double minValue;

    private Integer mustInput;

    private Boolean needSave;

    private Integer length;

    private Integer lock;

    private Integer isCondition;

    private Integer isCount;

    private static final long serialVersionUID = 1L;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSqlColumnName() {
        return sqlColumnName;
    }

    public void setSqlColumnName(String sqlColumnName) {
        this.sqlColumnName = sqlColumnName == null ? null : sqlColumnName.trim();
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getCtrlType() {
        return ctrlType;
    }

    public void setCtrlType(Integer ctrlType) {
        this.ctrlType = ctrlType;
    }

    public Integer getCtrlIndex() {
        return ctrlIndex;
    }

    public void setCtrlIndex(Integer ctrlIndex) {
        this.ctrlIndex = ctrlIndex;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getShowWidth() {
        return showWidth;
    }

    public void setShowWidth(Integer showWidth) {
        this.showWidth = showWidth;
    }

    public Integer getLookUpType() {
        return lookUpType;
    }

    public void setLookUpType(Integer lookUpType) {
        this.lookUpType = lookUpType;
    }

    public Integer getLookUpClassId() {
        return lookUpClassId;
    }

    public void setLookUpClassId(Integer lookUpClassId) {
        this.lookUpClassId = lookUpClassId;
    }

    public String getSrcTable() {
        return srcTable;
    }

    public void setSrcTable(String srcTable) {
        this.srcTable = srcTable == null ? null : srcTable.trim();
    }

    public String getSrcTableAlis() {
        return srcTableAlis;
    }

    public void setSrcTableAlis(String srcTableAlis) {
        this.srcTableAlis = srcTableAlis == null ? null : srcTableAlis.trim();
    }

    public String getSrcField() {
        return srcField;
    }

    public void setSrcField(String srcField) {
        this.srcField = srcField == null ? null : srcField.trim();
    }

    public String getDisplayField() {
        return displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField == null ? null : displayField.trim();
    }

    public String getDisplayExt() {
        return displayExt;
    }

    public void setDisplayExt(String displayExt) {
        this.displayExt = displayExt == null ? null : displayExt.trim();
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType == null ? null : joinType.trim();
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter == null ? null : filter.trim();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Integer getMustInput() {
        return mustInput;
    }

    public void setMustInput(Integer mustInput) {
        this.mustInput = mustInput;
    }

    public Boolean getNeedSave() {
        return needSave;
    }

    public void setNeedSave(Boolean needSave) {
        this.needSave = needSave;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public Integer getIsCondition() {
        return isCondition;
    }

    public void setIsCondition(Integer isCondition) {
        this.isCondition = isCondition;
    }

    public Integer getIsCount() {
        return isCount;
    }

    public void setIsCount(Integer isCount) {
        this.isCount = isCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", page=").append(page);
        sb.append(", name=").append(name);
        sb.append(", sqlColumnName=").append(sqlColumnName);
        sb.append(", dataType=").append(dataType);
        sb.append(", ctrlType=").append(ctrlType);
        sb.append(", ctrlIndex=").append(ctrlIndex);
        sb.append(", index=").append(index);
        sb.append(", display=").append(display);
        sb.append(", showWidth=").append(showWidth);
        sb.append(", lookUpType=").append(lookUpType);
        sb.append(", lookUpClassId=").append(lookUpClassId);
        sb.append(", srcTable=").append(srcTable);
        sb.append(", srcTableAlis=").append(srcTableAlis);
        sb.append(", srcField=").append(srcField);
        sb.append(", displayField=").append(displayField);
        sb.append(", displayExt=").append(displayExt);
        sb.append(", joinType=").append(joinType);
        sb.append(", filter=").append(filter);
        sb.append(", defaultValue=").append(defaultValue);
        sb.append(", maxValue=").append(maxValue);
        sb.append(", minValue=").append(minValue);
        sb.append(", mustInput=").append(mustInput);
        sb.append(", needSave=").append(needSave);
        sb.append(", length=").append(length);
        sb.append(", lock=").append(lock);
        sb.append(", isCondition=").append(isCondition);
        sb.append(", isCount=").append(isCount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table t_form_fields
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
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

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_form_fields
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}