package com.kingdee.hrp.sms.common.pojo;

/**
 * 用户角色类型枚举
 *
 * @author yadda
 */
public enum UserRoleTypeEnum {

    /**
     * 不支持的用户类别
     */
    NOT_SUPPORT(0, "不支持的类别"),
    /**
     * 系统管理员角色类型
     */
    SYSTEM(1, "系统管理员"),
    /**
     * 医院角色类型
     */
    HOSPITAL(2, "医院"),
    /**
     * 供应商角色类型
     */
    SUPPLIER(3, "供应商");

    private Integer number;
    private String name;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    UserRoleTypeEnum(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public static UserRoleTypeEnum getUserRoleTypeEnum(int number) {

        for (UserRoleTypeEnum userRoleTypeEnum : UserRoleTypeEnum.values()) {
            if (userRoleTypeEnum.number == number) {
                return userRoleTypeEnum;
            }
        }
        return UserRoleTypeEnum.NOT_SUPPORT;
    }

}
