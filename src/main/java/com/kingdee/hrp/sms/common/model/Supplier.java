package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Supplier implements Serializable {
    private Long id;

    private String number;

    private String name;

    private String creditCode;

    private String address;

    private String introduction;

    private String developmentExperience;

    private String legalPerson;

    private String contacts;

    private String phone;

    private String companyType;

    private String industryType;

    private Date validityPeriodBegin;

    private Date validityPeriodEnd;

    private Date issueDate;

    private String issueAgency;

    private String mainBusiness;

    private String accountBank;

    private String accountNumber;

    private String accountAddress;

    private String clearingCompany;

    private String deliveryCompany;

    private String clearingCurrency;

    private String clearingType;

    private String paymentType;

    private Integer province;

    private Integer city;

    private Integer district;

    private String logo;

    private Boolean status;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getDevelopmentExperience() {
        return developmentExperience;
    }

    public void setDevelopmentExperience(String developmentExperience) {
        this.developmentExperience = developmentExperience == null ? null : developmentExperience.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType == null ? null : industryType.trim();
    }

    public Date getValidityPeriodBegin() {
        return validityPeriodBegin;
    }

    public void setValidityPeriodBegin(Date validityPeriodBegin) {
        this.validityPeriodBegin = validityPeriodBegin;
    }

    public Date getValidityPeriodEnd() {
        return validityPeriodEnd;
    }

    public void setValidityPeriodEnd(Date validityPeriodEnd) {
        this.validityPeriodEnd = validityPeriodEnd;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssueAgency() {
        return issueAgency;
    }

    public void setIssueAgency(String issueAgency) {
        this.issueAgency = issueAgency == null ? null : issueAgency.trim();
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness == null ? null : mainBusiness.trim();
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank == null ? null : accountBank.trim();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress == null ? null : accountAddress.trim();
    }

    public String getClearingCompany() {
        return clearingCompany;
    }

    public void setClearingCompany(String clearingCompany) {
        this.clearingCompany = clearingCompany == null ? null : clearingCompany.trim();
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany == null ? null : deliveryCompany.trim();
    }

    public String getClearingCurrency() {
        return clearingCurrency;
    }

    public void setClearingCurrency(String clearingCurrency) {
        this.clearingCurrency = clearingCurrency == null ? null : clearingCurrency.trim();
    }

    public String getClearingType() {
        return clearingType;
    }

    public void setClearingType(String clearingType) {
        this.clearingType = clearingType == null ? null : clearingType.trim();
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.trim();
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", number=").append(number);
        sb.append(", name=").append(name);
        sb.append(", creditCode=").append(creditCode);
        sb.append(", address=").append(address);
        sb.append(", introduction=").append(introduction);
        sb.append(", developmentExperience=").append(developmentExperience);
        sb.append(", legalPerson=").append(legalPerson);
        sb.append(", contacts=").append(contacts);
        sb.append(", phone=").append(phone);
        sb.append(", companyType=").append(companyType);
        sb.append(", industryType=").append(industryType);
        sb.append(", validityPeriodBegin=").append(validityPeriodBegin);
        sb.append(", validityPeriodEnd=").append(validityPeriodEnd);
        sb.append(", issueDate=").append(issueDate);
        sb.append(", issueAgency=").append(issueAgency);
        sb.append(", mainBusiness=").append(mainBusiness);
        sb.append(", accountBank=").append(accountBank);
        sb.append(", accountNumber=").append(accountNumber);
        sb.append(", accountAddress=").append(accountAddress);
        sb.append(", clearingCompany=").append(clearingCompany);
        sb.append(", deliveryCompany=").append(deliveryCompany);
        sb.append(", clearingCurrency=").append(clearingCurrency);
        sb.append(", clearingType=").append(clearingType);
        sb.append(", paymentType=").append(paymentType);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", district=").append(district);
        sb.append(", logo=").append(logo);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table t_supplier
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
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

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}