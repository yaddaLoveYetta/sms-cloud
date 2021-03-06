package com.kingdee.hrp.sms.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PurchaseOrderExample() {
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

    public PurchaseOrderExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public PurchaseOrderExample orderBy(String ... orderByClauses) {
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
     * t_purchase_order 2019-02-21
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

        public Criteria andIdEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(PurchaseOrder.Column column) {
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

        public Criteria andHospitalEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("hospital = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalNotEqualTo(Long value) {
            addCriterion("hospital <>", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("hospital <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThan(Long value) {
            addCriterion("hospital >", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("hospital > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanOrEqualTo(Long value) {
            addCriterion("hospital >=", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("hospital >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalLessThan(Long value) {
            addCriterion("hospital <", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("hospital < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanOrEqualTo(Long value) {
            addCriterion("hospital <=", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanOrEqualToColumn(PurchaseOrder.Column column) {
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

        public Criteria andNumberEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("`number` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(String value) {
            addCriterion("`number` <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("`number` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(String value) {
            addCriterion("`number` >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("`number` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(String value) {
            addCriterion("`number` >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("`number` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(String value) {
            addCriterion("`number` <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("`number` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(String value) {
            addCriterion("`number` <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualToColumn(PurchaseOrder.Column column) {
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

        public Criteria andOriginNumberIsNull() {
            addCriterion("origin_number is null");
            return (Criteria) this;
        }

        public Criteria andOriginNumberIsNotNull() {
            addCriterion("origin_number is not null");
            return (Criteria) this;
        }

        public Criteria andOriginNumberEqualTo(String value) {
            addCriterion("origin_number =", value, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("origin_number = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOriginNumberNotEqualTo(String value) {
            addCriterion("origin_number <>", value, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("origin_number <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOriginNumberGreaterThan(String value) {
            addCriterion("origin_number >", value, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("origin_number > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOriginNumberGreaterThanOrEqualTo(String value) {
            addCriterion("origin_number >=", value, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("origin_number >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOriginNumberLessThan(String value) {
            addCriterion("origin_number <", value, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("origin_number < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOriginNumberLessThanOrEqualTo(String value) {
            addCriterion("origin_number <=", value, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("origin_number <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOriginNumberLike(String value) {
            addCriterion("origin_number like", value, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberNotLike(String value) {
            addCriterion("origin_number not like", value, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberIn(List<String> values) {
            addCriterion("origin_number in", values, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberNotIn(List<String> values) {
            addCriterion("origin_number not in", values, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberBetween(String value1, String value2) {
            addCriterion("origin_number between", value1, value2, "originNumber");
            return (Criteria) this;
        }

        public Criteria andOriginNumberNotBetween(String value1, String value2) {
            addCriterion("origin_number not between", value1, value2, "originNumber");
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

        public Criteria andSupplierEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("supplier = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualTo(Long value) {
            addCriterion("supplier <>", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("supplier <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThan(Long value) {
            addCriterion("supplier >", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("supplier > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualTo(Long value) {
            addCriterion("supplier >=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("supplier >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierLessThan(Long value) {
            addCriterion("supplier <", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("supplier < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualTo(Long value) {
            addCriterion("supplier <=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualToColumn(PurchaseOrder.Column column) {
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

        public Criteria andCreaterIsNull() {
            addCriterion("creater is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNotNull() {
            addCriterion("creater is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterEqualTo(Long value) {
            addCriterion("creater =", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("creater = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreaterNotEqualTo(Long value) {
            addCriterion("creater <>", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("creater <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThan(Long value) {
            addCriterion("creater >", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("creater > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThanOrEqualTo(Long value) {
            addCriterion("creater >=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("creater >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreaterLessThan(Long value) {
            addCriterion("creater <", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("creater < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreaterLessThanOrEqualTo(Long value) {
            addCriterion("creater <=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("creater <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreaterIn(List<Long> values) {
            addCriterion("creater in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotIn(List<Long> values) {
            addCriterion("creater not in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterBetween(Long value1, Long value2) {
            addCriterion("creater between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotBetween(Long value1, Long value2) {
            addCriterion("creater not between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("create_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("create_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("create_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("create_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("create_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("create_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNull() {
            addCriterion("checker is null");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNotNull() {
            addCriterion("checker is not null");
            return (Criteria) this;
        }

        public Criteria andCheckerEqualTo(Long value) {
            addCriterion("checker =", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("checker = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckerNotEqualTo(Long value) {
            addCriterion("checker <>", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("checker <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThan(Long value) {
            addCriterion("checker >", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("checker > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThanOrEqualTo(Long value) {
            addCriterion("checker >=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("checker >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckerLessThan(Long value) {
            addCriterion("checker <", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("checker < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckerLessThanOrEqualTo(Long value) {
            addCriterion("checker <=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("checker <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckerIn(List<Long> values) {
            addCriterion("checker in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotIn(List<Long> values) {
            addCriterion("checker not in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerBetween(Long value1, Long value2) {
            addCriterion("checker between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotBetween(Long value1, Long value2) {
            addCriterion("checker not between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNull() {
            addCriterion("check_date is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNotNull() {
            addCriterion("check_date is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEqualTo(Date value) {
            addCriterion("check_date =", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("check_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckDateNotEqualTo(Date value) {
            addCriterion("check_date <>", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("check_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThan(Date value) {
            addCriterion("check_date >", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("check_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanOrEqualTo(Date value) {
            addCriterion("check_date >=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("check_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThan(Date value) {
            addCriterion("check_date <", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("check_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanOrEqualTo(Date value) {
            addCriterion("check_date <=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("check_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckDateIn(List<Date> values) {
            addCriterion("check_date in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotIn(List<Date> values) {
            addCriterion("check_date not in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateBetween(Date value1, Date value2) {
            addCriterion("check_date between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotBetween(Date value1, Date value2) {
            addCriterion("check_date not between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNull() {
            addCriterion("order_status is null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNotNull() {
            addCriterion("order_status is not null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusEqualTo(Integer value) {
            addCriterion("order_status =", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("order_status = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotEqualTo(Integer value) {
            addCriterion("order_status <>", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("order_status <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThan(Integer value) {
            addCriterion("order_status >", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("order_status > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_status >=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("order_status >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThan(Integer value) {
            addCriterion("order_status <", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("order_status < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("order_status <=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("order_status <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderStatusIn(List<Integer> values) {
            addCriterion("order_status in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotIn(List<Integer> values) {
            addCriterion("order_status not in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusBetween(Integer value1, Integer value2) {
            addCriterion("order_status between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("order_status not between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusIsNull() {
            addCriterion("deliver_status is null");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusIsNotNull() {
            addCriterion("deliver_status is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusEqualTo(Integer value) {
            addCriterion("deliver_status =", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("deliver_status = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusNotEqualTo(Integer value) {
            addCriterion("deliver_status <>", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("deliver_status <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusGreaterThan(Integer value) {
            addCriterion("deliver_status >", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("deliver_status > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("deliver_status >=", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("deliver_status >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusLessThan(Integer value) {
            addCriterion("deliver_status <", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("deliver_status < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusLessThanOrEqualTo(Integer value) {
            addCriterion("deliver_status <=", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("deliver_status <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusIn(List<Integer> values) {
            addCriterion("deliver_status in", values, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusNotIn(List<Integer> values) {
            addCriterion("deliver_status not in", values, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusBetween(Integer value1, Integer value2) {
            addCriterion("deliver_status between", value1, value2, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("deliver_status not between", value1, value2, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusIsNull() {
            addCriterion("receive_status is null");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusIsNotNull() {
            addCriterion("receive_status is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusEqualTo(Integer value) {
            addCriterion("receive_status =", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("receive_status = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusNotEqualTo(Integer value) {
            addCriterion("receive_status <>", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("receive_status <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusGreaterThan(Integer value) {
            addCriterion("receive_status >", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("receive_status > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("receive_status >=", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("receive_status >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusLessThan(Integer value) {
            addCriterion("receive_status <", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("receive_status < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusLessThanOrEqualTo(Integer value) {
            addCriterion("receive_status <=", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("receive_status <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusIn(List<Integer> values) {
            addCriterion("receive_status in", values, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusNotIn(List<Integer> values) {
            addCriterion("receive_status not in", values, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusBetween(Integer value1, Integer value2) {
            addCriterion("receive_status between", value1, value2, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("receive_status not between", value1, value2, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusIsNull() {
            addCriterion("return_status is null");
            return (Criteria) this;
        }

        public Criteria andReturnStatusIsNotNull() {
            addCriterion("return_status is not null");
            return (Criteria) this;
        }

        public Criteria andReturnStatusEqualTo(Integer value) {
            addCriterion("return_status =", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("return_status = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusNotEqualTo(Integer value) {
            addCriterion("return_status <>", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("return_status <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusGreaterThan(Integer value) {
            addCriterion("return_status >", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("return_status > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_status >=", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("return_status >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusLessThan(Integer value) {
            addCriterion("return_status <", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("return_status < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusLessThanOrEqualTo(Integer value) {
            addCriterion("return_status <=", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("return_status <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusIn(List<Integer> values) {
            addCriterion("return_status in", values, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusNotIn(List<Integer> values) {
            addCriterion("return_status not in", values, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusBetween(Integer value1, Integer value2) {
            addCriterion("return_status between", value1, value2, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("return_status not between", value1, value2, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("amount = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("amount <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("amount > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("amount >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("amount < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("amount <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeIsNull() {
            addCriterion("purchase_type is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeIsNotNull() {
            addCriterion("purchase_type is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeEqualTo(Integer value) {
            addCriterion("purchase_type =", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchase_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeNotEqualTo(Integer value) {
            addCriterion("purchase_type <>", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchase_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeGreaterThan(Integer value) {
            addCriterion("purchase_type >", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchase_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("purchase_type >=", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchase_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeLessThan(Integer value) {
            addCriterion("purchase_type <", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchase_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeLessThanOrEqualTo(Integer value) {
            addCriterion("purchase_type <=", value, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchase_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeIn(List<Integer> values) {
            addCriterion("purchase_type in", values, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeNotIn(List<Integer> values) {
            addCriterion("purchase_type not in", values, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeBetween(Integer value1, Integer value2) {
            addCriterion("purchase_type between", value1, value2, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaseTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("purchase_type not between", value1, value2, "purchaseType");
            return (Criteria) this;
        }

        public Criteria andPurchaserIsNull() {
            addCriterion("purchaser is null");
            return (Criteria) this;
        }

        public Criteria andPurchaserIsNotNull() {
            addCriterion("purchaser is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaserEqualTo(Long value) {
            addCriterion("purchaser =", value, "purchaser");
            return (Criteria) this;
        }

        public Criteria andPurchaserEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchaser = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaserNotEqualTo(Long value) {
            addCriterion("purchaser <>", value, "purchaser");
            return (Criteria) this;
        }

        public Criteria andPurchaserNotEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchaser <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaserGreaterThan(Long value) {
            addCriterion("purchaser >", value, "purchaser");
            return (Criteria) this;
        }

        public Criteria andPurchaserGreaterThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchaser > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaserGreaterThanOrEqualTo(Long value) {
            addCriterion("purchaser >=", value, "purchaser");
            return (Criteria) this;
        }

        public Criteria andPurchaserGreaterThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchaser >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaserLessThan(Long value) {
            addCriterion("purchaser <", value, "purchaser");
            return (Criteria) this;
        }

        public Criteria andPurchaserLessThanColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchaser < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaserLessThanOrEqualTo(Long value) {
            addCriterion("purchaser <=", value, "purchaser");
            return (Criteria) this;
        }

        public Criteria andPurchaserLessThanOrEqualToColumn(PurchaseOrder.Column column) {
            addCriterion(new StringBuilder("purchaser <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPurchaserIn(List<Long> values) {
            addCriterion("purchaser in", values, "purchaser");
            return (Criteria) this;
        }

        public Criteria andPurchaserNotIn(List<Long> values) {
            addCriterion("purchaser not in", values, "purchaser");
            return (Criteria) this;
        }

        public Criteria andPurchaserBetween(Long value1, Long value2) {
            addCriterion("purchaser between", value1, value2, "purchaser");
            return (Criteria) this;
        }

        public Criteria andPurchaserNotBetween(Long value1, Long value2) {
            addCriterion("purchaser not between", value1, value2, "purchaser");
            return (Criteria) this;
        }
    }

    /**
     * t_purchase_order
     */
    public static class Criteria extends GeneratedCriteria {
        private PurchaseOrderExample example;

        protected Criteria(PurchaseOrderExample example) {
            super();
            this.example = example;
        }

        public PurchaseOrderExample example() {
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
     * t_purchase_order 2019-02-21
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