package com.kingdee.hrp.sms.basedata.model;

/**
 * @author yadda<silenceisok@163.com>
 * @since 2018/1/24
 */
public enum UserEnum {

    SUPPLIER(1, "供应商用户"), HOSPTOR(2, "医院用户"),

    GUEST(3, "普通用户");

    private Integer id;
    private String name;

    UserEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


}
