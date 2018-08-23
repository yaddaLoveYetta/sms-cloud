package com.kingdee.hrp.sms.common.enums;

/**
 * 功能所属组织类别
 * <p>
 * 采用三位二进制设计，000，第一位标示系统管理员所属(001即1),第二位标示医院所属(010即2),第三位标示供应商所属(100即4)
 * 衍生出各组所属组合，如系统管理员及医院所属(001+010=011即3)
 *
 * @author yadda
 */
public enum FormActionOwnerType {

    /**
     * 只有系统管理员可以使用的功能
     */
    SYSTEM(1, "系统管理员"),
    /**
     * 只有医院可以使用的功能
     */
    HOSPITAL(2, "医院"),
    /**
     * 只有供应商可以使用的功能
     */
    SUPPLIER(4, "供应商"),
    /**
     * 只有系统管理员或医院可以使用的功能
     */
    SYSTEM_HOSPITAL(3, "系统管理员及医院"),
    /**
     * 只有系统管理员或供应商可以使用的功能
     */
    SYSTEM_SUPPLIER(5, "系统管理员及供应商"),
    /**
     * 只有医院或供应商可以使用的功能
     */
    HOSPITAL_SUPPLIER(6, "医院及供应商"),
    /**
     * 系统管理员或医院或供应商可以使用的功能
     */
    SYSTEM_HOSPITAL_SUPPLIER(7, "系统管理员及医院及供应商"),
    /**
     * 没有归属的功能，系统管理员，医院，供应商都不可以使用
     */
    NOT_SUPPORT(0, "功能没有归属");

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
