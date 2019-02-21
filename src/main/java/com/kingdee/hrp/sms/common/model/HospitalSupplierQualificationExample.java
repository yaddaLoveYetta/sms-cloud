package com.kingdee.hrp.sms.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HospitalSupplierQualificationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HospitalSupplierQualificationExample() {
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

    public HospitalSupplierQualificationExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public HospitalSupplierQualificationExample orderBy(String ... orderByClauses) {
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
     * t_hospital_supplier_qualification 2019-02-21
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andIdEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
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

        public Criteria andQualificationTypeIsNull() {
            addCriterion("qualification_type is null");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeIsNotNull() {
            addCriterion("qualification_type is not null");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeEqualTo(Long value) {
            addCriterion("qualification_type =", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("qualification_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQualificationTypeNotEqualTo(Long value) {
            addCriterion("qualification_type <>", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeNotEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("qualification_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQualificationTypeGreaterThan(Long value) {
            addCriterion("qualification_type >", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeGreaterThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("qualification_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQualificationTypeGreaterThanOrEqualTo(Long value) {
            addCriterion("qualification_type >=", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeGreaterThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("qualification_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQualificationTypeLessThan(Long value) {
            addCriterion("qualification_type <", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeLessThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("qualification_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQualificationTypeLessThanOrEqualTo(Long value) {
            addCriterion("qualification_type <=", value, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeLessThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("qualification_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQualificationTypeIn(List<Long> values) {
            addCriterion("qualification_type in", values, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeNotIn(List<Long> values) {
            addCriterion("qualification_type not in", values, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeBetween(Long value1, Long value2) {
            addCriterion("qualification_type between", value1, value2, "qualificationType");
            return (Criteria) this;
        }

        public Criteria andQualificationTypeNotBetween(Long value1, Long value2) {
            addCriterion("qualification_type not between", value1, value2, "qualificationType");
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

        public Criteria andNumberEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("`number` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(String value) {
            addCriterion("`number` <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("`number` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(String value) {
            addCriterion("`number` >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("`number` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(String value) {
            addCriterion("`number` >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("`number` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(String value) {
            addCriterion("`number` <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("`number` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(String value) {
            addCriterion("`number` <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
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

        public Criteria andIssueIsNull() {
            addCriterion("issue is null");
            return (Criteria) this;
        }

        public Criteria andIssueIsNotNull() {
            addCriterion("issue is not null");
            return (Criteria) this;
        }

        public Criteria andIssueEqualTo(String value) {
            addCriterion("issue =", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("issue = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueNotEqualTo(String value) {
            addCriterion("issue <>", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueNotEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("issue <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueGreaterThan(String value) {
            addCriterion("issue >", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueGreaterThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("issue > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueGreaterThanOrEqualTo(String value) {
            addCriterion("issue >=", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueGreaterThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("issue >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueLessThan(String value) {
            addCriterion("issue <", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueLessThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("issue < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueLessThanOrEqualTo(String value) {
            addCriterion("issue <=", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueLessThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("issue <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIssueLike(String value) {
            addCriterion("issue like", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueNotLike(String value) {
            addCriterion("issue not like", value, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueIn(List<String> values) {
            addCriterion("issue in", values, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueNotIn(List<String> values) {
            addCriterion("issue not in", values, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueBetween(String value1, String value2) {
            addCriterion("issue between", value1, value2, "issue");
            return (Criteria) this;
        }

        public Criteria andIssueNotBetween(String value1, String value2) {
            addCriterion("issue not between", value1, value2, "issue");
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
            addCriterionForJDBCDate("validity_period_begin =", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_begin = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginNotEqualTo(Date value) {
            addCriterionForJDBCDate("validity_period_begin <>", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginNotEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_begin <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginGreaterThan(Date value) {
            addCriterionForJDBCDate("validity_period_begin >", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginGreaterThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_begin > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("validity_period_begin >=", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginGreaterThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_begin >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginLessThan(Date value) {
            addCriterionForJDBCDate("validity_period_begin <", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginLessThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_begin < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("validity_period_begin <=", value, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginLessThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_begin <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginIn(List<Date> values) {
            addCriterionForJDBCDate("validity_period_begin in", values, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginNotIn(List<Date> values) {
            addCriterionForJDBCDate("validity_period_begin not in", values, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("validity_period_begin between", value1, value2, "validityPeriodBegin");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodBeginNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("validity_period_begin not between", value1, value2, "validityPeriodBegin");
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
            addCriterionForJDBCDate("validity_period_end =", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_end = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndNotEqualTo(Date value) {
            addCriterionForJDBCDate("validity_period_end <>", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndNotEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_end <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndGreaterThan(Date value) {
            addCriterionForJDBCDate("validity_period_end >", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndGreaterThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_end > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("validity_period_end >=", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndGreaterThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_end >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndLessThan(Date value) {
            addCriterionForJDBCDate("validity_period_end <", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndLessThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_end < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("validity_period_end <=", value, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndLessThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("validity_period_end <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndIn(List<Date> values) {
            addCriterionForJDBCDate("validity_period_end in", values, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndNotIn(List<Date> values) {
            addCriterionForJDBCDate("validity_period_end not in", values, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("validity_period_end between", value1, value2, "validityPeriodEnd");
            return (Criteria) this;
        }

        public Criteria andValidityPeriodEndNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("validity_period_end not between", value1, value2, "validityPeriodEnd");
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

        public Criteria andHospitalEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("hospital = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalNotEqualTo(Long value) {
            addCriterion("hospital <>", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalNotEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("hospital <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThan(Long value) {
            addCriterion("hospital >", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("hospital > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanOrEqualTo(Long value) {
            addCriterion("hospital >=", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalGreaterThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("hospital >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalLessThan(Long value) {
            addCriterion("hospital <", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("hospital < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanOrEqualTo(Long value) {
            addCriterion("hospital <=", value, "hospital");
            return (Criteria) this;
        }

        public Criteria andHospitalLessThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
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

        public Criteria andSupplierEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("supplier = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualTo(Long value) {
            addCriterion("supplier <>", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("supplier <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThan(Long value) {
            addCriterion("supplier >", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("supplier > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualTo(Long value) {
            addCriterion("supplier >=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("supplier >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierLessThan(Long value) {
            addCriterion("supplier <", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("supplier < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualTo(Long value) {
            addCriterion("supplier <=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
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

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("remark = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("remark <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("remark > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("remark >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("remark < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualToColumn(HospitalSupplierQualification.Column column) {
            addCriterion(new StringBuilder("remark <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    /**
     * t_hospital_supplier_qualification
     */
    public static class Criteria extends GeneratedCriteria {
        private HospitalSupplierQualificationExample example;

        protected Criteria(HospitalSupplierQualificationExample example) {
            super();
            this.example = example;
        }

        public HospitalSupplierQualificationExample example() {
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
     * t_hospital_supplier_qualification 2019-02-21
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