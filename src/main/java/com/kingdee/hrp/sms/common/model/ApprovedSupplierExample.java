package com.kingdee.hrp.sms.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApprovedSupplierExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ApprovedSupplierExample() {
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

    public ApprovedSupplierExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public ApprovedSupplierExample orderBy(String ... orderByClauses) {
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
     * t_approved_supplier 2019-02-21
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

        public Criteria andIdEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(ApprovedSupplier.Column column) {
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

        public Criteria andHospitalIsNull() {
            addCriterion("hospital is null");
            return (Criteria) this;
        }

        public Criteria andHospitalIsNotNull() {
            addCriterion("hospital is not null");
            return (Criteria) this;
        }

        public Criteria andHospitalEqualTo(Long value) {
            addCriterion("hospital =", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("hospital = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalNotEqualTo(Long value) {
            addCriterion("hospital <>", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalNotEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("hospital <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThan(Long value) {
            addCriterion("hospital >", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("hospital > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanOrEqualTo(Long value) {
            addCriterion("hospital >=", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("hospital >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalLessThan(Long value) {
            addCriterion("hospital <", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("hospital < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanOrEqualTo(Long value) {
            addCriterion("hospital <=", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("hospital <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalIn(List<Long> values) {
            addCriterion("hospital in", values, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalNotIn(List<Long> values) {
            addCriterion("hospital not in", values, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalBetween(Long value1, Long value2) {
            addCriterion("hospital between", value1, value2, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalNotBetween(Long value1, Long value2) {
            addCriterion("hospital not between", value1, value2, "hospital");
            return (Criteria) this;
        }

        public Criteria andSupplierIsNull() {
            addCriterion("supplier is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIsNotNull() {
            addCriterion("supplier is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierEqualTo(Long value) {
            addCriterion("supplier =", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("supplier = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualTo(Long value) {
            addCriterion("supplier <>", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("supplier <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThan(Long value) {
            addCriterion("supplier >", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("supplier > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualTo(Long value) {
            addCriterion("supplier >=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("supplier >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierLessThan(Long value) {
            addCriterion("supplier <", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("supplier < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualTo(Long value) {
            addCriterion("supplier <=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("supplier <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierIn(List<Long> values) {
            addCriterion("supplier in", values, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotIn(List<Long> values) {
            addCriterion("supplier not in", values, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierBetween(Long value1, Long value2) {
            addCriterion("supplier between", value1, value2, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotBetween(Long value1, Long value2) {
            addCriterion("supplier not between", value1, value2, "supplier");
            return (Criteria) this;
        }

        public Criteria andItemIsNull() {
            addCriterion("item is null");
            return (Criteria) this;
        }

        public Criteria andItemIsNotNull() {
            addCriterion("item is not null");
            return (Criteria) this;
        }

        public Criteria andItemEqualTo(Long value) {
            addCriterion("item =", value, "item");
            return (Criteria) this;
        }

        public Criteria andItemEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("item = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andItemNotEqualTo(Long value) {
            addCriterion("item <>", value, "item");
            return (Criteria) this;
        }

        public Criteria andItemNotEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("item <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andItemGreaterThan(Long value) {
            addCriterion("item >", value, "item");
            return (Criteria) this;
        }

        public Criteria andItemGreaterThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("item > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andItemGreaterThanOrEqualTo(Long value) {
            addCriterion("item >=", value, "item");
            return (Criteria) this;
        }

        public Criteria andItemGreaterThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("item >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andItemLessThan(Long value) {
            addCriterion("item <", value, "item");
            return (Criteria) this;
        }

        public Criteria andItemLessThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("item < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andItemLessThanOrEqualTo(Long value) {
            addCriterion("item <=", value, "item");
            return (Criteria) this;
        }

        public Criteria andItemLessThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("item <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andItemIn(List<Long> values) {
            addCriterion("item in", values, "item");
            return (Criteria) this;
        }

        public Criteria andItemNotIn(List<Long> values) {
            addCriterion("item not in", values, "item");
            return (Criteria) this;
        }

        public Criteria andItemBetween(Long value1, Long value2) {
            addCriterion("item between", value1, value2, "item");
            return (Criteria) this;
        }

        public Criteria andItemNotBetween(Long value1, Long value2) {
            addCriterion("item not between", value1, value2, "item");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("price = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("price <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("price > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("price >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("price < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("price <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andEffectualDateIsNull() {
            addCriterion("effectual_date is null");
            return (Criteria) this;
        }

        public Criteria andEffectualDateIsNotNull() {
            addCriterion("effectual_date is not null");
            return (Criteria) this;
        }

        public Criteria andEffectualDateEqualTo(Date value) {
            addCriterion("effectual_date =", value, "effectualDate");
            return (Criteria) this;
        }

        public Criteria andEffectualDateEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("effectual_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEffectualDateNotEqualTo(Date value) {
            addCriterion("effectual_date <>", value, "effectualDate");
            return (Criteria) this;
        }

        public Criteria andEffectualDateNotEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("effectual_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEffectualDateGreaterThan(Date value) {
            addCriterion("effectual_date >", value, "effectualDate");
            return (Criteria) this;
        }

        public Criteria andEffectualDateGreaterThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("effectual_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEffectualDateGreaterThanOrEqualTo(Date value) {
            addCriterion("effectual_date >=", value, "effectualDate");
            return (Criteria) this;
        }

        public Criteria andEffectualDateGreaterThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("effectual_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEffectualDateLessThan(Date value) {
            addCriterion("effectual_date <", value, "effectualDate");
            return (Criteria) this;
        }

        public Criteria andEffectualDateLessThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("effectual_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEffectualDateLessThanOrEqualTo(Date value) {
            addCriterion("effectual_date <=", value, "effectualDate");
            return (Criteria) this;
        }

        public Criteria andEffectualDateLessThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("effectual_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEffectualDateIn(List<Date> values) {
            addCriterion("effectual_date in", values, "effectualDate");
            return (Criteria) this;
        }

        public Criteria andEffectualDateNotIn(List<Date> values) {
            addCriterion("effectual_date not in", values, "effectualDate");
            return (Criteria) this;
        }

        public Criteria andEffectualDateBetween(Date value1, Date value2) {
            addCriterion("effectual_date between", value1, value2, "effectualDate");
            return (Criteria) this;
        }

        public Criteria andEffectualDateNotBetween(Date value1, Date value2) {
            addCriterion("effectual_date not between", value1, value2, "effectualDate");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateIsNull() {
            addCriterion("uneffectual_date is null");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateIsNotNull() {
            addCriterion("uneffectual_date is not null");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateEqualTo(Date value) {
            addCriterion("uneffectual_date =", value, "uneffectualDate");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("uneffectual_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUneffectualDateNotEqualTo(Date value) {
            addCriterion("uneffectual_date <>", value, "uneffectualDate");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateNotEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("uneffectual_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUneffectualDateGreaterThan(Date value) {
            addCriterion("uneffectual_date >", value, "uneffectualDate");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateGreaterThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("uneffectual_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUneffectualDateGreaterThanOrEqualTo(Date value) {
            addCriterion("uneffectual_date >=", value, "uneffectualDate");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateGreaterThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("uneffectual_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUneffectualDateLessThan(Date value) {
            addCriterion("uneffectual_date <", value, "uneffectualDate");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateLessThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("uneffectual_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUneffectualDateLessThanOrEqualTo(Date value) {
            addCriterion("uneffectual_date <=", value, "uneffectualDate");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateLessThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("uneffectual_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUneffectualDateIn(List<Date> values) {
            addCriterion("uneffectual_date in", values, "uneffectualDate");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateNotIn(List<Date> values) {
            addCriterion("uneffectual_date not in", values, "uneffectualDate");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateBetween(Date value1, Date value2) {
            addCriterion("uneffectual_date between", value1, value2, "uneffectualDate");
            return (Criteria) this;
        }

        public Criteria andUneffectualDateNotBetween(Date value1, Date value2) {
            addCriterion("uneffectual_date not between", value1, value2, "uneffectualDate");
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

        public Criteria andStatusEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("`status` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("`status` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("`status` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualToColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("`status` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanColumn(ApprovedSupplier.Column column) {
            addCriterion(new StringBuilder("`status` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualToColumn(ApprovedSupplier.Column column) {
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
     * t_approved_supplier
     */
    public static class Criteria extends GeneratedCriteria {
        private ApprovedSupplierExample example;

        protected Criteria(ApprovedSupplierExample example) {
            super();
            this.example = example;
        }

        public ApprovedSupplierExample example() {
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
     * t_approved_supplier 2019-02-21
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