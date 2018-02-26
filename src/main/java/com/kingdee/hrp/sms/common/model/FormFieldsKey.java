package com.kingdee.hrp.sms.common.model;

import com.kingdee.hrp.sms.common.RootModel;

public class FormFieldsKey extends RootModel {
    private Integer classId;

    private String key;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }
}