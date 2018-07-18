package com.kingdee.hrp.sms.common.pojo;

/**
 * 必录性控制
 *
 * @author yadda(silenceisok@163.com)
 * @date 2018/7/17 22:19
 */
public enum MustInputType {

    /*
        1		新增时对于系统管理员必填
        2		编辑时对于系统管理员用户必填
        4		新增时对于供应商用户必填
        8		编辑时对于供应商用户必填
        16		新增时对于医院用户必填
        32		编辑时对于医院用户必填
    */

    ADD_SYSTEM_MUST(1, "新增时对于系统管理员必填"), EDIT_SYSTEM_MUST(2, "编辑时对于系统管理员用户必填"),
    ADD_SUPPLIER_MUST(4, "新增时对于供应商用户必填"), EDIT_SUPPLIER_MUST(8, "编辑时对于供应商用户必填"),
    ADD_HOSPITAL_MUST(16, "新增时对于医院用户必填"), EDIT_HOSPITAL_MUST(32, "编辑时对于医院用户必填");

    private Integer number;
    private String desc;

    public int value() {
        return this.number;
    }

    public String desc() {
        return this.desc;
    }

    MustInputType(Integer number, String desc) {
        this.number = number;
        this.desc = desc;
    }
}
