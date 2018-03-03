package com.kingdee.hrp.sms.common.model;

import com.kingdee.hrp.sms.common.RootModel;

public class FormClassEntryKey extends RootModel {
    private Integer classId;

    private Integer entryIndex;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getEntryIndex() {
        return entryIndex;
    }

    public void setEntryIndex(Integer entryIndex) {
        this.entryIndex = entryIndex;
    }
}