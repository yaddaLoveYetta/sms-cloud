package com.kingdee.hrp.sms.common.model;

public class AccessControl extends AccessControlKey {
    private Integer accessMask;

    public Integer getAccessMask() {
        return accessMask;
    }

    public void setAccessMask(Integer accessMask) {
        this.accessMask = accessMask;
    }
}