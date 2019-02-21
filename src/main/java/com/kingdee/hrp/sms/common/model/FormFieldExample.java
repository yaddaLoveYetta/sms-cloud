package com.kingdee.hrp.sms.common.model;

import java.util.ArrayList;
import java.util.List;

public class FormFieldExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FormFieldExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public FormFieldExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public FormFieldExample orderBy(String ... orderByClauses) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orderByClauses.length; i++) {
            sb.append(orderByClauses[i]);
            if (i < orderByClauses.length - 1) {
                sb.append(" , ");
            }
        }
        this.setOrderByClause(sb.toString());
        return this;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(this);
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * t_form_field 2019-02-21
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andClassIdIsNull() {
            addCriterion("class_id is null");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNotNull() {
            addCriterion("class_id is not null");
            return (Criteria) this;
        }

        public Criteria andClassIdEqualTo(Integer value) {
            addCriterion("class_id =", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("class_id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClassIdNotEqualTo(Integer value) {
            addCriterion("class_id <>", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("class_id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThan(Integer value) {
            addCriterion("class_id >", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("class_id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("class_id >=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("class_id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClassIdLessThan(Integer value) {
            addCriterion("class_id <", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("class_id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("class_id <=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("class_id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClassIdIn(List<Integer> values) {
            addCriterion("class_id in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotIn(List<Integer> values) {
            addCriterion("class_id not in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdBetween(Integer value1, Integer value2) {
            addCriterion("class_id between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("class_id not between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andKeyIsNull() {
            addCriterion("`key` is null");
            return (Criteria) this;
        }

        public Criteria andKeyIsNotNull() {
            addCriterion("`key` is not null");
            return (Criteria) this;
        }

        public Criteria andKeyEqualTo(String value) {
            addCriterion("`key` =", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`key` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeyNotEqualTo(String value) {
            addCriterion("`key` <>", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`key` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeyGreaterThan(String value) {
            addCriterion("`key` >", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`key` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeyGreaterThanOrEqualTo(String value) {
            addCriterion("`key` >=", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`key` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeyLessThan(String value) {
            addCriterion("`key` <", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`key` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeyLessThanOrEqualTo(String value) {
            addCriterion("`key` <=", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`key` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andKeyLike(String value) {
            addCriterion("`key` like", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotLike(String value) {
            addCriterion("`key` not like", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyIn(List<String> values) {
            addCriterion("`key` in", values, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotIn(List<String> values) {
            addCriterion("`key` not in", values, "key");
            return (Criteria) this;
        }

        public Criteria andKeyBetween(String value1, String value2) {
            addCriterion("`key` between", value1, value2, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotBetween(String value1, String value2) {
            addCriterion("`key` not between", value1, value2, "key");
            return (Criteria) this;
        }

        public Criteria andPageIsNull() {
            addCriterion("page is null");
            return (Criteria) this;
        }

        public Criteria andPageIsNotNull() {
            addCriterion("page is not null");
            return (Criteria) this;
        }

        public Criteria andPageEqualTo(Integer value) {
            addCriterion("page =", value, "page");
            return (Criteria) this;
        }

        public Criteria andPageEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("page = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPageNotEqualTo(Integer value) {
            addCriterion("page <>", value, "page");
            return (Criteria) this;
        }

        public Criteria andPageNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("page <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPageGreaterThan(Integer value) {
            addCriterion("page >", value, "page");
            return (Criteria) this;
        }

        public Criteria andPageGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("page > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPageGreaterThanOrEqualTo(Integer value) {
            addCriterion("page >=", value, "page");
            return (Criteria) this;
        }

        public Criteria andPageGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("page >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPageLessThan(Integer value) {
            addCriterion("page <", value, "page");
            return (Criteria) this;
        }

        public Criteria andPageLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("page < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPageLessThanOrEqualTo(Integer value) {
            addCriterion("page <=", value, "page");
            return (Criteria) this;
        }

        public Criteria andPageLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("page <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPageIn(List<Integer> values) {
            addCriterion("page in", values, "page");
            return (Criteria) this;
        }

        public Criteria andPageNotIn(List<Integer> values) {
            addCriterion("page not in", values, "page");
            return (Criteria) this;
        }

        public Criteria andPageBetween(Integer value1, Integer value2) {
            addCriterion("page between", value1, value2, "page");
            return (Criteria) this;
        }

        public Criteria andPageNotBetween(Integer value1, Integer value2) {
            addCriterion("page not between", value1, value2, "page");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`name` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`name` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`name` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`name` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`name` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`name` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameIsNull() {
            addCriterion("sql_column_name is null");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameIsNotNull() {
            addCriterion("sql_column_name is not null");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameEqualTo(String value) {
            addCriterion("sql_column_name =", value, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("sql_column_name = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameNotEqualTo(String value) {
            addCriterion("sql_column_name <>", value, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("sql_column_name <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameGreaterThan(String value) {
            addCriterion("sql_column_name >", value, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("sql_column_name > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameGreaterThanOrEqualTo(String value) {
            addCriterion("sql_column_name >=", value, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("sql_column_name >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameLessThan(String value) {
            addCriterion("sql_column_name <", value, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("sql_column_name < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameLessThanOrEqualTo(String value) {
            addCriterion("sql_column_name <=", value, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("sql_column_name <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameLike(String value) {
            addCriterion("sql_column_name like", value, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameNotLike(String value) {
            addCriterion("sql_column_name not like", value, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameIn(List<String> values) {
            addCriterion("sql_column_name in", values, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameNotIn(List<String> values) {
            addCriterion("sql_column_name not in", values, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameBetween(String value1, String value2) {
            addCriterion("sql_column_name between", value1, value2, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andSqlColumnNameNotBetween(String value1, String value2) {
            addCriterion("sql_column_name not between", value1, value2, "sqlColumnName");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNull() {
            addCriterion("data_type is null");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNotNull() {
            addCriterion("data_type is not null");
            return (Criteria) this;
        }

        public Criteria andDataTypeEqualTo(Integer value) {
            addCriterion("data_type =", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("data_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataTypeNotEqualTo(Integer value) {
            addCriterion("data_type <>", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("data_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThan(Integer value) {
            addCriterion("data_type >", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("data_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("data_type >=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("data_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThan(Integer value) {
            addCriterion("data_type <", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("data_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThanOrEqualTo(Integer value) {
            addCriterion("data_type <=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("data_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataTypeIn(List<Integer> values) {
            addCriterion("data_type in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotIn(List<Integer> values) {
            addCriterion("data_type not in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeBetween(Integer value1, Integer value2) {
            addCriterion("data_type between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("data_type not between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeIsNull() {
            addCriterion("ctrl_type is null");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeIsNotNull() {
            addCriterion("ctrl_type is not null");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeEqualTo(Integer value) {
            addCriterion("ctrl_type =", value, "ctrlType");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlTypeNotEqualTo(Integer value) {
            addCriterion("ctrl_type <>", value, "ctrlType");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlTypeGreaterThan(Integer value) {
            addCriterion("ctrl_type >", value, "ctrlType");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ctrl_type >=", value, "ctrlType");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlTypeLessThan(Integer value) {
            addCriterion("ctrl_type <", value, "ctrlType");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlTypeLessThanOrEqualTo(Integer value) {
            addCriterion("ctrl_type <=", value, "ctrlType");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlTypeIn(List<Integer> values) {
            addCriterion("ctrl_type in", values, "ctrlType");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeNotIn(List<Integer> values) {
            addCriterion("ctrl_type not in", values, "ctrlType");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeBetween(Integer value1, Integer value2) {
            addCriterion("ctrl_type between", value1, value2, "ctrlType");
            return (Criteria) this;
        }

        public Criteria andCtrlTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ctrl_type not between", value1, value2, "ctrlType");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexIsNull() {
            addCriterion("ctrl_index is null");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexIsNotNull() {
            addCriterion("ctrl_index is not null");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexEqualTo(Integer value) {
            addCriterion("ctrl_index =", value, "ctrlIndex");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_index = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlIndexNotEqualTo(Integer value) {
            addCriterion("ctrl_index <>", value, "ctrlIndex");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_index <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlIndexGreaterThan(Integer value) {
            addCriterion("ctrl_index >", value, "ctrlIndex");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_index > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("ctrl_index >=", value, "ctrlIndex");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_index >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlIndexLessThan(Integer value) {
            addCriterion("ctrl_index <", value, "ctrlIndex");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_index < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlIndexLessThanOrEqualTo(Integer value) {
            addCriterion("ctrl_index <=", value, "ctrlIndex");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("ctrl_index <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCtrlIndexIn(List<Integer> values) {
            addCriterion("ctrl_index in", values, "ctrlIndex");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexNotIn(List<Integer> values) {
            addCriterion("ctrl_index not in", values, "ctrlIndex");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexBetween(Integer value1, Integer value2) {
            addCriterion("ctrl_index between", value1, value2, "ctrlIndex");
            return (Criteria) this;
        }

        public Criteria andCtrlIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("ctrl_index not between", value1, value2, "ctrlIndex");
            return (Criteria) this;
        }

        public Criteria andIndexIsNull() {
            addCriterion("`index` is null");
            return (Criteria) this;
        }

        public Criteria andIndexIsNotNull() {
            addCriterion("`index` is not null");
            return (Criteria) this;
        }

        public Criteria andIndexEqualTo(Integer value) {
            addCriterion("`index` =", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`index` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndexNotEqualTo(Integer value) {
            addCriterion("`index` <>", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`index` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndexGreaterThan(Integer value) {
            addCriterion("`index` >", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`index` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("`index` >=", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`index` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndexLessThan(Integer value) {
            addCriterion("`index` <", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`index` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndexLessThanOrEqualTo(Integer value) {
            addCriterion("`index` <=", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`index` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndexIn(List<Integer> values) {
            addCriterion("`index` in", values, "index");
            return (Criteria) this;
        }

        public Criteria andIndexNotIn(List<Integer> values) {
            addCriterion("`index` not in", values, "index");
            return (Criteria) this;
        }

        public Criteria andIndexBetween(Integer value1, Integer value2) {
            addCriterion("`index` between", value1, value2, "index");
            return (Criteria) this;
        }

        public Criteria andIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("`index` not between", value1, value2, "index");
            return (Criteria) this;
        }

        public Criteria andDisplayIsNull() {
            addCriterion("display is null");
            return (Criteria) this;
        }

        public Criteria andDisplayIsNotNull() {
            addCriterion("display is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayEqualTo(Integer value) {
            addCriterion("display =", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayNotEqualTo(Integer value) {
            addCriterion("display <>", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayGreaterThan(Integer value) {
            addCriterion("display >", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayGreaterThanOrEqualTo(Integer value) {
            addCriterion("display >=", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayLessThan(Integer value) {
            addCriterion("display <", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayLessThanOrEqualTo(Integer value) {
            addCriterion("display <=", value, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayIn(List<Integer> values) {
            addCriterion("display in", values, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotIn(List<Integer> values) {
            addCriterion("display not in", values, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayBetween(Integer value1, Integer value2) {
            addCriterion("display between", value1, value2, "display");
            return (Criteria) this;
        }

        public Criteria andDisplayNotBetween(Integer value1, Integer value2) {
            addCriterion("display not between", value1, value2, "display");
            return (Criteria) this;
        }

        public Criteria andShowWidthIsNull() {
            addCriterion("show_width is null");
            return (Criteria) this;
        }

        public Criteria andShowWidthIsNotNull() {
            addCriterion("show_width is not null");
            return (Criteria) this;
        }

        public Criteria andShowWidthEqualTo(Integer value) {
            addCriterion("show_width =", value, "showWidth");
            return (Criteria) this;
        }

        public Criteria andShowWidthEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("show_width = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShowWidthNotEqualTo(Integer value) {
            addCriterion("show_width <>", value, "showWidth");
            return (Criteria) this;
        }

        public Criteria andShowWidthNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("show_width <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShowWidthGreaterThan(Integer value) {
            addCriterion("show_width >", value, "showWidth");
            return (Criteria) this;
        }

        public Criteria andShowWidthGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("show_width > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShowWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("show_width >=", value, "showWidth");
            return (Criteria) this;
        }

        public Criteria andShowWidthGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("show_width >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShowWidthLessThan(Integer value) {
            addCriterion("show_width <", value, "showWidth");
            return (Criteria) this;
        }

        public Criteria andShowWidthLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("show_width < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShowWidthLessThanOrEqualTo(Integer value) {
            addCriterion("show_width <=", value, "showWidth");
            return (Criteria) this;
        }

        public Criteria andShowWidthLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("show_width <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andShowWidthIn(List<Integer> values) {
            addCriterion("show_width in", values, "showWidth");
            return (Criteria) this;
        }

        public Criteria andShowWidthNotIn(List<Integer> values) {
            addCriterion("show_width not in", values, "showWidth");
            return (Criteria) this;
        }

        public Criteria andShowWidthBetween(Integer value1, Integer value2) {
            addCriterion("show_width between", value1, value2, "showWidth");
            return (Criteria) this;
        }

        public Criteria andShowWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("show_width not between", value1, value2, "showWidth");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeIsNull() {
            addCriterion("look_up_type is null");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeIsNotNull() {
            addCriterion("look_up_type is not null");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeEqualTo(Integer value) {
            addCriterion("look_up_type =", value, "lookUpType");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpTypeNotEqualTo(Integer value) {
            addCriterion("look_up_type <>", value, "lookUpType");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpTypeGreaterThan(Integer value) {
            addCriterion("look_up_type >", value, "lookUpType");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("look_up_type >=", value, "lookUpType");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpTypeLessThan(Integer value) {
            addCriterion("look_up_type <", value, "lookUpType");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpTypeLessThanOrEqualTo(Integer value) {
            addCriterion("look_up_type <=", value, "lookUpType");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpTypeIn(List<Integer> values) {
            addCriterion("look_up_type in", values, "lookUpType");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeNotIn(List<Integer> values) {
            addCriterion("look_up_type not in", values, "lookUpType");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeBetween(Integer value1, Integer value2) {
            addCriterion("look_up_type between", value1, value2, "lookUpType");
            return (Criteria) this;
        }

        public Criteria andLookUpTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("look_up_type not between", value1, value2, "lookUpType");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdIsNull() {
            addCriterion("look_up_class_id is null");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdIsNotNull() {
            addCriterion("look_up_class_id is not null");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdEqualTo(Integer value) {
            addCriterion("look_up_class_id =", value, "lookUpClassId");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_class_id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdNotEqualTo(Integer value) {
            addCriterion("look_up_class_id <>", value, "lookUpClassId");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_class_id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdGreaterThan(Integer value) {
            addCriterion("look_up_class_id >", value, "lookUpClassId");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_class_id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("look_up_class_id >=", value, "lookUpClassId");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_class_id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdLessThan(Integer value) {
            addCriterion("look_up_class_id <", value, "lookUpClassId");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_class_id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("look_up_class_id <=", value, "lookUpClassId");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("look_up_class_id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdIn(List<Integer> values) {
            addCriterion("look_up_class_id in", values, "lookUpClassId");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdNotIn(List<Integer> values) {
            addCriterion("look_up_class_id not in", values, "lookUpClassId");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdBetween(Integer value1, Integer value2) {
            addCriterion("look_up_class_id between", value1, value2, "lookUpClassId");
            return (Criteria) this;
        }

        public Criteria andLookUpClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("look_up_class_id not between", value1, value2, "lookUpClassId");
            return (Criteria) this;
        }

        public Criteria andSrcTableIsNull() {
            addCriterion("src_table is null");
            return (Criteria) this;
        }

        public Criteria andSrcTableIsNotNull() {
            addCriterion("src_table is not null");
            return (Criteria) this;
        }

        public Criteria andSrcTableEqualTo(String value) {
            addCriterion("src_table =", value, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableNotEqualTo(String value) {
            addCriterion("src_table <>", value, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableGreaterThan(String value) {
            addCriterion("src_table >", value, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableGreaterThanOrEqualTo(String value) {
            addCriterion("src_table >=", value, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableLessThan(String value) {
            addCriterion("src_table <", value, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableLessThanOrEqualTo(String value) {
            addCriterion("src_table <=", value, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableLike(String value) {
            addCriterion("src_table like", value, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableNotLike(String value) {
            addCriterion("src_table not like", value, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableIn(List<String> values) {
            addCriterion("src_table in", values, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableNotIn(List<String> values) {
            addCriterion("src_table not in", values, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableBetween(String value1, String value2) {
            addCriterion("src_table between", value1, value2, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableNotBetween(String value1, String value2) {
            addCriterion("src_table not between", value1, value2, "srcTable");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisIsNull() {
            addCriterion("src_table_alis is null");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisIsNotNull() {
            addCriterion("src_table_alis is not null");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisEqualTo(String value) {
            addCriterion("src_table_alis =", value, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table_alis = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisNotEqualTo(String value) {
            addCriterion("src_table_alis <>", value, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table_alis <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisGreaterThan(String value) {
            addCriterion("src_table_alis >", value, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table_alis > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisGreaterThanOrEqualTo(String value) {
            addCriterion("src_table_alis >=", value, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table_alis >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisLessThan(String value) {
            addCriterion("src_table_alis <", value, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table_alis < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisLessThanOrEqualTo(String value) {
            addCriterion("src_table_alis <=", value, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_table_alis <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisLike(String value) {
            addCriterion("src_table_alis like", value, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisNotLike(String value) {
            addCriterion("src_table_alis not like", value, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisIn(List<String> values) {
            addCriterion("src_table_alis in", values, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisNotIn(List<String> values) {
            addCriterion("src_table_alis not in", values, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisBetween(String value1, String value2) {
            addCriterion("src_table_alis between", value1, value2, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcTableAlisNotBetween(String value1, String value2) {
            addCriterion("src_table_alis not between", value1, value2, "srcTableAlis");
            return (Criteria) this;
        }

        public Criteria andSrcFieldIsNull() {
            addCriterion("src_field is null");
            return (Criteria) this;
        }

        public Criteria andSrcFieldIsNotNull() {
            addCriterion("src_field is not null");
            return (Criteria) this;
        }

        public Criteria andSrcFieldEqualTo(String value) {
            addCriterion("src_field =", value, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_field = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcFieldNotEqualTo(String value) {
            addCriterion("src_field <>", value, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_field <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcFieldGreaterThan(String value) {
            addCriterion("src_field >", value, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_field > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcFieldGreaterThanOrEqualTo(String value) {
            addCriterion("src_field >=", value, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_field >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcFieldLessThan(String value) {
            addCriterion("src_field <", value, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_field < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcFieldLessThanOrEqualTo(String value) {
            addCriterion("src_field <=", value, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("src_field <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSrcFieldLike(String value) {
            addCriterion("src_field like", value, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldNotLike(String value) {
            addCriterion("src_field not like", value, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldIn(List<String> values) {
            addCriterion("src_field in", values, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldNotIn(List<String> values) {
            addCriterion("src_field not in", values, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldBetween(String value1, String value2) {
            addCriterion("src_field between", value1, value2, "srcField");
            return (Criteria) this;
        }

        public Criteria andSrcFieldNotBetween(String value1, String value2) {
            addCriterion("src_field not between", value1, value2, "srcField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldIsNull() {
            addCriterion("display_field is null");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldIsNotNull() {
            addCriterion("display_field is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldEqualTo(String value) {
            addCriterion("display_field =", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_field = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayFieldNotEqualTo(String value) {
            addCriterion("display_field <>", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_field <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayFieldGreaterThan(String value) {
            addCriterion("display_field >", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_field > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayFieldGreaterThanOrEqualTo(String value) {
            addCriterion("display_field >=", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_field >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayFieldLessThan(String value) {
            addCriterion("display_field <", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_field < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayFieldLessThanOrEqualTo(String value) {
            addCriterion("display_field <=", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_field <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayFieldLike(String value) {
            addCriterion("display_field like", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldNotLike(String value) {
            addCriterion("display_field not like", value, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldIn(List<String> values) {
            addCriterion("display_field in", values, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldNotIn(List<String> values) {
            addCriterion("display_field not in", values, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldBetween(String value1, String value2) {
            addCriterion("display_field between", value1, value2, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayFieldNotBetween(String value1, String value2) {
            addCriterion("display_field not between", value1, value2, "displayField");
            return (Criteria) this;
        }

        public Criteria andDisplayExtIsNull() {
            addCriterion("display_ext is null");
            return (Criteria) this;
        }

        public Criteria andDisplayExtIsNotNull() {
            addCriterion("display_ext is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayExtEqualTo(String value) {
            addCriterion("display_ext =", value, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_ext = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayExtNotEqualTo(String value) {
            addCriterion("display_ext <>", value, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_ext <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayExtGreaterThan(String value) {
            addCriterion("display_ext >", value, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_ext > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayExtGreaterThanOrEqualTo(String value) {
            addCriterion("display_ext >=", value, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_ext >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayExtLessThan(String value) {
            addCriterion("display_ext <", value, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_ext < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayExtLessThanOrEqualTo(String value) {
            addCriterion("display_ext <=", value, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("display_ext <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDisplayExtLike(String value) {
            addCriterion("display_ext like", value, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtNotLike(String value) {
            addCriterion("display_ext not like", value, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtIn(List<String> values) {
            addCriterion("display_ext in", values, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtNotIn(List<String> values) {
            addCriterion("display_ext not in", values, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtBetween(String value1, String value2) {
            addCriterion("display_ext between", value1, value2, "displayExt");
            return (Criteria) this;
        }

        public Criteria andDisplayExtNotBetween(String value1, String value2) {
            addCriterion("display_ext not between", value1, value2, "displayExt");
            return (Criteria) this;
        }

        public Criteria andJoinTypeIsNull() {
            addCriterion("join_type is null");
            return (Criteria) this;
        }

        public Criteria andJoinTypeIsNotNull() {
            addCriterion("join_type is not null");
            return (Criteria) this;
        }

        public Criteria andJoinTypeEqualTo(String value) {
            addCriterion("join_type =", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("join_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andJoinTypeNotEqualTo(String value) {
            addCriterion("join_type <>", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("join_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andJoinTypeGreaterThan(String value) {
            addCriterion("join_type >", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("join_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andJoinTypeGreaterThanOrEqualTo(String value) {
            addCriterion("join_type >=", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("join_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andJoinTypeLessThan(String value) {
            addCriterion("join_type <", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("join_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andJoinTypeLessThanOrEqualTo(String value) {
            addCriterion("join_type <=", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("join_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andJoinTypeLike(String value) {
            addCriterion("join_type like", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeNotLike(String value) {
            addCriterion("join_type not like", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeIn(List<String> values) {
            addCriterion("join_type in", values, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeNotIn(List<String> values) {
            addCriterion("join_type not in", values, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeBetween(String value1, String value2) {
            addCriterion("join_type between", value1, value2, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeNotBetween(String value1, String value2) {
            addCriterion("join_type not between", value1, value2, "joinType");
            return (Criteria) this;
        }

        public Criteria andFilterIsNull() {
            addCriterion("`filter` is null");
            return (Criteria) this;
        }

        public Criteria andFilterIsNotNull() {
            addCriterion("`filter` is not null");
            return (Criteria) this;
        }

        public Criteria andFilterEqualTo(String value) {
            addCriterion("`filter` =", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`filter` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andFilterNotEqualTo(String value) {
            addCriterion("`filter` <>", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`filter` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andFilterGreaterThan(String value) {
            addCriterion("`filter` >", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`filter` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andFilterGreaterThanOrEqualTo(String value) {
            addCriterion("`filter` >=", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`filter` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andFilterLessThan(String value) {
            addCriterion("`filter` <", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`filter` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andFilterLessThanOrEqualTo(String value) {
            addCriterion("`filter` <=", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`filter` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andFilterLike(String value) {
            addCriterion("`filter` like", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterNotLike(String value) {
            addCriterion("`filter` not like", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterIn(List<String> values) {
            addCriterion("`filter` in", values, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterNotIn(List<String> values) {
            addCriterion("`filter` not in", values, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterBetween(String value1, String value2) {
            addCriterion("`filter` between", value1, value2, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterNotBetween(String value1, String value2) {
            addCriterion("`filter` not between", value1, value2, "filter");
            return (Criteria) this;
        }

        public Criteria andDefaultValueIsNull() {
            addCriterion("default_value is null");
            return (Criteria) this;
        }

        public Criteria andDefaultValueIsNotNull() {
            addCriterion("default_value is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultValueEqualTo(String value) {
            addCriterion("default_value =", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("default_value = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDefaultValueNotEqualTo(String value) {
            addCriterion("default_value <>", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("default_value <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDefaultValueGreaterThan(String value) {
            addCriterion("default_value >", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("default_value > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDefaultValueGreaterThanOrEqualTo(String value) {
            addCriterion("default_value >=", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("default_value >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDefaultValueLessThan(String value) {
            addCriterion("default_value <", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("default_value < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDefaultValueLessThanOrEqualTo(String value) {
            addCriterion("default_value <=", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("default_value <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDefaultValueLike(String value) {
            addCriterion("default_value like", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueNotLike(String value) {
            addCriterion("default_value not like", value, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueIn(List<String> values) {
            addCriterion("default_value in", values, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueNotIn(List<String> values) {
            addCriterion("default_value not in", values, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueBetween(String value1, String value2) {
            addCriterion("default_value between", value1, value2, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andDefaultValueNotBetween(String value1, String value2) {
            addCriterion("default_value not between", value1, value2, "defaultValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueIsNull() {
            addCriterion("max_value is null");
            return (Criteria) this;
        }

        public Criteria andMaxValueIsNotNull() {
            addCriterion("max_value is not null");
            return (Criteria) this;
        }

        public Criteria andMaxValueEqualTo(Double value) {
            addCriterion("max_value =", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("max_value = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaxValueNotEqualTo(Double value) {
            addCriterion("max_value <>", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("max_value <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaxValueGreaterThan(Double value) {
            addCriterion("max_value >", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("max_value > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaxValueGreaterThanOrEqualTo(Double value) {
            addCriterion("max_value >=", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("max_value >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaxValueLessThan(Double value) {
            addCriterion("max_value <", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("max_value < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaxValueLessThanOrEqualTo(Double value) {
            addCriterion("max_value <=", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("max_value <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaxValueIn(List<Double> values) {
            addCriterion("max_value in", values, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueNotIn(List<Double> values) {
            addCriterion("max_value not in", values, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueBetween(Double value1, Double value2) {
            addCriterion("max_value between", value1, value2, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueNotBetween(Double value1, Double value2) {
            addCriterion("max_value not between", value1, value2, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMinValueIsNull() {
            addCriterion("min_value is null");
            return (Criteria) this;
        }

        public Criteria andMinValueIsNotNull() {
            addCriterion("min_value is not null");
            return (Criteria) this;
        }

        public Criteria andMinValueEqualTo(Double value) {
            addCriterion("min_value =", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("min_value = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMinValueNotEqualTo(Double value) {
            addCriterion("min_value <>", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("min_value <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMinValueGreaterThan(Double value) {
            addCriterion("min_value >", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("min_value > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMinValueGreaterThanOrEqualTo(Double value) {
            addCriterion("min_value >=", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("min_value >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMinValueLessThan(Double value) {
            addCriterion("min_value <", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("min_value < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMinValueLessThanOrEqualTo(Double value) {
            addCriterion("min_value <=", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("min_value <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMinValueIn(List<Double> values) {
            addCriterion("min_value in", values, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueNotIn(List<Double> values) {
            addCriterion("min_value not in", values, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueBetween(Double value1, Double value2) {
            addCriterion("min_value between", value1, value2, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueNotBetween(Double value1, Double value2) {
            addCriterion("min_value not between", value1, value2, "minValue");
            return (Criteria) this;
        }

        public Criteria andMustInputIsNull() {
            addCriterion("must_input is null");
            return (Criteria) this;
        }

        public Criteria andMustInputIsNotNull() {
            addCriterion("must_input is not null");
            return (Criteria) this;
        }

        public Criteria andMustInputEqualTo(Integer value) {
            addCriterion("must_input =", value, "mustInput");
            return (Criteria) this;
        }

        public Criteria andMustInputEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("must_input = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMustInputNotEqualTo(Integer value) {
            addCriterion("must_input <>", value, "mustInput");
            return (Criteria) this;
        }

        public Criteria andMustInputNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("must_input <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMustInputGreaterThan(Integer value) {
            addCriterion("must_input >", value, "mustInput");
            return (Criteria) this;
        }

        public Criteria andMustInputGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("must_input > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMustInputGreaterThanOrEqualTo(Integer value) {
            addCriterion("must_input >=", value, "mustInput");
            return (Criteria) this;
        }

        public Criteria andMustInputGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("must_input >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMustInputLessThan(Integer value) {
            addCriterion("must_input <", value, "mustInput");
            return (Criteria) this;
        }

        public Criteria andMustInputLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("must_input < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMustInputLessThanOrEqualTo(Integer value) {
            addCriterion("must_input <=", value, "mustInput");
            return (Criteria) this;
        }

        public Criteria andMustInputLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("must_input <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMustInputIn(List<Integer> values) {
            addCriterion("must_input in", values, "mustInput");
            return (Criteria) this;
        }

        public Criteria andMustInputNotIn(List<Integer> values) {
            addCriterion("must_input not in", values, "mustInput");
            return (Criteria) this;
        }

        public Criteria andMustInputBetween(Integer value1, Integer value2) {
            addCriterion("must_input between", value1, value2, "mustInput");
            return (Criteria) this;
        }

        public Criteria andMustInputNotBetween(Integer value1, Integer value2) {
            addCriterion("must_input not between", value1, value2, "mustInput");
            return (Criteria) this;
        }

        public Criteria andNeedSaveIsNull() {
            addCriterion("need_save is null");
            return (Criteria) this;
        }

        public Criteria andNeedSaveIsNotNull() {
            addCriterion("need_save is not null");
            return (Criteria) this;
        }

        public Criteria andNeedSaveEqualTo(Boolean value) {
            addCriterion("need_save =", value, "needSave");
            return (Criteria) this;
        }

        public Criteria andNeedSaveEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("need_save = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNeedSaveNotEqualTo(Boolean value) {
            addCriterion("need_save <>", value, "needSave");
            return (Criteria) this;
        }

        public Criteria andNeedSaveNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("need_save <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNeedSaveGreaterThan(Boolean value) {
            addCriterion("need_save >", value, "needSave");
            return (Criteria) this;
        }

        public Criteria andNeedSaveGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("need_save > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNeedSaveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("need_save >=", value, "needSave");
            return (Criteria) this;
        }

        public Criteria andNeedSaveGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("need_save >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNeedSaveLessThan(Boolean value) {
            addCriterion("need_save <", value, "needSave");
            return (Criteria) this;
        }

        public Criteria andNeedSaveLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("need_save < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNeedSaveLessThanOrEqualTo(Boolean value) {
            addCriterion("need_save <=", value, "needSave");
            return (Criteria) this;
        }

        public Criteria andNeedSaveLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("need_save <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNeedSaveIn(List<Boolean> values) {
            addCriterion("need_save in", values, "needSave");
            return (Criteria) this;
        }

        public Criteria andNeedSaveNotIn(List<Boolean> values) {
            addCriterion("need_save not in", values, "needSave");
            return (Criteria) this;
        }

        public Criteria andNeedSaveBetween(Boolean value1, Boolean value2) {
            addCriterion("need_save between", value1, value2, "needSave");
            return (Criteria) this;
        }

        public Criteria andNeedSaveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("need_save not between", value1, value2, "needSave");
            return (Criteria) this;
        }

        public Criteria andLengthIsNull() {
            addCriterion("`length` is null");
            return (Criteria) this;
        }

        public Criteria andLengthIsNotNull() {
            addCriterion("`length` is not null");
            return (Criteria) this;
        }

        public Criteria andLengthEqualTo(Integer value) {
            addCriterion("`length` =", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`length` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLengthNotEqualTo(Integer value) {
            addCriterion("`length` <>", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`length` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLengthGreaterThan(Integer value) {
            addCriterion("`length` >", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`length` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("`length` >=", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`length` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLengthLessThan(Integer value) {
            addCriterion("`length` <", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`length` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLengthLessThanOrEqualTo(Integer value) {
            addCriterion("`length` <=", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`length` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLengthIn(List<Integer> values) {
            addCriterion("`length` in", values, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotIn(List<Integer> values) {
            addCriterion("`length` not in", values, "length");
            return (Criteria) this;
        }

        public Criteria andLengthBetween(Integer value1, Integer value2) {
            addCriterion("`length` between", value1, value2, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotBetween(Integer value1, Integer value2) {
            addCriterion("`length` not between", value1, value2, "length");
            return (Criteria) this;
        }

        public Criteria andLockIsNull() {
            addCriterion("`lock` is null");
            return (Criteria) this;
        }

        public Criteria andLockIsNotNull() {
            addCriterion("`lock` is not null");
            return (Criteria) this;
        }

        public Criteria andLockEqualTo(Integer value) {
            addCriterion("`lock` =", value, "lock");
            return (Criteria) this;
        }

        public Criteria andLockEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`lock` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLockNotEqualTo(Integer value) {
            addCriterion("`lock` <>", value, "lock");
            return (Criteria) this;
        }

        public Criteria andLockNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`lock` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLockGreaterThan(Integer value) {
            addCriterion("`lock` >", value, "lock");
            return (Criteria) this;
        }

        public Criteria andLockGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`lock` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLockGreaterThanOrEqualTo(Integer value) {
            addCriterion("`lock` >=", value, "lock");
            return (Criteria) this;
        }

        public Criteria andLockGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`lock` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLockLessThan(Integer value) {
            addCriterion("`lock` <", value, "lock");
            return (Criteria) this;
        }

        public Criteria andLockLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`lock` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLockLessThanOrEqualTo(Integer value) {
            addCriterion("`lock` <=", value, "lock");
            return (Criteria) this;
        }

        public Criteria andLockLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("`lock` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLockIn(List<Integer> values) {
            addCriterion("`lock` in", values, "lock");
            return (Criteria) this;
        }

        public Criteria andLockNotIn(List<Integer> values) {
            addCriterion("`lock` not in", values, "lock");
            return (Criteria) this;
        }

        public Criteria andLockBetween(Integer value1, Integer value2) {
            addCriterion("`lock` between", value1, value2, "lock");
            return (Criteria) this;
        }

        public Criteria andLockNotBetween(Integer value1, Integer value2) {
            addCriterion("`lock` not between", value1, value2, "lock");
            return (Criteria) this;
        }

        public Criteria andIsConditionIsNull() {
            addCriterion("is_condition is null");
            return (Criteria) this;
        }

        public Criteria andIsConditionIsNotNull() {
            addCriterion("is_condition is not null");
            return (Criteria) this;
        }

        public Criteria andIsConditionEqualTo(Integer value) {
            addCriterion("is_condition =", value, "isCondition");
            return (Criteria) this;
        }

        public Criteria andIsConditionEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_condition = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsConditionNotEqualTo(Integer value) {
            addCriterion("is_condition <>", value, "isCondition");
            return (Criteria) this;
        }

        public Criteria andIsConditionNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_condition <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsConditionGreaterThan(Integer value) {
            addCriterion("is_condition >", value, "isCondition");
            return (Criteria) this;
        }

        public Criteria andIsConditionGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_condition > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsConditionGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_condition >=", value, "isCondition");
            return (Criteria) this;
        }

        public Criteria andIsConditionGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_condition >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsConditionLessThan(Integer value) {
            addCriterion("is_condition <", value, "isCondition");
            return (Criteria) this;
        }

        public Criteria andIsConditionLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_condition < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsConditionLessThanOrEqualTo(Integer value) {
            addCriterion("is_condition <=", value, "isCondition");
            return (Criteria) this;
        }

        public Criteria andIsConditionLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_condition <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsConditionIn(List<Integer> values) {
            addCriterion("is_condition in", values, "isCondition");
            return (Criteria) this;
        }

        public Criteria andIsConditionNotIn(List<Integer> values) {
            addCriterion("is_condition not in", values, "isCondition");
            return (Criteria) this;
        }

        public Criteria andIsConditionBetween(Integer value1, Integer value2) {
            addCriterion("is_condition between", value1, value2, "isCondition");
            return (Criteria) this;
        }

        public Criteria andIsConditionNotBetween(Integer value1, Integer value2) {
            addCriterion("is_condition not between", value1, value2, "isCondition");
            return (Criteria) this;
        }

        public Criteria andIsCountIsNull() {
            addCriterion("is_count is null");
            return (Criteria) this;
        }

        public Criteria andIsCountIsNotNull() {
            addCriterion("is_count is not null");
            return (Criteria) this;
        }

        public Criteria andIsCountEqualTo(Integer value) {
            addCriterion("is_count =", value, "isCount");
            return (Criteria) this;
        }

        public Criteria andIsCountEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_count = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsCountNotEqualTo(Integer value) {
            addCriterion("is_count <>", value, "isCount");
            return (Criteria) this;
        }

        public Criteria andIsCountNotEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_count <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsCountGreaterThan(Integer value) {
            addCriterion("is_count >", value, "isCount");
            return (Criteria) this;
        }

        public Criteria andIsCountGreaterThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_count > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_count >=", value, "isCount");
            return (Criteria) this;
        }

        public Criteria andIsCountGreaterThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_count >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsCountLessThan(Integer value) {
            addCriterion("is_count <", value, "isCount");
            return (Criteria) this;
        }

        public Criteria andIsCountLessThanColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_count < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsCountLessThanOrEqualTo(Integer value) {
            addCriterion("is_count <=", value, "isCount");
            return (Criteria) this;
        }

        public Criteria andIsCountLessThanOrEqualToColumn(FormField.Column column) {
            addCriterion(new StringBuilder("is_count <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIsCountIn(List<Integer> values) {
            addCriterion("is_count in", values, "isCount");
            return (Criteria) this;
        }

        public Criteria andIsCountNotIn(List<Integer> values) {
            addCriterion("is_count not in", values, "isCount");
            return (Criteria) this;
        }

        public Criteria andIsCountBetween(Integer value1, Integer value2) {
            addCriterion("is_count between", value1, value2, "isCount");
            return (Criteria) this;
        }

        public Criteria andIsCountNotBetween(Integer value1, Integer value2) {
            addCriterion("is_count not between", value1, value2, "isCount");
            return (Criteria) this;
        }
    }

    /**
     * t_form_field
     */
    public static class Criteria extends GeneratedCriteria {
        private FormFieldExample example;

        protected Criteria(FormFieldExample example) {
            super();
            this.example = example;
        }

        public FormFieldExample example() {
            return this.example;
        }

        public Criteria andIf(boolean ifAdd, ICriteriaAdd add) {
            if (ifAdd) {
                add.add(this);
            }
            return this;
        }

        public interface ICriteriaAdd {
            Criteria add(Criteria add);
        }
    }

    /**
     * t_form_field 2019-02-21
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}