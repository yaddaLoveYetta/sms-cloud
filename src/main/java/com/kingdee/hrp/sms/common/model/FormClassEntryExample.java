package com.kingdee.hrp.sms.common.model;

import java.util.ArrayList;
import java.util.List;

public class FormClassEntryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FormClassEntryExample() {
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

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andClassIdNotEqualTo(Integer value) {
            addCriterion("class_id <>", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThan(Integer value) {
            addCriterion("class_id >", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("class_id >=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThan(Integer value) {
            addCriterion("class_id <", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("class_id <=", value, "classId");
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

        public Criteria andEntryIndexIsNull() {
            addCriterion("entry_index is null");
            return (Criteria) this;
        }

        public Criteria andEntryIndexIsNotNull() {
            addCriterion("entry_index is not null");
            return (Criteria) this;
        }

        public Criteria andEntryIndexEqualTo(Integer value) {
            addCriterion("entry_index =", value, "entryIndex");
            return (Criteria) this;
        }

        public Criteria andEntryIndexNotEqualTo(Integer value) {
            addCriterion("entry_index <>", value, "entryIndex");
            return (Criteria) this;
        }

        public Criteria andEntryIndexGreaterThan(Integer value) {
            addCriterion("entry_index >", value, "entryIndex");
            return (Criteria) this;
        }

        public Criteria andEntryIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("entry_index >=", value, "entryIndex");
            return (Criteria) this;
        }

        public Criteria andEntryIndexLessThan(Integer value) {
            addCriterion("entry_index <", value, "entryIndex");
            return (Criteria) this;
        }

        public Criteria andEntryIndexLessThanOrEqualTo(Integer value) {
            addCriterion("entry_index <=", value, "entryIndex");
            return (Criteria) this;
        }

        public Criteria andEntryIndexIn(List<Integer> values) {
            addCriterion("entry_index in", values, "entryIndex");
            return (Criteria) this;
        }

        public Criteria andEntryIndexNotIn(List<Integer> values) {
            addCriterion("entry_index not in", values, "entryIndex");
            return (Criteria) this;
        }

        public Criteria andEntryIndexBetween(Integer value1, Integer value2) {
            addCriterion("entry_index between", value1, value2, "entryIndex");
            return (Criteria) this;
        }

        public Criteria andEntryIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("entry_index not between", value1, value2, "entryIndex");
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

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
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

        public Criteria andTableNameIsNull() {
            addCriterion("`table_name` is null");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNotNull() {
            addCriterion("`table_name` is not null");
            return (Criteria) this;
        }

        public Criteria andTableNameEqualTo(String value) {
            addCriterion("`table_name` =", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotEqualTo(String value) {
            addCriterion("`table_name` <>", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThan(String value) {
            addCriterion("`table_name` >", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("`table_name` >=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThan(String value) {
            addCriterion("`table_name` <", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThanOrEqualTo(String value) {
            addCriterion("`table_name` <=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLike(String value) {
            addCriterion("`table_name` like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotLike(String value) {
            addCriterion("`table_name` not like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameIn(List<String> values) {
            addCriterion("`table_name` in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotIn(List<String> values) {
            addCriterion("`table_name` not in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameBetween(String value1, String value2) {
            addCriterion("`table_name` between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotBetween(String value1, String value2) {
            addCriterion("`table_name` not between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyIsNull() {
            addCriterion("primary_key is null");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyIsNotNull() {
            addCriterion("primary_key is not null");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyEqualTo(String value) {
            addCriterion("primary_key =", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyNotEqualTo(String value) {
            addCriterion("primary_key <>", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyGreaterThan(String value) {
            addCriterion("primary_key >", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyGreaterThanOrEqualTo(String value) {
            addCriterion("primary_key >=", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyLessThan(String value) {
            addCriterion("primary_key <", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyLessThanOrEqualTo(String value) {
            addCriterion("primary_key <=", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyLike(String value) {
            addCriterion("primary_key like", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyNotLike(String value) {
            addCriterion("primary_key not like", value, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyIn(List<String> values) {
            addCriterion("primary_key in", values, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyNotIn(List<String> values) {
            addCriterion("primary_key not in", values, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyBetween(String value1, String value2) {
            addCriterion("primary_key between", value1, value2, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andPrimaryKeyNotBetween(String value1, String value2) {
            addCriterion("primary_key not between", value1, value2, "primaryKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyIsNull() {
            addCriterion("foreign_key is null");
            return (Criteria) this;
        }

        public Criteria andForeignKeyIsNotNull() {
            addCriterion("foreign_key is not null");
            return (Criteria) this;
        }

        public Criteria andForeignKeyEqualTo(String value) {
            addCriterion("foreign_key =", value, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyNotEqualTo(String value) {
            addCriterion("foreign_key <>", value, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyGreaterThan(String value) {
            addCriterion("foreign_key >", value, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyGreaterThanOrEqualTo(String value) {
            addCriterion("foreign_key >=", value, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyLessThan(String value) {
            addCriterion("foreign_key <", value, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyLessThanOrEqualTo(String value) {
            addCriterion("foreign_key <=", value, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyLike(String value) {
            addCriterion("foreign_key like", value, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyNotLike(String value) {
            addCriterion("foreign_key not like", value, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyIn(List<String> values) {
            addCriterion("foreign_key in", values, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyNotIn(List<String> values) {
            addCriterion("foreign_key not in", values, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyBetween(String value1, String value2) {
            addCriterion("foreign_key between", value1, value2, "foreignKey");
            return (Criteria) this;
        }

        public Criteria andForeignKeyNotBetween(String value1, String value2) {
            addCriterion("foreign_key not between", value1, value2, "foreignKey");
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

        public Criteria andJoinTypeNotEqualTo(String value) {
            addCriterion("join_type <>", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeGreaterThan(String value) {
            addCriterion("join_type >", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeGreaterThanOrEqualTo(String value) {
            addCriterion("join_type >=", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeLessThan(String value) {
            addCriterion("join_type <", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeLessThanOrEqualTo(String value) {
            addCriterion("join_type <=", value, "joinType");
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
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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