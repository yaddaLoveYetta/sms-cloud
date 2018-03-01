package com.kingdee.hrp.sms.common.model;

public class FormFields extends FormFieldsKey {
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

    private Integer length;

    private Integer lock;

    private Integer isCondition;

    private Integer isCount;

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
}