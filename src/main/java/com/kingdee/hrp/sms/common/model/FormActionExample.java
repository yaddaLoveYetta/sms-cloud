package com.kingdee.hrp.sms.common.model;

import java.util.ArrayList;
import java.util.List;

public class FormActionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FormActionExample() {
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

        public Criteria andAccessMaskIsNull() {
            addCriterion("access_mask is null");
            return (Criteria) this;
        }

        public Criteria andAccessMaskIsNotNull() {
            addCriterion("access_mask is not null");
            return (Criteria) this;
        }

        public Criteria andAccessMaskEqualTo(Integer value) {
            addCriterion("access_mask =", value, "accessMask");
            return (Criteria) this;
        }

        public Criteria andAccessMaskNotEqualTo(Integer value) {
            addCriterion("access_mask <>", value, "accessMask");
            return (Criteria) this;
        }

        public Criteria andAccessMaskGreaterThan(Integer value) {
            addCriterion("access_mask >", value, "accessMask");
            return (Criteria) this;
        }

        public Criteria andAccessMaskGreaterThanOrEqualTo(Integer value) {
            addCriterion("access_mask >=", value, "accessMask");
            return (Criteria) this;
        }

        public Criteria andAccessMaskLessThan(Integer value) {
            addCriterion("access_mask <", value, "accessMask");
            return (Criteria) this;
        }

        public Criteria andAccessMaskLessThanOrEqualTo(Integer value) {
            addCriterion("access_mask <=", value, "accessMask");
            return (Criteria) this;
        }

        public Criteria andAccessMaskIn(List<Integer> values) {
            addCriterion("access_mask in", values, "accessMask");
            return (Criteria) this;
        }

        public Criteria andAccessMaskNotIn(List<Integer> values) {
            addCriterion("access_mask not in", values, "accessMask");
            return (Criteria) this;
        }

        public Criteria andAccessMaskBetween(Integer value1, Integer value2) {
            addCriterion("access_mask between", value1, value2, "accessMask");
            return (Criteria) this;
        }

