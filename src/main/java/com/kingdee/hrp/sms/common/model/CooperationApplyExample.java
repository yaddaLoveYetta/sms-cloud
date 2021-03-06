package com.kingdee.hrp.sms.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CooperationApplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CooperationApplyExample() {
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

    public CooperationApplyExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public CooperationApplyExample orderBy(String ... orderByClauses) {
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
     * t_cooperation_apply 2019-02-21
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

        public Criteria andIdEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(CooperationApply.Column column) {
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

        public Criteria andSupplierEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("supplier = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualTo(Long value) {
            addCriterion("supplier <>", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("supplier <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThan(Long value) {
            addCriterion("supplier >", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("supplier > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualTo(Long value) {
            addCriterion("supplier >=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("supplier >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierLessThan(Long value) {
            addCriterion("supplier <", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("supplier < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualTo(Long value) {
            addCriterion("supplier <=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualToColumn(CooperationApply.Column column) {
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

        public Criteria andHospitalEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("hospital = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalNotEqualTo(Long value) {
            addCriterion("hospital <>", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalNotEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("hospital <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThan(Long value) {
            addCriterion("hospital >", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("hospital > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanOrEqualTo(Long value) {
            addCriterion("hospital >=", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanOrEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("hospital >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalLessThan(Long value) {
            addCriterion("hospital <", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("hospital < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanOrEqualTo(Long value) {
            addCriterion("hospital <=", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanOrEqualToColumn(CooperationApply.Column column) {
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

        public Criteria andDateIsNull() {
            addCriterion("`date` is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("`date` is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Date value) {
            addCriterion("`date` =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`date` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterion("`date` <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`date` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterion("`date` >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`date` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterion("`date` >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`date` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterion("`date` <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`date` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterion("`date` <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`date` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Date> values) {
            addCriterion("`date` in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Date> values) {
            addCriterion("`date` not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Date value1, Date value2) {
            addCriterion("`date` between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Date value1, Date value2) {
            addCriterion("`date` not between", value1, value2, "date");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`status` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`status` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`status` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`status` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`status` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("`status` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andProcessDateIsNull() {
            addCriterion("process_date is null");
            return (Criteria) this;
        }

        public Criteria andProcessDateIsNotNull() {
            addCriterion("process_date is not null");
            return (Criteria) this;
        }

        public Criteria andProcessDateEqualTo(Date value) {
            addCriterion("process_date =", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("process_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProcessDateNotEqualTo(Date value) {
            addCriterion("process_date <>", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("process_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProcessDateGreaterThan(Date value) {
            addCriterion("process_date >", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateGreaterThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("process_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProcessDateGreaterThanOrEqualTo(Date value) {
            addCriterion("process_date >=", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateGreaterThanOrEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("process_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProcessDateLessThan(Date value) {
            addCriterion("process_date <", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateLessThanColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("process_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProcessDateLessThanOrEqualTo(Date value) {
            addCriterion("process_date <=", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateLessThanOrEqualToColumn(CooperationApply.Column column) {
            addCriterion(new StringBuilder("process_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andProcessDateIn(List<Date> values) {
            addCriterion("process_date in", values, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotIn(List<Date> values) {
            addCriterion("process_date not in", values, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateBetween(Date value1, Date value2) {
            addCriterion("process_date between", value1, value2, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotBetween(Date value1, Date value2) {
            addCriterion("process_date not between", value1, value2, "processDate");
            return (Criteria) this;
        }
    }

    /**
     * t_cooperation_apply
     */
    public static class Criteria extends GeneratedCriteria {
        private CooperationApplyExample example;

        protected Criteria(CooperationApplyExample example) {
            super();
            this.example = example;
        }

        public CooperationApplyExample example() {
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
     * t_cooperation_apply 2019-02-21
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