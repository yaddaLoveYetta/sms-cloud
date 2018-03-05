package com.kingdee.hrp.sms.common.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * 列表查询where条件对象
 */
public class Condition implements Serializable {


    /**
     * 链接类型 And/Or,默认"AND"
     */
    private LinkTypeEnum linkType = LinkTypeEnum.AND;
    /**
     * 左括号-可能有多个，如 "(("，甚至"((("等复杂查询,默认"("
     */
    private String leftParenTheses = "(";
    /**
     * 比较字段在模板描述中对应的key
     */
    private String fieldKey;
    /**
     * 比较符号
     */
    private LogicOperatorEnum logicOperator;
    /**
     * 比较值
     */
    private Object value;
    /**
     * 右括号-可能有多个，如 "))"，甚至")))"等复杂查询,默认")"
     */
    private String rightParenTheses = ")";
    /**
     * 是否需要转换条件字段，用于传入引用他表字段时过滤，
     * 例如传入引用基础资料key是否需要转换为名称条件，用户输入查询时通常需要转换成名称查询，而代码中调用通常不需要转换，直接用id匹配
     * 默认true
     */
    private Boolean needConvert = true;

    public LinkTypeEnum getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkTypeEnum linkType) {
        this.linkType = linkType;
    }

    public String getLeftParenTheses() {
        return leftParenTheses;
    }

    public void setLeftParenTheses(String leftParenTheses) {
        this.leftParenTheses = leftParenTheses;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public LogicOperatorEnum getLogicOperator() {
        return logicOperator;
    }

    public void setLogicOperator(LogicOperatorEnum logicOperator) {
        this.logicOperator = logicOperator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getRightParenTheses() {
        return rightParenTheses;
    }

    public void setRightParenTheses(String rightParenTheses) {
        this.rightParenTheses = rightParenTheses;
    }

    public Boolean getNeedConvert() {
        return needConvert;
    }

    public void setNeedConvert(Boolean needConvert) {
        this.needConvert = needConvert;
    }


    /**
     * 条件链接类型
     */
    public enum LinkTypeEnum implements Serializable {

        AND("AND"), OR("OR"), NOT_SUPPORT("NOT_SUPPORT");

        private String name;

        LinkTypeEnum(String name) {
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JsonCreator
        private static LinkTypeEnum getDirectionEnum(String name) {

            for (LinkTypeEnum linkTypeEnum : LinkTypeEnum.values()) {
                if (linkTypeEnum.name.equalsIgnoreCase(name)) {
                    return linkTypeEnum;
                }
            }
            return LinkTypeEnum.NOT_SUPPORT;
        }
    }

    /**
     * 比较符号
     */
    public enum LogicOperatorEnum implements Serializable {

        EQUAL("ET", "等于"), NOT_EQUAL("NET", "不等于"), LESS_THAN("LT", "小于"), LESS_OR_EQUAL("LET", "小于或等于"),
        GREATER("GT", "大于"), GREATER_OR_EQUAL("GET", "大于或等于"),
        LIKE("BLT", "LIKE模糊匹配"), IN("IN", "包含于"),
        NOT_SUPPORT("NOT_SUPPORT", "不支持的比较符号");

        private String name;
        private String description;


        LogicOperatorEnum(String name, String description) {
            this.name = name;
            this.description = description;
        }

        @JsonValue //序列化时 枚举对应生成的值
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @JsonCreator //反序列化时的 初始化函数，入参为 对应该枚举的 json值
        private static LogicOperatorEnum getLogicOperatorEnum(String name) {

            for (LogicOperatorEnum logicOperatorEnum : LogicOperatorEnum.values()) {
                if (logicOperatorEnum.name.equalsIgnoreCase(name)) {
                    return logicOperatorEnum;
                }
            }
            return LogicOperatorEnum.NOT_SUPPORT;
        }
    }
}

