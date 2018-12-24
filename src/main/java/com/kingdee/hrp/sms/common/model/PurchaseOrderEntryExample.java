package com.kingdee.hrp.sms.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PurchaseOrderEntryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PurchaseOrderEntryExample() {
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

    public PurchaseOrderEntryExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public PurchaseOrderEntryExample orderBy(String ... orderByClauses) {
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
     * t_purchase_order_entry 2018-12-24
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

        public Criteria andIdEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
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

        public Criteria andParentIsNull() {
            addCriterion("parent is null");
            return (Criteria) this;
        }

        public Criteria andParentIsNotNull() {
            addCriterion("parent is not null");
            return (Criteria) this;
        }

        public Criteria andParentEqualTo(Long value) {
            addCriterion("parent =", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("parent = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParentNotEqualTo(Long value) {
            addCriterion("parent <>", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("parent <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParentGreaterThan(Long value) {
            addCriterion("parent >", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("parent > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParentGreaterThanOrEqualTo(Long value) {
            addCriterion("parent >=", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("parent >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParentLessThan(Long value) {
            addCriterion("parent <", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("parent < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParentLessThanOrEqualTo(Long value) {
            addCriterion("parent <=", value, "parent");
            return (Criteria) this;
        }

        public Criteria andParentLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("parent <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParentIn(List<Long> values) {
            addCriterion("parent in", values, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotIn(List<Long> values) {
            addCriterion("parent not in", values, "parent");
            return (Criteria) this;
        }

        public Criteria andParentBetween(Long value1, Long value2) {
            addCriterion("parent between", value1, value2, "parent");
            return (Criteria) this;
        }

        public Criteria andParentNotBetween(Long value1, Long value2) {
            addCriterion("parent not between", value1, value2, "parent");
            return (Criteria) this;
        }

        public Criteria andSequenceIsNull() {
            addCriterion("`sequence` is null");
            return (Criteria) this;
        }

        public Criteria andSequenceIsNotNull() {
            addCriterion("`sequence` is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceEqualTo(Integer value) {
            addCriterion("`sequence` =", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("`sequence` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSequenceNotEqualTo(Integer value) {
            addCriterion("`sequence` <>", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("`sequence` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThan(Integer value) {
            addCriterion("`sequence` >", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("`sequence` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThanOrEqualTo(Integer value) {
            addCriterion("`sequence` >=", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("`sequence` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSequenceLessThan(Integer value) {
            addCriterion("`sequence` <", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("`sequence` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSequenceLessThanOrEqualTo(Integer value) {
            addCriterion("`sequence` <=", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("`sequence` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSequenceIn(List<Integer> values) {
            addCriterion("`sequence` in", values, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotIn(List<Integer> values) {
            addCriterion("`sequence` not in", values, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceBetween(Integer value1, Integer value2) {
            addCriterion("`sequence` between", value1, value2, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotBetween(Integer value1, Integer value2) {
            addCriterion("`sequence` not between", value1, value2, "sequence");
            return (Criteria) this;
        }

        public Criteria andMaterialIsNull() {
            addCriterion("material is null");
            return (Criteria) this;
        }

        public Criteria andMaterialIsNotNull() {
            addCriterion("material is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialEqualTo(Long value) {
            addCriterion("material =", value, "material");
            return (Criteria) this;
        }

        public Criteria andMaterialEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("material = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaterialNotEqualTo(Long value) {
            addCriterion("material <>", value, "material");
            return (Criteria) this;
        }

        public Criteria andMaterialNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("material <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaterialGreaterThan(Long value) {
            addCriterion("material >", value, "material");
            return (Criteria) this;
        }

        public Criteria andMaterialGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("material > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaterialGreaterThanOrEqualTo(Long value) {
            addCriterion("material >=", value, "material");
            return (Criteria) this;
        }

        public Criteria andMaterialGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("material >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaterialLessThan(Long value) {
            addCriterion("material <", value, "material");
            return (Criteria) this;
        }

        public Criteria andMaterialLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("material < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaterialLessThanOrEqualTo(Long value) {
            addCriterion("material <=", value, "material");
            return (Criteria) this;
        }

        public Criteria andMaterialLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("material <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andMaterialIn(List<Long> values) {
            addCriterion("material in", values, "material");
            return (Criteria) this;
        }

        public Criteria andMaterialNotIn(List<Long> values) {
            addCriterion("material not in", values, "material");
            return (Criteria) this;
        }

        public Criteria andMaterialBetween(Long value1, Long value2) {
            addCriterion("material between", value1, value2, "material");
            return (Criteria) this;
        }

        public Criteria andMaterialNotBetween(Long value1, Long value2) {
            addCriterion("material not between", value1, value2, "material");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(Long value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("unit = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(Long value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("unit <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(Long value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("unit > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(Long value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("unit >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(Long value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("unit < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(Long value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("unit <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<Long> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<Long> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(Long value1, Long value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(Long value1, Long value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andQtyIsNull() {
            addCriterion("qty is null");
            return (Criteria) this;
        }

        public Criteria andQtyIsNotNull() {
            addCriterion("qty is not null");
            return (Criteria) this;
        }

        public Criteria andQtyEqualTo(Float value) {
            addCriterion("qty =", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("qty = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQtyNotEqualTo(Float value) {
            addCriterion("qty <>", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("qty <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThan(Float value) {
            addCriterion("qty >", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("qty > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThanOrEqualTo(Float value) {
            addCriterion("qty >=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("qty >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQtyLessThan(Float value) {
            addCriterion("qty <", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("qty < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQtyLessThanOrEqualTo(Float value) {
            addCriterion("qty <=", value, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("qty <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQtyIn(List<Float> values) {
            addCriterion("qty in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotIn(List<Float> values) {
            addCriterion("qty not in", values, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyBetween(Float value1, Float value2) {
            addCriterion("qty between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andQtyNotBetween(Float value1, Float value2) {
            addCriterion("qty not between", value1, value2, "qty");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyIsNull() {
            addCriterion("confirm_qty is null");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyIsNotNull() {
            addCriterion("confirm_qty is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyEqualTo(Float value) {
            addCriterion("confirm_qty =", value, "confirmQty");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_qty = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmQtyNotEqualTo(Float value) {
            addCriterion("confirm_qty <>", value, "confirmQty");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_qty <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmQtyGreaterThan(Float value) {
            addCriterion("confirm_qty >", value, "confirmQty");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_qty > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmQtyGreaterThanOrEqualTo(Float value) {
            addCriterion("confirm_qty >=", value, "confirmQty");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_qty >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmQtyLessThan(Float value) {
            addCriterion("confirm_qty <", value, "confirmQty");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_qty < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmQtyLessThanOrEqualTo(Float value) {
            addCriterion("confirm_qty <=", value, "confirmQty");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_qty <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmQtyIn(List<Float> values) {
            addCriterion("confirm_qty in", values, "confirmQty");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyNotIn(List<Float> values) {
            addCriterion("confirm_qty not in", values, "confirmQty");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyBetween(Float value1, Float value2) {
            addCriterion("confirm_qty between", value1, value2, "confirmQty");
            return (Criteria) this;
        }

        public Criteria andConfirmQtyNotBetween(Float value1, Float value2) {
            addCriterion("confirm_qty not between", value1, value2, "confirmQty");
            return (Criteria) this;
        }

        public Criteria andConfirmDateIsNull() {
            addCriterion("confirm_date is null");
            return (Criteria) this;
        }

        public Criteria andConfirmDateIsNotNull() {
            addCriterion("confirm_date is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmDateEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_date =", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_date <>", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDateGreaterThan(Date value) {
            addCriterionForJDBCDate("confirm_date >", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_date >=", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDateLessThan(Date value) {
            addCriterionForJDBCDate("confirm_date <", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_date <=", value, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDateIn(List<Date> values) {
            addCriterionForJDBCDate("confirm_date in", values, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("confirm_date not in", values, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("confirm_date between", value1, value2, "confirmDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("confirm_date not between", value1, value2, "confirmDate");
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

        public Criteria andPriceEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("price = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("price <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("price > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("price >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("price < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
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

        public Criteria andAmountEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("amount = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("amount <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("amount > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("amount >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("amount < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
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

        public Criteria andDeliveryDateIsNull() {
            addCriterion("delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIsNotNull() {
            addCriterion("delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date =", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("delivery_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date <>", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("delivery_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThan(Date value) {
            addCriterionForJDBCDate("delivery_date >", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("delivery_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date >=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("delivery_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThan(Date value) {
            addCriterionForJDBCDate("delivery_date <", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("delivery_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date <=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("delivery_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIn(List<Date> values) {
            addCriterionForJDBCDate("delivery_date in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("delivery_date not in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("delivery_date between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("delivery_date not between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateIsNull() {
            addCriterion("confirm_delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateIsNotNull() {
            addCriterion("confirm_delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_delivery_date =", value, "confirmDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_delivery_date = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_delivery_date <>", value, "confirmDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_delivery_date <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateGreaterThan(Date value) {
            addCriterionForJDBCDate("confirm_delivery_date >", value, "confirmDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_delivery_date > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_delivery_date >=", value, "confirmDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_delivery_date >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateLessThan(Date value) {
            addCriterionForJDBCDate("confirm_delivery_date <", value, "confirmDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_delivery_date < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("confirm_delivery_date <=", value, "confirmDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("confirm_delivery_date <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateIn(List<Date> values) {
            addCriterionForJDBCDate("confirm_delivery_date in", values, "confirmDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("confirm_delivery_date not in", values, "confirmDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("confirm_delivery_date between", value1, value2, "confirmDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andConfirmDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("confirm_delivery_date not between", value1, value2, "confirmDeliveryDate");
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

        public Criteria andDeliverStatusEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_status = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusNotEqualTo(Integer value) {
            addCriterion("deliver_status <>", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_status <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusGreaterThan(Integer value) {
            addCriterion("deliver_status >", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_status > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("deliver_status >=", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_status >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusLessThan(Integer value) {
            addCriterion("deliver_status <", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_status < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverStatusLessThanOrEqualTo(Integer value) {
            addCriterion("deliver_status <=", value, "deliverStatus");
            return (Criteria) this;
        }

        public Criteria andDeliverStatusLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
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

        public Criteria andDeliverQtyIsNull() {
            addCriterion("deliver_qty is null");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyIsNotNull() {
            addCriterion("deliver_qty is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyEqualTo(Float value) {
            addCriterion("deliver_qty =", value, "deliverQty");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_qty = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverQtyNotEqualTo(Float value) {
            addCriterion("deliver_qty <>", value, "deliverQty");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_qty <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverQtyGreaterThan(Float value) {
            addCriterion("deliver_qty >", value, "deliverQty");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_qty > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverQtyGreaterThanOrEqualTo(Float value) {
            addCriterion("deliver_qty >=", value, "deliverQty");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_qty >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverQtyLessThan(Float value) {
            addCriterion("deliver_qty <", value, "deliverQty");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_qty < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverQtyLessThanOrEqualTo(Float value) {
            addCriterion("deliver_qty <=", value, "deliverQty");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("deliver_qty <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDeliverQtyIn(List<Float> values) {
            addCriterion("deliver_qty in", values, "deliverQty");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyNotIn(List<Float> values) {
            addCriterion("deliver_qty not in", values, "deliverQty");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyBetween(Float value1, Float value2) {
            addCriterion("deliver_qty between", value1, value2, "deliverQty");
            return (Criteria) this;
        }

        public Criteria andDeliverQtyNotBetween(Float value1, Float value2) {
            addCriterion("deliver_qty not between", value1, value2, "deliverQty");
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

        public Criteria andReceiveStatusEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_status = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusNotEqualTo(Integer value) {
            addCriterion("receive_status <>", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_status <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusGreaterThan(Integer value) {
            addCriterion("receive_status >", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_status > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("receive_status >=", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_status >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusLessThan(Integer value) {
            addCriterion("receive_status <", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_status < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveStatusLessThanOrEqualTo(Integer value) {
            addCriterion("receive_status <=", value, "receiveStatus");
            return (Criteria) this;
        }

        public Criteria andReceiveStatusLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
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

        public Criteria andReceiveQtyIsNull() {
            addCriterion("receive_qty is null");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyIsNotNull() {
            addCriterion("receive_qty is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyEqualTo(Float value) {
            addCriterion("receive_qty =", value, "receiveQty");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_qty = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveQtyNotEqualTo(Float value) {
            addCriterion("receive_qty <>", value, "receiveQty");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_qty <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveQtyGreaterThan(Float value) {
            addCriterion("receive_qty >", value, "receiveQty");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_qty > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveQtyGreaterThanOrEqualTo(Float value) {
            addCriterion("receive_qty >=", value, "receiveQty");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_qty >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveQtyLessThan(Float value) {
            addCriterion("receive_qty <", value, "receiveQty");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_qty < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveQtyLessThanOrEqualTo(Float value) {
            addCriterion("receive_qty <=", value, "receiveQty");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("receive_qty <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiveQtyIn(List<Float> values) {
            addCriterion("receive_qty in", values, "receiveQty");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyNotIn(List<Float> values) {
            addCriterion("receive_qty not in", values, "receiveQty");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyBetween(Float value1, Float value2) {
            addCriterion("receive_qty between", value1, value2, "receiveQty");
            return (Criteria) this;
        }

        public Criteria andReceiveQtyNotBetween(Float value1, Float value2) {
            addCriterion("receive_qty not between", value1, value2, "receiveQty");
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

        public Criteria andReturnStatusEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_status = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusNotEqualTo(Integer value) {
            addCriterion("return_status <>", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_status <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusGreaterThan(Integer value) {
            addCriterion("return_status >", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_status > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_status >=", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_status >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusLessThan(Integer value) {
            addCriterion("return_status <", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_status < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnStatusLessThanOrEqualTo(Integer value) {
            addCriterion("return_status <=", value, "returnStatus");
            return (Criteria) this;
        }

        public Criteria andReturnStatusLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
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

        public Criteria andReturnQtyIsNull() {
            addCriterion("return_qty is null");
            return (Criteria) this;
        }

        public Criteria andReturnQtyIsNotNull() {
            addCriterion("return_qty is not null");
            return (Criteria) this;
        }

        public Criteria andReturnQtyEqualTo(Float value) {
            addCriterion("return_qty =", value, "returnQty");
            return (Criteria) this;
        }

        public Criteria andReturnQtyEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_qty = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnQtyNotEqualTo(Float value) {
            addCriterion("return_qty <>", value, "returnQty");
            return (Criteria) this;
        }

        public Criteria andReturnQtyNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_qty <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnQtyGreaterThan(Float value) {
            addCriterion("return_qty >", value, "returnQty");
            return (Criteria) this;
        }

        public Criteria andReturnQtyGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_qty > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnQtyGreaterThanOrEqualTo(Float value) {
            addCriterion("return_qty >=", value, "returnQty");
            return (Criteria) this;
        }

        public Criteria andReturnQtyGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_qty >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnQtyLessThan(Float value) {
            addCriterion("return_qty <", value, "returnQty");
            return (Criteria) this;
        }

        public Criteria andReturnQtyLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_qty < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnQtyLessThanOrEqualTo(Float value) {
            addCriterion("return_qty <=", value, "returnQty");
            return (Criteria) this;
        }

        public Criteria andReturnQtyLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("return_qty <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReturnQtyIn(List<Float> values) {
            addCriterion("return_qty in", values, "returnQty");
            return (Criteria) this;
        }

        public Criteria andReturnQtyNotIn(List<Float> values) {
            addCriterion("return_qty not in", values, "returnQty");
            return (Criteria) this;
        }

        public Criteria andReturnQtyBetween(Float value1, Float value2) {
            addCriterion("return_qty between", value1, value2, "returnQty");
            return (Criteria) this;
        }

        public Criteria andReturnQtyNotBetween(Float value1, Float value2) {
            addCriterion("return_qty not between", value1, value2, "returnQty");
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

        public Criteria andRemarkEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("remark = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("remark <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("remark > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("remark >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanColumn(PurchaseOrderEntry.Column column) {
            addCriterion(new StringBuilder("remark < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualToColumn(PurchaseOrderEntry.Column column) {
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
     * t_purchase_order_entry
     */
    public static class Criteria extends GeneratedCriteria {
        private PurchaseOrderEntryExample example;

        protected Criteria(PurchaseOrderEntryExample example) {
            super();
            this.example = example;
        }

        public PurchaseOrderEntryExample example() {
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
     * t_purchase_order_entry 2018-12-24
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