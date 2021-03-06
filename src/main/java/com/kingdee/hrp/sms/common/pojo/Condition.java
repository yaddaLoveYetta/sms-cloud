package com.kingdee.hrp.sms.common.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 列表查询where条件对象
 *
 * @author yadda
 */
@Getter
@Setter
@Accessors(chain = true)
public class Condition implements Serializable {

    /**
     * 链接类型 And/Or,默认"AND"
     */
    private LinkType linkType = LinkType.AND;
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
     * 默认=
     */
    private LogicOperator logicOperator = LogicOperator.EQUAL;
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

    /**
     * 条件链接类型
     */
    @ToString
    public enum LinkType implements Serializable {

        AND("AND"), OR("OR"), NULL(""), NOT_SUPPORT("NOT_SUPPORT");

        private String name;

        LinkType(String name) {
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
        private static LinkType getLinkType(String name) {

            for (LinkType linkType : LinkType.values()) {
                if (linkType.name.equalsIgnoreCase(name)) {
                    return linkType;
                }
            }
            return LinkType.NOT_SUPPORT;
        }
    }

    /**
     * 比较符号
     */
    public enum LogicOperator implements Serializable {

        EQUAL("ET", "=", "等于"), NOT_EQUAL("NET", "!=", "不等于"), LESS_THAN("LT", "<", "小于"), LESS_OR_EQUAL("LET", "<=",
                "小于或等于"),
        GREATER("GT", ">", "大于"), GREATER_OR_EQUAL("GET", ">=", "大于或等于"), NULL("BN", "IS NULL", "为空"), NOT_NULL("NBN",
                "IS NOT NULL", "不为空"),
        LIKE("BLT", "LIKE", "LIKE模糊匹配"), IN("IN", "IN", "包含于"),
        NOT_SUPPORT("NOT_SUPPORT", "NOT_SUPPORT", "不支持的比较符号");

        private String name;
        private String operator;
        private String description;

        LogicOperator(String name, String operator, String description) {
            this.name = name;
            this.operator = operator;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * 序列化时 枚举对应生成的值
         *
         * @return
         */
        @JsonValue
        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * 反序列化时的 初始化函数，入参为 对应该枚举的 json值
         *
         * @param name name
         * @return LogicOperator
         */
        @JsonCreator
        private static LogicOperator geLogicOperator(String name) {

            for (LogicOperator logicOperator : LogicOperator.values()) {
                if (logicOperator.name.equalsIgnoreCase(name)) {
                    return logicOperator;
                }
            }
            return LogicOperator.NOT_SUPPORT;
        }
    }
}

