package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_hospital]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Hospital implements Serializable {
    /**
     * 内码
     */
    private Long id;

    /**
     * 代码
     */
    private String number;

    /**
     * 名称
     */
    private String name;

    /**
     * 医疗机构登记号
     */
    private String registrationNo;

    /**
     * 医院地址
     */
    private String address;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 医疗科目
     */
    private String medicalSubjects;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 发证机关
     */
    private String issueAgency;

    /**
     * 法定代表人
     */
    private String legalPerson;

    /**
     * 省
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 地区
     */
    private Integer district;

    /**
     * 发证日期
     */
    private Date issueDate;

    /**
     * 有效期开始
     */
    private Date validityPeriodBegin;

    /**
     * 有效期结束
     */
    private Date validityPeriodEnd;

    /**
     * 医院logo
     */
    private String logo;

    /**
     * 是否禁用(0可用1禁用，默认0可用)
     */
    private Boolean status;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;

    private static final long serialVersionUID = 1L;

    /**
     * t_hospital
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        number("number", "number", "VARCHAR", true),
        name("name", "name", "VARCHAR", true),
        registrationNo("registration_no", "registrationNo", "VARCHAR", false),
        address("address", "address", "VARCHAR", false),
        introduction("introduction", "introduction", "VARCHAR", false),
        medicalSubjects("medical_subjects", "medicalSubjects", "VARCHAR", false),
        principal("principal", "principal", "VARCHAR", false),
        issueAgency("issue_agency", "issueAgency", "VARCHAR", false),
        legalPerson("legal_person", "legalPerson", "VARCHAR", false),
        province("province", "province", "INTEGER", false),
        city("city", "city", "INTEGER", false),
        district("district", "district", "INTEGER", false),
        issueDate("issue_date", "issueDate", "TIMESTAMP", false),
        validityPeriodBegin("validity_period_begin", "validityPeriodBegin", "TIMESTAMP", false),
        validityPeriodEnd("validity_period_end", "validityPeriodEnd", "TIMESTAMP", false),
        logo("logo", "logo", "VARCHAR", false),
        status("status", "status", "BIT", true),
        contacts("contacts", "contacts", "VARCHAR", false),
        phone("phone", "phone", "VARCHAR", false);

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