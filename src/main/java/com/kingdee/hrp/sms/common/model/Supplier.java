package com.kingdee.hrp.sms.common.model;

import com.kingdee.hrp.sms.common.RootModel;
import java.util.Date;

public class Supplier extends RootModel {
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

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table t_supplier
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id"),
        number("number"),
        name("name"),
        creditCode("credit_code"),
        address("address"),
        introduction("introduction"),
        developmentExperience("development_experience"),
        legalPerson("legal_person"),
        contacts("contacts"),
        phone("phone"),
        companyType("company_type"),
        industryType("industry_type"),
        validityPeriodBegin("validity_period_begin"),
        validityPeriodEnd("validity_period_end"),
        issueDate("issue_date"),
        issueAgency("issue_agency"),
        mainBusiness("main_business"),
        accountBank("account_bank"),
        accountNumber("account_number"),
        accountAddress("account_address"),
        clearingCompany("clearing_company"),
        deliveryCompany("delivery_company"),
        clearingCurrency("clearing_currency"),
        clearingType("clearing_type"),
        paymentType("payment_type"),
        province("province"),
        city("city"),
        district("district"),
        logo("logo"),
        status("status");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

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
        Column(String column) {
            this.column = column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.column + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table t_supplier
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.column + " ASC";
        }
    }
}