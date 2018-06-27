package com.kingdee.hrp.sms.common.enums;

/**
 * 单据状态值
 *
 * @author yadda
 */

public enum BaseStatus {

    NOT_SUPPORT(-1, "不支持的状态"), ADD(0, "新增"), SAVE(1, "保存"), UN_AUDITED(2, "未审核"), AUDITED(3, "已审核"),
    UN_PROCESSED(40, "未处理"), PROCESSED(41, "已处理");

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

    private BaseStatus(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public static BaseStatus getBaseStatus(int number) {

        for (BaseStatus d : BaseStatus.values()) {
            if (d.number == number) {
                return d;
            }
        }
        return BaseStatus.NOT_SUPPORT;
    }

}
