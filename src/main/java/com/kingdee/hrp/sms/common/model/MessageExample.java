package com.kingdee.hrp.sms.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageExample() {
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

    public MessageExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public MessageExample orderBy(String ... orderByClauses) {
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
     * t_message 2018-12-24
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

        public Criteria andIdEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(Message.Column column) {
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

        public Criteria andSenderOrgIsNull() {
            addCriterion("sender_org is null");
            return (Criteria) this;
        }

        public Criteria andSenderOrgIsNotNull() {
            addCriterion("sender_org is not null");
            return (Criteria) this;
        }

        public Criteria andSenderOrgEqualTo(Long value) {
            addCriterion("sender_org =", value, "senderOrg");
            return (Criteria) this;
        }

        public Criteria andSenderOrgEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_org = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderOrgNotEqualTo(Long value) {
            addCriterion("sender_org <>", value, "senderOrg");
            return (Criteria) this;
        }

        public Criteria andSenderOrgNotEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_org <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderOrgGreaterThan(Long value) {
            addCriterion("sender_org >", value, "senderOrg");
            return (Criteria) this;
        }

        public Criteria andSenderOrgGreaterThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_org > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderOrgGreaterThanOrEqualTo(Long value) {
            addCriterion("sender_org >=", value, "senderOrg");
            return (Criteria) this;
        }

        public Criteria andSenderOrgGreaterThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_org >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderOrgLessThan(Long value) {
            addCriterion("sender_org <", value, "senderOrg");
            return (Criteria) this;
        }

        public Criteria andSenderOrgLessThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_org < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderOrgLessThanOrEqualTo(Long value) {
            addCriterion("sender_org <=", value, "senderOrg");
            return (Criteria) this;
        }

        public Criteria andSenderOrgLessThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_org <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderOrgIn(List<Long> values) {
            addCriterion("sender_org in", values, "senderOrg");
            return (Criteria) this;
        }

        public Criteria andSenderOrgNotIn(List<Long> values) {
            addCriterion("sender_org not in", values, "senderOrg");
            return (Criteria) this;
        }

        public Criteria andSenderOrgBetween(Long value1, Long value2) {
            addCriterion("sender_org between", value1, value2, "senderOrg");
            return (Criteria) this;
        }

        public Criteria andSenderOrgNotBetween(Long value1, Long value2) {
            addCriterion("sender_org not between", value1, value2, "senderOrg");
            return (Criteria) this;
        }

        public Criteria andSenderTypeIsNull() {
            addCriterion("sender_type is null");
            return (Criteria) this;
        }

        public Criteria andSenderTypeIsNotNull() {
            addCriterion("sender_type is not null");
            return (Criteria) this;
        }

        public Criteria andSenderTypeEqualTo(Integer value) {
            addCriterion("sender_type =", value, "senderType");
            return (Criteria) this;
        }

        public Criteria andSenderTypeEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderTypeNotEqualTo(Integer value) {
            addCriterion("sender_type <>", value, "senderType");
            return (Criteria) this;
        }

        public Criteria andSenderTypeNotEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderTypeGreaterThan(Integer value) {
            addCriterion("sender_type >", value, "senderType");
            return (Criteria) this;
        }

        public Criteria andSenderTypeGreaterThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("sender_type >=", value, "senderType");
            return (Criteria) this;
        }

        public Criteria andSenderTypeGreaterThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderTypeLessThan(Integer value) {
            addCriterion("sender_type <", value, "senderType");
            return (Criteria) this;
        }

        public Criteria andSenderTypeLessThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderTypeLessThanOrEqualTo(Integer value) {
            addCriterion("sender_type <=", value, "senderType");
            return (Criteria) this;
        }

        public Criteria andSenderTypeLessThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("sender_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSenderTypeIn(List<Integer> values) {
            addCriterion("sender_type in", values, "senderType");
            return (Criteria) this;
        }

        public Criteria andSenderTypeNotIn(List<Integer> values) {
            addCriterion("sender_type not in", values, "senderType");
            return (Criteria) this;
        }

        public Criteria andSenderTypeBetween(Integer value1, Integer value2) {
            addCriterion("sender_type between", value1, value2, "senderType");
            return (Criteria) this;
        }

        public Criteria andSenderTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("sender_type not between", value1, value2, "senderType");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeIsNull() {
            addCriterion("receiver_type is null");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeIsNotNull() {
            addCriterion("receiver_type is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeEqualTo(Integer value) {
            addCriterion("receiver_type =", value, "receiverType");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_type = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverTypeNotEqualTo(Integer value) {
            addCriterion("receiver_type <>", value, "receiverType");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeNotEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_type <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverTypeGreaterThan(Integer value) {
            addCriterion("receiver_type >", value, "receiverType");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeGreaterThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_type > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("receiver_type >=", value, "receiverType");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeGreaterThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_type >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverTypeLessThan(Integer value) {
            addCriterion("receiver_type <", value, "receiverType");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeLessThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_type < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverTypeLessThanOrEqualTo(Integer value) {
            addCriterion("receiver_type <=", value, "receiverType");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeLessThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_type <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverTypeIn(List<Integer> values) {
            addCriterion("receiver_type in", values, "receiverType");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeNotIn(List<Integer> values) {
            addCriterion("receiver_type not in", values, "receiverType");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeBetween(Integer value1, Integer value2) {
            addCriterion("receiver_type between", value1, value2, "receiverType");
            return (Criteria) this;
        }

        public Criteria andReceiverTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("receiver_type not between", value1, value2, "receiverType");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgIsNull() {
            addCriterion("receiver_org is null");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgIsNotNull() {
            addCriterion("receiver_org is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgEqualTo(Long value) {
            addCriterion("receiver_org =", value, "receiverOrg");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_org = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverOrgNotEqualTo(Long value) {
            addCriterion("receiver_org <>", value, "receiverOrg");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgNotEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_org <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverOrgGreaterThan(Long value) {
            addCriterion("receiver_org >", value, "receiverOrg");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgGreaterThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_org > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverOrgGreaterThanOrEqualTo(Long value) {
            addCriterion("receiver_org >=", value, "receiverOrg");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgGreaterThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_org >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverOrgLessThan(Long value) {
            addCriterion("receiver_org <", value, "receiverOrg");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgLessThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_org < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverOrgLessThanOrEqualTo(Long value) {
            addCriterion("receiver_org <=", value, "receiverOrg");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgLessThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("receiver_org <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andReceiverOrgIn(List<Long> values) {
            addCriterion("receiver_org in", values, "receiverOrg");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgNotIn(List<Long> values) {
            addCriterion("receiver_org not in", values, "receiverOrg");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgBetween(Long value1, Long value2) {
            addCriterion("receiver_org between", value1, value2, "receiverOrg");
            return (Criteria) this;
        }

        public Criteria andReceiverOrgNotBetween(Long value1, Long value2) {
            addCriterion("receiver_org not between", value1, value2, "receiverOrg");
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

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`type` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`type` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("`type` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`type` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("`type` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`type` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTopicIsNull() {
            addCriterion("topic is null");
            return (Criteria) this;
        }

        public Criteria andTopicIsNotNull() {
            addCriterion("topic is not null");
            return (Criteria) this;
        }

        public Criteria andTopicEqualTo(String value) {
            addCriterion("topic =", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("topic = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTopicNotEqualTo(String value) {
            addCriterion("topic <>", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicNotEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("topic <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTopicGreaterThan(String value) {
            addCriterion("topic >", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicGreaterThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("topic > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTopicGreaterThanOrEqualTo(String value) {
            addCriterion("topic >=", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicGreaterThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("topic >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTopicLessThan(String value) {
            addCriterion("topic <", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicLessThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("topic < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTopicLessThanOrEqualTo(String value) {
            addCriterion("topic <=", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicLessThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("topic <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTopicLike(String value) {
            addCriterion("topic like", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicNotLike(String value) {
            addCriterion("topic not like", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicIn(List<String> values) {
            addCriterion("topic in", values, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicNotIn(List<String> values) {
            addCriterion("topic not in", values, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicBetween(String value1, String value2) {
            addCriterion("topic between", value1, value2, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicNotBetween(String value1, String value2) {
            addCriterion("topic not between", value1, value2, "topic");
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

        public Criteria andDateEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`date` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterion("`date` <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`date` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterion("`date` >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("`date` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterion("`date` >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`date` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterion("`date` <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("`date` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterion("`date` <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualToColumn(Message.Column column) {
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

        public Criteria andDataIsNull() {
            addCriterion("`data` is null");
            return (Criteria) this;
        }

        public Criteria andDataIsNotNull() {
            addCriterion("`data` is not null");
            return (Criteria) this;
        }

        public Criteria andDataEqualTo(String value) {
            addCriterion("`data` =", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`data` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataNotEqualTo(String value) {
            addCriterion("`data` <>", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`data` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataGreaterThan(String value) {
            addCriterion("`data` >", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataGreaterThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("`data` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataGreaterThanOrEqualTo(String value) {
            addCriterion("`data` >=", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataGreaterThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`data` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataLessThan(String value) {
            addCriterion("`data` <", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataLessThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("`data` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataLessThanOrEqualTo(String value) {
            addCriterion("`data` <=", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataLessThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`data` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDataLike(String value) {
            addCriterion("`data` like", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotLike(String value) {
            addCriterion("`data` not like", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataIn(List<String> values) {
            addCriterion("`data` in", values, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotIn(List<String> values) {
            addCriterion("`data` not in", values, "data");
            return (Criteria) this;
        }

        public Criteria andDataBetween(String value1, String value2) {
            addCriterion("`data` between", value1, value2, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotBetween(String value1, String value2) {
            addCriterion("`data` not between", value1, value2, "data");
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

        public Criteria andStatusEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`status` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`status` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("`status` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualToColumn(Message.Column column) {
            addCriterion(new StringBuilder("`status` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanColumn(Message.Column column) {
            addCriterion(new StringBuilder("`status` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualToColumn(Message.Column column) {
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
    }

    /**
     * t_message
     */
    public static class Criteria extends GeneratedCriteria {
        private MessageExample example;

        protected Criteria(MessageExample example) {
            super();
            this.example = example;
        }

        public MessageExample example() {
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
     * t_message 2018-12-24
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