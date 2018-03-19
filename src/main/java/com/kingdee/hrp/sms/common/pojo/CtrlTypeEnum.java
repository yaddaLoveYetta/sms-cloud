package com.kingdee.hrp.sms.common.pojo;

/**
 * @author : yadda(silenceisok@163.com)
 * @date : 2018/3/19 11:25
 */
public enum CtrlTypeEnum {

        /*
        1	整数
        2	数字带两位小数
        3	选择框(值也是数字)
        5	下拉列表
        6	F7选择框
        7	级联选择器
        8	手机号码
        9	座机电话
        10	普通文本
        11	多行文本
        12	日期时间
        13	男：女
        14	密码控件
        15	是:否
        16	单价/金额(两位小数)  */

    INTEGER(1, "整数"), DECIMAL(2, "数字带两位小数"), CHECKBOX(3, "选择框"), SELECT(5, "下拉列表"),
    F7(6, "F7选择框"), CASCADE(7, "级联选择器"), MOBILE(8, "手机号码"), PHONE(9, "座机电话"),
    TEXT(10, "普通文本"), TEXTAREA(11, "多行文本"), DATETIME(12, "日期时间"), SEX(13, "男：女"),
    PASSWORD(14, "密码控件"), WHETHER(15, "是:否"), MONEY(16, "单价/金额(两位小数)");

    private int number;
    private String name;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private CtrlTypeEnum(int number, String name) {
        this.number = number;
        this.name = name;
    }

    /**
     * 获取字段数据类型，默认文本
     *
     * @param number 类型代码
     * @return CtrlTypeEnum
     */
    public static CtrlTypeEnum getTypeEnum(int number) {

        for (CtrlTypeEnum d : CtrlTypeEnum.values()) {
            if (d.number == number) {
                return d;
            }
        }
        return CtrlTypeEnum.TEXT;
    }
}