        public Criteria andAccessMaskNotBetween(Integer value1, Integer value2) {
            addCriterion("access_mask not between", value1, value2, "accessMask");
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

        public Criteria andTextIsNull() {
            addCriterion("`text` is null");
            return (Criteria) this;
        }

        public Criteria andTextIsNotNull() {
            addCriterion("`text` is not null");
            return (Criteria) this;
        }

        public Criteria andTextEqualTo(String value) {
            addCriterion("`text` =", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotEqualTo(String value) {
            addCriterion("`text` <>", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThan(String value) {
            addCriterion("`text` >", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThanOrEqualTo(String value) {
            addCriterion("`text` >=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThan(String value) {
            addCriterion("`text` <", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThanOrEqualTo(String value) {
            addCriterion("`text` <=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLike(String value) {
            addCriterion("`text` like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotLike(String value) {
            addCriterion("`text` not like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextIn(List<String> values) {
            addCriterion("`text` in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotIn(List<String> values) {
            addCriterion("`text` not in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextBetween(String value1, String value2) {
            addCriterion("`text` between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotBetween(String value1, String value2) {
            addCriterion("`text` not between", value1, value2, "text");
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

        public Criteria andIndexNotEqualTo(Integer value) {
            addCriterion("`index` <>", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexGreaterThan(Integer value) {
            addCriterion("`index` >", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("`index` >=", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexLessThan(Integer value) {
            addCriterion("`index` <", value, "index");
            return (Criteria) this;
        }

        public Criteria andIndexLessThanOrEqualTo(Integer value) {
            addCriterion("`index` <=", value, "index");
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

        public Criteria andAccessUseIsNull() {
            addCriterion("access_use is null");
            return (Criteria) this;
        }

        public Criteria andAccessUseIsNotNull() {
            addCriterion("access_use is not null");
            return (Criteria) this;
        }

        public Criteria andAccessUseEqualTo(Integer value) {
            addCriterion("access_use =", value, "accessUse");
            return (Criteria) this;
        }

        public Criteria andAccessUseNotEqualTo(Integer value) {
            addCriterion("access_use <>", value, "accessUse");
            return (Criteria) this;
        }

        public Criteria andAccessUseGreaterThan(Integer value) {
            addCriterion("access_use >", value, "accessUse");
            return (Criteria) this;
        }

        public Criteria andAccessUseGreaterThanOrEqualTo(Integer value) {
            addCriterion("access_use >=", value, "accessUse");
            return (Criteria) this;
        }

        public Criteria andAccessUseLessThan(Integer value) {
            addCriterion("access_use <", value, "accessUse");
            return (Criteria) this;
        }

        public Criteria andAccessUseLessThanOrEqualTo(Integer value) {
            addCriterion("access_use <=", value, "accessUse");
            return (Criteria) this;
        }

        public Criteria andAccessUseIn(List<Integer> values) {
            addCriterion("access_use in", values, "accessUse");
            return (Criteria) this;
        }

        public Criteria andAccessUseNotIn(List<Integer> values) {
            addCriterion("access_use not in", values, "accessUse");
            return (Criteria) this;
        }

        public Criteria andAccessUseBetween(Integer value1, Integer value2) {
            addCriterion("access_use between", value1, value2, "accessUse");
            return (Criteria) this;
        }

        public Criteria andAccessUseNotBetween(Integer value1, Integer value2) {
            addCriterion("access_use not between", value1, value2, "accessUse");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeIsNull() {
            addCriterion("owner_type is null");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeIsNotNull() {
            addCriterion("owner_type is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeEqualTo(Integer value) {
            addCriterion("owner_type =", value, "ownerType");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeNotEqualTo(Integer value) {
            addCriterion("owner_type <>", value, "ownerType");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeGreaterThan(Integer value) {
            addCriterion("owner_type >", value, "ownerType");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("owner_type >=", value, "ownerType");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeLessThan(Integer value) {
            addCriterion("owner_type <", value, "ownerType");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("owner_type <=", value, "ownerType");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeIn(List<Integer> values) {
            addCriterion("owner_type in", values, "ownerType");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeNotIn(List<Integer> values) {
            addCriterion("owner_type not in", values, "ownerType");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeBetween(Integer value1, Integer value2) {
            addCriterion("owner_type between", value1, value2, "ownerType");
            return (Criteria) this;
        }

        public Criteria andOwnerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("owner_type not between", value1, value2, "ownerType");
            return (Criteria) this;
        }

        public Criteria andGroupIsNull() {
            addCriterion("`group` is null");
            return (Criteria) this;
        }

        public Criteria andGroupIsNotNull() {
            addCriterion("`group` is not null");
            return (Criteria) this;
        }

        public Criteria andGroupEqualTo(Integer value) {
            addCriterion("`group` =", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotEqualTo(Integer value) {
            addCriterion("`group` <>", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupGreaterThan(Integer value) {
            addCriterion("`group` >", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupGreaterThanOrEqualTo(Integer value) {
            addCriterion("`group` >=", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupLessThan(Integer value) {
            addCriterion("`group` <", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupLessThanOrEqualTo(Integer value) {
            addCriterion("`group` <=", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupIn(List<Integer> values) {
            addCriterion("`group` in", values, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotIn(List<Integer> values) {
            addCriterion("`group` not in", values, "group");
            return (Criteria) this;
        }

        public Criteria andGroupBetween(Integer value1, Integer value2) {
            addCriterion("`group` between", value1, value2, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotBetween(Integer value1, Integer value2) {
            addCriterion("`group` not between", value1, value2, "group");
            return (Criteria) this;
        }

        public Criteria andIconIsNull() {
            addCriterion("icon is null");
            return (Criteria) this;
        }

        public Criteria andIconIsNotNull() {
            addCriterion("icon is not null");
            return (Criteria) this;
        }

        public Criteria andIconEqualTo(String value) {
            addCriterion("icon =", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotEqualTo(String value) {
            addCriterion("icon <>", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconGreaterThan(String value) {
            addCriterion("icon >", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconGreaterThanOrEqualTo(String value) {
            addCriterion("icon >=", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLessThan(String value) {
            addCriterion("icon <", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLessThanOrEqualTo(String value) {
            addCriterion("icon <=", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLike(String value) {
            addCriterion("icon like", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotLike(String value) {
            addCriterion("icon not like", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconIn(List<String> values) {
            addCriterion("icon in", values, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotIn(List<String> values) {
            addCriterion("icon not in", values, "icon");
            return (Criteria) this;
        }

        public Criteria andIconBetween(String value1, String value2) {
            addCriterion("icon between", value1, value2, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotBetween(String value1, String value2) {
            addCriterion("icon not between", value1, value2, "icon");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("`desc` is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("`desc` is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("`desc` =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("`desc` <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("`desc` >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("`desc` >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("`desc` <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("`desc` <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("`desc` like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("`desc` not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("`desc` in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("`desc` not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("`desc` between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("`desc` not between", value1, value2, "desc");
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