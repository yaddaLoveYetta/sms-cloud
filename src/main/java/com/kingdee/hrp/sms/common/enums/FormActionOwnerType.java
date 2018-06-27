package com.kingdee.hrp.sms.common.enums;

/**
 * 菜单所属组织类别
 *
 * @author le.xiao
 */
public enum FormActionOwnerType {

    SYSTEM(1, "系统管理员"), HOSPITAL(2, "医院"), SUPPLIER(4, "供应商"),
    SYSTEM_HOSPITAL(3, "系统管理员及医院"), SYSTEM_SUPPLIER(5, "系统管理员及供应商"), HOSPITAL_SUPPLIER(5,
            "医院及供应商"), SYSTEM_HOSPITAL_SUPPLIER(7, "系统管理员及医院及供应商"),
    NOT_SUPPORT(0, "菜单没有归属");

    private int number;
    private String desc;

    FormActionOwnerType(int number, String desc) {
        this.number = number;
        this.desc = desc;
    }

    public static FormActionOwnerType getFormActionOwnerType(int number) {

        for (FormActionOwnerType d : FormActionOwnerType.values()) {
            if (d.number == number) {
                return d;
            }
        }
        return FormActionOwnerType.NOT_SUPPORT;
    }
}
