package com.kingdee.hrp.sms.common.model;

import com.kingdee.hrp.sms.common.RootModel;

public class Role extends RootModel {
    private Long id;

    private String number;

    private String name;

    private Integer type;

    private Boolean userDefine;

    private Long org;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getUserDefine() {
        return userDefine;
    }

    public void setUserDefine(Boolean userDefine) {
        this.userDefine = userDefine;
    }

    public Long getOrg() {
        return org;
    }

    public void setOrg(Long org) {
        this.org = org;
    }
}