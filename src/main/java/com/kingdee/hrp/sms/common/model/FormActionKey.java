package com.kingdee.hrp.sms.common.model;

import com.kingdee.hrp.sms.common.RootModel;

public class FormActionKey extends RootModel {
    private Integer classId;

    private Integer accessMask;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getAccessMask() {
        return accessMask;
    }

    public void setAccessMask(Integer accessMask) {
        this.accessMask = accessMask;
    }
}