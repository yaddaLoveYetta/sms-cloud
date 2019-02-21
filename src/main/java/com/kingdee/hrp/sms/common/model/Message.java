package com.kingdee.hrp.sms.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 由数据库表[t_message]生成
 * @author yadda
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class Message implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 消息发送者归属组织（发送者所在医院/供应商公司）
     */
    private Long senderOrg;

    /**
     * 消息发送者归属组织类别(2医院/3供应商/1系统)
     */
    private Integer senderType;

    /**
     * 消息接收者归属组织类别(2医院/3供应商/1系统)
     */
    private Integer receiverType;

    /**
     * 消息接收者归属组织（接收人所在医院/供应商公司）
     */
    private Long receiverOrg;

    /**
     * 消息类别(枚举)
     */
    private Integer type;

    /**
     * 消息主题(需要展示的内容)
     */
    private String topic;

    /**
     * 接收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    /**
     * 消息扩展内容，用来辅助业务处理
     */
    private String data;

    /**
     * 记录状态(已处理，未处理等关联classId=1080)
     */
    private Integer status;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_message]列对应的枚举
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        senderOrg("sender_org", "senderOrg", "BIGINT", false),
        senderType("sender_type", "senderType", "INTEGER", false),
        receiverType("receiver_type", "receiverType", "INTEGER", false),
        receiverOrg("receiver_org", "receiverOrg", "BIGINT", false),
        type("type", "type", "INTEGER", true),
        topic("topic", "topic", "VARCHAR", false),
        date("date", "date", "TIMESTAMP", true),
        data("data", "data", "VARCHAR", true),
        status("status", "status", "INTEGER", true);

        private static final String BEGINNING_DELIMITER = "`";

        private static final String ENDING_DELIMITER = "`";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}