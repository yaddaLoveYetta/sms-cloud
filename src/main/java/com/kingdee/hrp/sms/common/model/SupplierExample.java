package com.kingdee.hrp.sms.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupplierExample() {
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

    public SupplierExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public SupplierExample orderBy(String ... orderByClauses) {
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
     * t_supplier 2019-02-21
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNumberIsNull() {
            addCriterion("`number` is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("`number` is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(String value) {
            addCriterion("`number` =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`number` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(String value) {
            addCriterion("`number` <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`number` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(String value) {
            addCriterion("`number` >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`number` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(String value) {
            addCriterion("`number` >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`number` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(String value) {
            addCriterion("`number` <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`number` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(String value) {
            addCriterion("`number` <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`number` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberLike(String value) {
            addCriterion("`number` like", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotLike(String value) {
            addCriterion("`number` not like", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<String> values) {
            addCriterion("`number` in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<String> values) {
            addCriterion("`number` not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(String value1, String value2) {
            addCriterion("`number` between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(String value1, String value2) {
            addCriterion("`number` not between", value1, value2, "number");
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

        public Criteria andNameEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`name` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`name` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`name` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`name` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`name` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualToColumn(Supplier.Column column) {
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

        public Criteria andCreditCodeIsNull() {
            addCriterion("credit_code is null");
            return (Criteria) this;
        }

        public Criteria andCreditCodeIsNotNull() {
            addCriterion("credit_code is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCodeEqualTo(String value) {
            addCriterion("credit_code =", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("credit_code = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreditCodeNotEqualTo(String value) {
            addCriterion("credit_code <>", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("credit_code <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreditCodeGreaterThan(String value) {
            addCriterion("credit_code >", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("credit_code > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreditCodeGreaterThanOrEqualTo(String value) {
            addCriterion("credit_code >=", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("credit_code >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreditCodeLessThan(String value) {
            addCriterion("credit_code <", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("credit_code < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreditCodeLessThanOrEqualTo(String value) {
            addCriterion("credit_code <=", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("credit_code <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreditCodeLike(String value) {
            addCriterion("credit_code like", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeNotLike(String value) {
            addCriterion("credit_code not like", value, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeIn(List<String> values) {
            addCriterion("credit_code in", values, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeNotIn(List<String> values) {
            addCriterion("credit_code not in", values, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeBetween(String value1, String value2) {
            addCriterion("credit_code between", value1, value2, "creditCode");
            return (Criteria) this;
        }

        public Criteria andCreditCodeNotBetween(String value1, String value2) {
            addCriterion("credit_code not between", value1, value2, "creditCode");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("address = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("address <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("address > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("address >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("address < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("address <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andIntroductionIsNull() {
            addCriterion("introduction is null");
            return (Criteria) this;
        }

        public Criteria andIntroductionIsNotNull() {
            addCriterion("introduction is not null");
            return (Criteria) this;
        }

        public Criteria andIntroductionEqualTo(String value) {
            addCriterion("introduction =", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("introduction = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIntroductionNotEqualTo(String value) {
            addCriterion("introduction <>", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("introduction <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIntroductionGreaterThan(String value) {
            addCriterion("introduction >", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("introduction > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIntroductionGreaterThanOrEqualTo(String value) {
            addCriterion("introduction >=", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("introduction >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIntroductionLessThan(String value) {
            addCriterion("introduction <", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("introduction < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIntroductionLessThanOrEqualTo(String value) {
            addCriterion("introduction <=", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("introduction <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIntroductionLike(String value) {
            addCriterion("introduction like", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotLike(String value) {
            addCriterion("introduction not like", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionIn(List<String> values) {
            addCriterion("introduction in", values, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotIn(List<String> values) {
            addCriterion("introduction not in", values, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionBetween(String value1, String value2) {
            addCriterion("introduction between", value1, value2, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotBetween(String value1, String value2) {
            addCriterion("introduction not between", value1, value2, "introduction");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceIsNull() {
            addCriterion("development_experience is null");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceIsNotNull() {
            addCriterion("development_experience is not null");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceEqualTo(String value) {
            addCriterion("development_experience =", value, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("development_experience = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceNotEqualTo(String value) {
            addCriterion("development_experience <>", value, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("development_experience <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceGreaterThan(String value) {
            addCriterion("development_experience >", value, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("development_experience > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceGreaterThanOrEqualTo(String value) {
            addCriterion("development_experience >=", value, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("development_experience >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceLessThan(String value) {
            addCriterion("development_experience <", value, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("development_experience < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceLessThanOrEqualTo(String value) {
            addCriterion("development_experience <=", value, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("development_experience <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceLike(String value) {
            addCriterion("development_experience like", value, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceNotLike(String value) {
            addCriterion("development_experience not like", value, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceIn(List<String> values) {
            addCriterion("development_experience in", values, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceNotIn(List<String> values) {
            addCriterion("development_experience not in", values, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceBetween(String value1, String value2) {
            addCriterion("development_experience between", value1, value2, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andDevelopmentExperienceNotBetween(String value1, String value2) {
            addCriterion("development_experience not between", value1, value2, "developmentExperience");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIsNull() {
            addCriterion("legal_person is null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIsNotNull() {
            addCriterion("legal_person is not null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonEqualTo(String value) {
            addCriterion("legal_person =", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("legal_person = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotEqualTo(String value) {
            addCriterion("legal_person <>", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("legal_person <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThan(String value) {
            addCriterion("legal_person >", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("legal_person > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThanOrEqualTo(String value) {
            addCriterion("legal_person >=", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("legal_person >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThan(String value) {
            addCriterion("legal_person <", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("legal_person < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThanOrEqualTo(String value) {
            addCriterion("legal_person <=", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("legal_person <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLegalPersonLike(String value) {
            addCriterion("legal_person like", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotLike(String value) {
            addCriterion("legal_person not like", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIn(List<String> values) {
            addCriterion("legal_person in", values, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotIn(List<String> values) {
            addCriterion("legal_person not in", values, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonBetween(String value1, String value2) {
            addCriterion("legal_person between", value1, value2, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotBetween(String value1, String value2) {
            addCriterion("legal_person not between", value1, value2, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andContactsIsNull() {
            addCriterion("contacts is null");
            return (Criteria) this;
        }

        public Criteria andContactsIsNotNull() {
            addCriterion("contacts is not null");
            return (Criteria) this;
        }

        public Criteria andContactsEqualTo(String value) {
            addCriterion("contacts =", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("contacts = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andContactsNotEqualTo(String value) {
            addCriterion("contacts <>", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("contacts <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andContactsGreaterThan(String value) {
            addCriterion("contacts >", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("contacts > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andContactsGreaterThanOrEqualTo(String value) {
            addCriterion("contacts >=", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("contacts >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andContactsLessThan(String value) {
            addCriterion("contacts <", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("contacts < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andContactsLessThanOrEqualTo(String value) {
            addCriterion("contacts <=", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("contacts <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andContactsLike(String value) {
            addCriterion("contacts like", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsNotLike(String value) {
            addCriterion("contacts not like", value, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsIn(List<String> values) {
            addCriterion("contacts in", values, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsNotIn(List<String> values) {
            addCriterion("contacts not in", values, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsBetween(String value1, String value2) {
            addCriterion("contacts between", value1, value2, "contacts");
            return (Criteria) this;
        }

        public Criteria andContactsNotBetween(String value1, String value2) {
            addCriterion("contacts not between", value1, value2, "contacts");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("phone = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("phone <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("phone > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("phone >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("phone < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("phone <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIsNull() {
            addCriterion("company_type is null");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIsNotNull() {
            addCriterion("company_type is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeEqualTo(String value) {
            addCriterion("company_type =", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("company_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCompanyTypeNotEqualTo(String value) {
            addCriterion("company_type <>", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("company_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCompanyTypeGreaterThan(String value) {
            addCriterion("company_type >", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("company_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCompanyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("company_type >=", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("company_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCompanyTypeLessThan(String value) {
            addCriterion("company_type <", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("company_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCompanyTypeLessThanOrEqualTo(String value) {
            addCriterion("company_type <=", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("company_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCompanyTypeLike(String value) {
            addCriterion("company_type like", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeNotLike(String value) {
            addCriterion("company_type not like", value, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeIn(List<String> values) {
            addCriterion("company_type in", values, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeNotIn(List<String> values) {
            addCriterion("company_type not in", values, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeBetween(String value1, String value2) {
            addCriterion("company_type between", value1, value2, "companyType");
            return (Criteria) this;
        }

        public Criteria andCompanyTypeNotBetween(String value1, String value2) {
            addCriterion("company_type not between", value1, value2, "companyType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeIsNull() {
            addCriterion("industry_type is null");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeIsNotNull() {
            addCriterion("industry_type is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeEqualTo(String value) {
            addCriterion("industry_type =", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("industry_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndustryTypeNotEqualTo(String value) {
            addCriterion("industry_type <>", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("industry_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndustryTypeGreaterThan(String value) {
            addCriterion("industry_type >", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("industry_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndustryTypeGreaterThanOrEqualTo(String value) {
            addCriterion("industry_type >=", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("industry_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndustryTypeLessThan(String value) {
            addCriterion("industry_type <", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("industry_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndustryTypeLessThanOrEqualTo(String value) {
            addCriterion("industry_type <=", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("industry_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIndustryTypeLike(String value) {
            addCriterion("industry_type like", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeNotLike(String value) {
            addCriterion("industry_type not like", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeIn(List<String> values) {
            addCriterion("industry_type in", values, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeNotIn(List<String> values) {
            addCriterion("industry_type not in", values, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeBetween(String value1, String value2) {
            addCriterion("industry_type between", value1, value2, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeNotBetween(String value1, String value2) {
            addCriterion("industry_type not between", value1, value2, "industryType");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginIsNull() {
            addCriterion("validity_period_begin is null");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginIsNotNull() {
            addCriterion("validity_period_begin is not null");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginEqualTo(Date value) {
            addCriterion("validity_period_begin =", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_begin = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginNotEqualTo(Date value) {
            addCriterion("validity_period_begin <>", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_begin <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginGreaterThan(Date value) {
            addCriterion("validity_period_begin >", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_begin > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginGreaterThanOrEqualTo(Date value) {
            addCriterion("validity_period_begin >=", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_begin >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginLessThan(Date value) {
            addCriterion("validity_period_begin <", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_begin < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginLessThanOrEqualTo(Date value) {
            addCriterion("validity_period_begin <=", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_begin <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginIn(List<Date> values) {
            addCriterion("validity_period_begin in", values, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginNotIn(List<Date> values) {
            addCriterion("validity_period_begin not in", values, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginBetween(Date value1, Date value2) {
            addCriterion("validity_period_begin between", value1, value2, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginNotBetween(Date value1, Date value2) {
            addCriterion("validity_period_begin not between", value1, value2, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndIsNull() {
            addCriterion("validity_period_end is null");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndIsNotNull() {
            addCriterion("validity_period_end is not null");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndEqualTo(Date value) {
            addCriterion("validity_period_end =", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_end = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndNotEqualTo(Date value) {
            addCriterion("validity_period_end <>", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_end <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndGreaterThan(Date value) {
            addCriterion("validity_period_end >", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_end > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndGreaterThanOrEqualTo(Date value) {
            addCriterion("validity_period_end >=", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_end >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndLessThan(Date value) {
            addCriterion("validity_period_end <", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_end < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndLessThanOrEqualTo(Date value) {
            addCriterion("validity_period_end <=", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("validity_period_end <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndIn(List<Date> values) {
            addCriterion("validity_period_end in", values, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndNotIn(List<Date> values) {
            addCriterion("validity_period_end not in", values, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndBetween(Date value1, Date value2) {
            addCriterion("validity_period_end between", value1, value2, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndNotBetween(Date value1, Date value2) {
            addCriterion("validity_period_end not between", value1, value2, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andIssueDateIsNull() {
            addCriterion("issue_date is null");
            return (Criteria) this;
        }

        public Criteria andIssueDateIsNotNull() {
            addCriterion("issue_date is not null");
            return (Criteria) this;
        }

        public Criteria andIssueDateEqualTo(Date value) {
            addCriterion("issue_date =", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueDateNotEqualTo(Date value) {
            addCriterion("issue_date <>", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueDateGreaterThan(Date value) {
            addCriterion("issue_date >", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueDateGreaterThanOrEqualTo(Date value) {
            addCriterion("issue_date >=", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueDateLessThan(Date value) {
            addCriterion("issue_date <", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueDateLessThanOrEqualTo(Date value) {
            addCriterion("issue_date <=", value, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueDateIn(List<Date> values) {
            addCriterion("issue_date in", values, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateNotIn(List<Date> values) {
            addCriterion("issue_date not in", values, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateBetween(Date value1, Date value2) {
            addCriterion("issue_date between", value1, value2, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueDateNotBetween(Date value1, Date value2) {
            addCriterion("issue_date not between", value1, value2, "issueDate");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyIsNull() {
            addCriterion("issue_agency is null");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyIsNotNull() {
            addCriterion("issue_agency is not null");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyEqualTo(String value) {
            addCriterion("issue_agency =", value, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_agency = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueAgencyNotEqualTo(String value) {
            addCriterion("issue_agency <>", value, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_agency <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueAgencyGreaterThan(String value) {
            addCriterion("issue_agency >", value, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_agency > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueAgencyGreaterThanOrEqualTo(String value) {
            addCriterion("issue_agency >=", value, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_agency >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueAgencyLessThan(String value) {
            addCriterion("issue_agency <", value, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_agency < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueAgencyLessThanOrEqualTo(String value) {
            addCriterion("issue_agency <=", value, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("issue_agency <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueAgencyLike(String value) {
            addCriterion("issue_agency like", value, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyNotLike(String value) {
            addCriterion("issue_agency not like", value, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyIn(List<String> values) {
            addCriterion("issue_agency in", values, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyNotIn(List<String> values) {
            addCriterion("issue_agency not in", values, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyBetween(String value1, String value2) {
            addCriterion("issue_agency between", value1, value2, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andIssueAgencyNotBetween(String value1, String value2) {
            addCriterion("issue_agency not between", value1, value2, "issueAgency");
            return (Criteria) this;
        }

        public Criteria andMainBusinessIsNull() {
            addCriterion("main_business is null");
            return (Criteria) this;
        }

        public Criteria andMainBusinessIsNotNull() {
            addCriterion("main_business is not null");
            return (Criteria) this;
        }

        public Criteria andMainBusinessEqualTo(String value) {
            addCriterion("main_business =", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("main_business = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotEqualTo(String value) {
            addCriterion("main_business <>", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("main_business <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMainBusinessGreaterThan(String value) {
            addCriterion("main_business >", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("main_business > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMainBusinessGreaterThanOrEqualTo(String value) {
            addCriterion("main_business >=", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("main_business >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMainBusinessLessThan(String value) {
            addCriterion("main_business <", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("main_business < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMainBusinessLessThanOrEqualTo(String value) {
            addCriterion("main_business <=", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("main_business <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMainBusinessLike(String value) {
            addCriterion("main_business like", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotLike(String value) {
            addCriterion("main_business not like", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessIn(List<String> values) {
            addCriterion("main_business in", values, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotIn(List<String> values) {
            addCriterion("main_business not in", values, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessBetween(String value1, String value2) {
            addCriterion("main_business between", value1, value2, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotBetween(String value1, String value2) {
            addCriterion("main_business not between", value1, value2, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andAccountBankIsNull() {
            addCriterion("account_bank is null");
            return (Criteria) this;
        }

        public Criteria andAccountBankIsNotNull() {
            addCriterion("account_bank is not null");
            return (Criteria) this;
        }

        public Criteria andAccountBankEqualTo(String value) {
            addCriterion("account_bank =", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_bank = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountBankNotEqualTo(String value) {
            addCriterion("account_bank <>", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_bank <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountBankGreaterThan(String value) {
            addCriterion("account_bank >", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_bank > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountBankGreaterThanOrEqualTo(String value) {
            addCriterion("account_bank >=", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_bank >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountBankLessThan(String value) {
            addCriterion("account_bank <", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_bank < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountBankLessThanOrEqualTo(String value) {
            addCriterion("account_bank <=", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_bank <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountBankLike(String value) {
            addCriterion("account_bank like", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotLike(String value) {
            addCriterion("account_bank not like", value, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankIn(List<String> values) {
            addCriterion("account_bank in", values, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotIn(List<String> values) {
            addCriterion("account_bank not in", values, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankBetween(String value1, String value2) {
            addCriterion("account_bank between", value1, value2, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountBankNotBetween(String value1, String value2) {
            addCriterion("account_bank not between", value1, value2, "accountBank");
            return (Criteria) this;
        }

        public Criteria andAccountNumberIsNull() {
            addCriterion("account_number is null");
            return (Criteria) this;
        }

        public Criteria andAccountNumberIsNotNull() {
            addCriterion("account_number is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNumberEqualTo(String value) {
            addCriterion("account_number =", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_number = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotEqualTo(String value) {
            addCriterion("account_number <>", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_number <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountNumberGreaterThan(String value) {
            addCriterion("account_number >", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_number > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountNumberGreaterThanOrEqualTo(String value) {
            addCriterion("account_number >=", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_number >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountNumberLessThan(String value) {
            addCriterion("account_number <", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_number < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountNumberLessThanOrEqualTo(String value) {
            addCriterion("account_number <=", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_number <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountNumberLike(String value) {
            addCriterion("account_number like", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotLike(String value) {
            addCriterion("account_number not like", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberIn(List<String> values) {
            addCriterion("account_number in", values, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotIn(List<String> values) {
            addCriterion("account_number not in", values, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberBetween(String value1, String value2) {
            addCriterion("account_number between", value1, value2, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotBetween(String value1, String value2) {
            addCriterion("account_number not between", value1, value2, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountAddressIsNull() {
            addCriterion("account_address is null");
            return (Criteria) this;
        }

        public Criteria andAccountAddressIsNotNull() {
            addCriterion("account_address is not null");
            return (Criteria) this;
        }

        public Criteria andAccountAddressEqualTo(String value) {
            addCriterion("account_address =", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_address = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountAddressNotEqualTo(String value) {
            addCriterion("account_address <>", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_address <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountAddressGreaterThan(String value) {
            addCriterion("account_address >", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_address > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountAddressGreaterThanOrEqualTo(String value) {
            addCriterion("account_address >=", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_address >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountAddressLessThan(String value) {
            addCriterion("account_address <", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_address < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountAddressLessThanOrEqualTo(String value) {
            addCriterion("account_address <=", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("account_address <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAccountAddressLike(String value) {
            addCriterion("account_address like", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressNotLike(String value) {
            addCriterion("account_address not like", value, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressIn(List<String> values) {
            addCriterion("account_address in", values, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressNotIn(List<String> values) {
            addCriterion("account_address not in", values, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressBetween(String value1, String value2) {
            addCriterion("account_address between", value1, value2, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andAccountAddressNotBetween(String value1, String value2) {
            addCriterion("account_address not between", value1, value2, "accountAddress");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyIsNull() {
            addCriterion("clearing_company is null");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyIsNotNull() {
            addCriterion("clearing_company is not null");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyEqualTo(String value) {
            addCriterion("clearing_company =", value, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_company = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCompanyNotEqualTo(String value) {
            addCriterion("clearing_company <>", value, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_company <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCompanyGreaterThan(String value) {
            addCriterion("clearing_company >", value, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_company > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("clearing_company >=", value, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_company >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCompanyLessThan(String value) {
            addCriterion("clearing_company <", value, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_company < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCompanyLessThanOrEqualTo(String value) {
            addCriterion("clearing_company <=", value, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_company <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCompanyLike(String value) {
            addCriterion("clearing_company like", value, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyNotLike(String value) {
            addCriterion("clearing_company not like", value, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyIn(List<String> values) {
            addCriterion("clearing_company in", values, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyNotIn(List<String> values) {
            addCriterion("clearing_company not in", values, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyBetween(String value1, String value2) {
            addCriterion("clearing_company between", value1, value2, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCompanyNotBetween(String value1, String value2) {
            addCriterion("clearing_company not between", value1, value2, "clearingCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyIsNull() {
            addCriterion("delivery_company is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyIsNotNull() {
            addCriterion("delivery_company is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyEqualTo(String value) {
            addCriterion("delivery_company =", value, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("delivery_company = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyNotEqualTo(String value) {
            addCriterion("delivery_company <>", value, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("delivery_company <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyGreaterThan(String value) {
            addCriterion("delivery_company >", value, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("delivery_company > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("delivery_company >=", value, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("delivery_company >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyLessThan(String value) {
            addCriterion("delivery_company <", value, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("delivery_company < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyLessThanOrEqualTo(String value) {
            addCriterion("delivery_company <=", value, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("delivery_company <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyLike(String value) {
            addCriterion("delivery_company like", value, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyNotLike(String value) {
            addCriterion("delivery_company not like", value, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyIn(List<String> values) {
            addCriterion("delivery_company in", values, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyNotIn(List<String> values) {
            addCriterion("delivery_company not in", values, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyBetween(String value1, String value2) {
            addCriterion("delivery_company between", value1, value2, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andDeliveryCompanyNotBetween(String value1, String value2) {
            addCriterion("delivery_company not between", value1, value2, "deliveryCompany");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyIsNull() {
            addCriterion("clearing_currency is null");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyIsNotNull() {
            addCriterion("clearing_currency is not null");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyEqualTo(String value) {
            addCriterion("clearing_currency =", value, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_currency = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyNotEqualTo(String value) {
            addCriterion("clearing_currency <>", value, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_currency <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyGreaterThan(String value) {
            addCriterion("clearing_currency >", value, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_currency > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("clearing_currency >=", value, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_currency >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyLessThan(String value) {
            addCriterion("clearing_currency <", value, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_currency < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyLessThanOrEqualTo(String value) {
            addCriterion("clearing_currency <=", value, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_currency <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyLike(String value) {
            addCriterion("clearing_currency like", value, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyNotLike(String value) {
            addCriterion("clearing_currency not like", value, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyIn(List<String> values) {
            addCriterion("clearing_currency in", values, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyNotIn(List<String> values) {
            addCriterion("clearing_currency not in", values, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyBetween(String value1, String value2) {
            addCriterion("clearing_currency between", value1, value2, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingCurrencyNotBetween(String value1, String value2) {
            addCriterion("clearing_currency not between", value1, value2, "clearingCurrency");
            return (Criteria) this;
        }

        public Criteria andClearingTypeIsNull() {
            addCriterion("clearing_type is null");
            return (Criteria) this;
        }

        public Criteria andClearingTypeIsNotNull() {
            addCriterion("clearing_type is not null");
            return (Criteria) this;
        }

        public Criteria andClearingTypeEqualTo(String value) {
            addCriterion("clearing_type =", value, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingTypeNotEqualTo(String value) {
            addCriterion("clearing_type <>", value, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingTypeGreaterThan(String value) {
            addCriterion("clearing_type >", value, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingTypeGreaterThanOrEqualTo(String value) {
            addCriterion("clearing_type >=", value, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingTypeLessThan(String value) {
            addCriterion("clearing_type <", value, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingTypeLessThanOrEqualTo(String value) {
            addCriterion("clearing_type <=", value, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("clearing_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andClearingTypeLike(String value) {
            addCriterion("clearing_type like", value, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeNotLike(String value) {
            addCriterion("clearing_type not like", value, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeIn(List<String> values) {
            addCriterion("clearing_type in", values, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeNotIn(List<String> values) {
            addCriterion("clearing_type not in", values, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeBetween(String value1, String value2) {
            addCriterion("clearing_type between", value1, value2, "clearingType");
            return (Criteria) this;
        }

        public Criteria andClearingTypeNotBetween(String value1, String value2) {
            addCriterion("clearing_type not between", value1, value2, "clearingType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIsNull() {
            addCriterion("payment_type is null");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIsNotNull() {
            addCriterion("payment_type is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeEqualTo(String value) {
            addCriterion("payment_type =", value, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("payment_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNotEqualTo(String value) {
            addCriterion("payment_type <>", value, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("payment_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPaymentTypeGreaterThan(String value) {
            addCriterion("payment_type >", value, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("payment_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPaymentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("payment_type >=", value, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("payment_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPaymentTypeLessThan(String value) {
            addCriterion("payment_type <", value, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("payment_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPaymentTypeLessThanOrEqualTo(String value) {
            addCriterion("payment_type <=", value, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("payment_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPaymentTypeLike(String value) {
            addCriterion("payment_type like", value, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNotLike(String value) {
            addCriterion("payment_type not like", value, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeIn(List<String> values) {
            addCriterion("payment_type in", values, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNotIn(List<String> values) {
            addCriterion("payment_type not in", values, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeBetween(String value1, String value2) {
            addCriterion("payment_type between", value1, value2, "paymentType");
            return (Criteria) this;
        }

        public Criteria andPaymentTypeNotBetween(String value1, String value2) {
            addCriterion("payment_type not between", value1, value2, "paymentType");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(Integer value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("province = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(Integer value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("province <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(Integer value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("province > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(Integer value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("province >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(Integer value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("province < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(Integer value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("province <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<Integer> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<Integer> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(Integer value1, Integer value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(Integer value1, Integer value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(Integer value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("city = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(Integer value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("city <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(Integer value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("city > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(Integer value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("city >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityLessThan(Integer value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("city < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(Integer value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("city <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCityIn(List<Integer> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<Integer> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(Integer value1, Integer value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(Integer value1, Integer value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andDistrictIsNull() {
            addCriterion("district is null");
            return (Criteria) this;
        }

        public Criteria andDistrictIsNotNull() {
            addCriterion("district is not null");
            return (Criteria) this;
        }

        public Criteria andDistrictEqualTo(Integer value) {
            addCriterion("district =", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("district = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDistrictNotEqualTo(Integer value) {
            addCriterion("district <>", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("district <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThan(Integer value) {
            addCriterion("district >", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("district > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThanOrEqualTo(Integer value) {
            addCriterion("district >=", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("district >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDistrictLessThan(Integer value) {
            addCriterion("district <", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("district < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDistrictLessThanOrEqualTo(Integer value) {
            addCriterion("district <=", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("district <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDistrictIn(List<Integer> values) {
            addCriterion("district in", values, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotIn(List<Integer> values) {
            addCriterion("district not in", values, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictBetween(Integer value1, Integer value2) {
            addCriterion("district between", value1, value2, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotBetween(Integer value1, Integer value2) {
            addCriterion("district not between", value1, value2, "district");
            return (Criteria) this;
        }

        public Criteria andLogoIsNull() {
            addCriterion("logo is null");
            return (Criteria) this;
        }

        public Criteria andLogoIsNotNull() {
            addCriterion("logo is not null");
            return (Criteria) this;
        }

        public Criteria andLogoEqualTo(String value) {
            addCriterion("logo =", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("logo = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualTo(String value) {
            addCriterion("logo <>", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("logo <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThan(String value) {
            addCriterion("logo >", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("logo > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualTo(String value) {
            addCriterion("logo >=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("logo >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLogoLessThan(String value) {
            addCriterion("logo <", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("logo < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualTo(String value) {
            addCriterion("logo <=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("logo <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLogoLike(String value) {
            addCriterion("logo like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotLike(String value) {
            addCriterion("logo not like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoIn(List<String> values) {
            addCriterion("logo in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotIn(List<String> values) {
            addCriterion("logo not in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoBetween(String value1, String value2) {
            addCriterion("logo between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotBetween(String value1, String value2) {
            addCriterion("logo not between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Boolean value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`status` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`status` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`status` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`status` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`status` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualToColumn(Supplier.Column column) {
            addCriterion(new StringBuilder("`status` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Boolean> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Boolean> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    /**
     * t_supplier
     */
    public static class Criteria extends GeneratedCriteria {
        private SupplierExample example;

        protected Criteria(SupplierExample example) {
            super();
            this.example = example;
        }

        public SupplierExample example() {
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
     * t_supplier 2019-02-21
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