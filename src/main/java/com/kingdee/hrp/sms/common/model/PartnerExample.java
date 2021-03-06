package com.kingdee.hrp.sms.common.model;

import java.util.ArrayList;
import java.util.List;

public class PartnerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PartnerExample() {
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

    public PartnerExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public PartnerExample orderBy(String ... orderByClauses) {
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
     * t_partner 2019-02-21
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

        public Criteria andIdEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(Partner.Column column) {
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

        public Criteria andOrgIsNull() {
            addCriterion("org is null");
            return (Criteria) this;
        }

        public Criteria andOrgIsNotNull() {
            addCriterion("org is not null");
            return (Criteria) this;
        }

        public Criteria andOrgEqualTo(Long value) {
            addCriterion("org =", value, "org");
            return (Criteria) this;
        }

        public Criteria andOrgEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("org = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrgNotEqualTo(Long value) {
            addCriterion("org <>", value, "org");
            return (Criteria) this;
        }

        public Criteria andOrgNotEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("org <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrgGreaterThan(Long value) {
            addCriterion("org >", value, "org");
            return (Criteria) this;
        }

        public Criteria andOrgGreaterThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("org > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrgGreaterThanOrEqualTo(Long value) {
            addCriterion("org >=", value, "org");
            return (Criteria) this;
        }

        public Criteria andOrgGreaterThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("org >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrgLessThan(Long value) {
            addCriterion("org <", value, "org");
            return (Criteria) this;
        }

        public Criteria andOrgLessThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("org < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrgLessThanOrEqualTo(Long value) {
            addCriterion("org <=", value, "org");
            return (Criteria) this;
        }

        public Criteria andOrgLessThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("org <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrgIn(List<Long> values) {
            addCriterion("org in", values, "org");
            return (Criteria) this;
        }

        public Criteria andOrgNotIn(List<Long> values) {
            addCriterion("org not in", values, "org");
            return (Criteria) this;
        }

        public Criteria andOrgBetween(Long value1, Long value2) {
            addCriterion("org between", value1, value2, "org");
            return (Criteria) this;
        }

        public Criteria andOrgNotBetween(Long value1, Long value2) {
            addCriterion("org not between", value1, value2, "org");
            return (Criteria) this;
        }

        public Criteria andLinkOrgIsNull() {
            addCriterion("link_org is null");
            return (Criteria) this;
        }

        public Criteria andLinkOrgIsNotNull() {
            addCriterion("link_org is not null");
            return (Criteria) this;
        }

        public Criteria andLinkOrgEqualTo(Long value) {
            addCriterion("link_org =", value, "linkOrg");
            return (Criteria) this;
        }

        public Criteria andLinkOrgEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("link_org = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLinkOrgNotEqualTo(Long value) {
            addCriterion("link_org <>", value, "linkOrg");
            return (Criteria) this;
        }

        public Criteria andLinkOrgNotEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("link_org <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLinkOrgGreaterThan(Long value) {
            addCriterion("link_org >", value, "linkOrg");
            return (Criteria) this;
        }

        public Criteria andLinkOrgGreaterThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("link_org > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLinkOrgGreaterThanOrEqualTo(Long value) {
            addCriterion("link_org >=", value, "linkOrg");
            return (Criteria) this;
        }

        public Criteria andLinkOrgGreaterThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("link_org >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLinkOrgLessThan(Long value) {
            addCriterion("link_org <", value, "linkOrg");
            return (Criteria) this;
        }

        public Criteria andLinkOrgLessThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("link_org < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLinkOrgLessThanOrEqualTo(Long value) {
            addCriterion("link_org <=", value, "linkOrg");
            return (Criteria) this;
        }

        public Criteria andLinkOrgLessThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("link_org <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andLinkOrgIn(List<Long> values) {
            addCriterion("link_org in", values, "linkOrg");
            return (Criteria) this;
        }

        public Criteria andLinkOrgNotIn(List<Long> values) {
            addCriterion("link_org not in", values, "linkOrg");
            return (Criteria) this;
        }

        public Criteria andLinkOrgBetween(Long value1, Long value2) {
            addCriterion("link_org between", value1, value2, "linkOrg");
            return (Criteria) this;
        }

        public Criteria andLinkOrgNotBetween(Long value1, Long value2) {
            addCriterion("link_org not between", value1, value2, "linkOrg");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(Integer value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("check_status = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(Integer value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("check_status <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(Integer value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("check_status > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("check_status >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(Integer value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("check_status < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(Integer value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("check_status <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<Integer> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<Integer> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(Integer value1, Integer value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
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

        public Criteria andStatusEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`status` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`status` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`status` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`status` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`status` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualToColumn(Partner.Column column) {
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

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Boolean value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`type` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Boolean value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`type` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Boolean value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`type` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`type` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Boolean value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`type` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Boolean value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("`type` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Boolean> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Boolean> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Boolean value1, Boolean value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierIsNull() {
            addCriterion("hrp_supplier is null");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierIsNotNull() {
            addCriterion("hrp_supplier is not null");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierEqualTo(Long value) {
            addCriterion("hrp_supplier =", value, "hrpSupplier");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("hrp_supplier = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHrpSupplierNotEqualTo(Long value) {
            addCriterion("hrp_supplier <>", value, "hrpSupplier");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierNotEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("hrp_supplier <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHrpSupplierGreaterThan(Long value) {
            addCriterion("hrp_supplier >", value, "hrpSupplier");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierGreaterThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("hrp_supplier > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHrpSupplierGreaterThanOrEqualTo(Long value) {
            addCriterion("hrp_supplier >=", value, "hrpSupplier");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierGreaterThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("hrp_supplier >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHrpSupplierLessThan(Long value) {
            addCriterion("hrp_supplier <", value, "hrpSupplier");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierLessThanColumn(Partner.Column column) {
            addCriterion(new StringBuilder("hrp_supplier < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHrpSupplierLessThanOrEqualTo(Long value) {
            addCriterion("hrp_supplier <=", value, "hrpSupplier");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierLessThanOrEqualToColumn(Partner.Column column) {
            addCriterion(new StringBuilder("hrp_supplier <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHrpSupplierIn(List<Long> values) {
            addCriterion("hrp_supplier in", values, "hrpSupplier");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierNotIn(List<Long> values) {
            addCriterion("hrp_supplier not in", values, "hrpSupplier");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierBetween(Long value1, Long value2) {
            addCriterion("hrp_supplier between", value1, value2, "hrpSupplier");
            return (Criteria) this;
        }

        public Criteria andHrpSupplierNotBetween(Long value1, Long value2) {
            addCriterion("hrp_supplier not between", value1, value2, "hrpSupplier");
            return (Criteria) this;
        }
    }

    /**
     * t_partner
     */
    public static class Criteria extends GeneratedCriteria {
        private PartnerExample example;

        protected Criteria(PartnerExample example) {
            super();
            this.example = example;
        }

        public PartnerExample example() {
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
     * t_partner 2019-02-21
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