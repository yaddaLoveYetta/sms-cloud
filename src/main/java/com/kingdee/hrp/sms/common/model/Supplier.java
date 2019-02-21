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
 * 由数据库表[t_supplier]生成
 * @author yadda
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class Supplier implements Serializable {
    /**
     * 内码
     */
    private Long id;

    /**
     * 代码
     */
    private String number;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 企业统一信用代码
     */
    private String creditCode;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 
     */
    private String introduction;

    /**
     * 
     */
    private String developmentExperience;

    /**
     * 
     */
    private String legalPerson;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 
     */
    private String companyType;

    /**
     * 
     */
    private String industryType;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validityPeriodBegin;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validityPeriodEnd;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date issueDate;

    /**
     * 
     */
    private String issueAgency;

    /**
     * 
     */
    private String mainBusiness;

    /**
     * 
     */
    private String accountBank;

    /**
     * 
     */
    private String accountNumber;

    /**
     * 
     */
    private String accountAddress;

    /**
     * 
     */
    private String clearingCompany;

    /**
     * 
     */
    private String deliveryCompany;

    /**
     * 
     */
    private String clearingCurrency;

    /**
     * 
     */
    private String clearingType;

    /**
     * 
     */
    private String paymentType;

    /**
     * 省
     */
    private Integer province;

    /**
     * 市
     */
    private Integer city;

    /**
     * 区
     */
    private Integer district;

    /**
     * 供应商logo
     */
    private String logo;

    /**
     * 是否禁用(0可用1禁用，默认0可用)
     */
    private Boolean status;

    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;

    /**
     * 数据库表[t_supplier]列对应的枚举
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        number("number", "number", "VARCHAR", true),
        name("name", "name", "VARCHAR", true),
        creditCode("credit_code", "creditCode", "VARCHAR", false),
        address("address", "address", "VARCHAR", false),
        introduction("introduction", "introduction", "VARCHAR", false),
        developmentExperience("development_experience", "developmentExperience", "VARCHAR", false),
        legalPerson("legal_person", "legalPerson", "VARCHAR", false),
        contacts("contacts", "contacts", "VARCHAR", false),
        phone("phone", "phone", "VARCHAR", false),
        companyType("company_type", "companyType", "VARCHAR", false),
        industryType("industry_type", "industryType", "VARCHAR", false),
        validityPeriodBegin("validity_period_begin", "validityPeriodBegin", "TIMESTAMP", false),
        validityPeriodEnd("validity_period_end", "validityPeriodEnd", "TIMESTAMP", false),
        issueDate("issue_date", "issueDate", "TIMESTAMP", false),
        issueAgency("issue_agency", "issueAgency", "VARCHAR", false),
        mainBusiness("main_business", "mainBusiness", "VARCHAR", false),
        accountBank("account_bank", "accountBank", "VARCHAR", false),
        accountNumber("account_number", "accountNumber", "VARCHAR", false),
        accountAddress("account_address", "accountAddress", "VARCHAR", false),
        clearingCompany("clearing_company", "clearingCompany", "VARCHAR", false),
        deliveryCompany("delivery_company", "deliveryCompany", "VARCHAR", false),
        clearingCurrency("clearing_currency", "clearingCurrency", "VARCHAR", false),
        clearingType("clearing_type", "clearingType", "VARCHAR", false),
        paymentType("payment_type", "paymentType", "VARCHAR", false),
        province("province", "province", "INTEGER", false),
        city("city", "city", "INTEGER", false),
        district("district", "district", "INTEGER", false),
        logo("logo", "logo", "VARCHAR", false),
        status("status", "status", "BIT", true);

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