package com.kingdee.hrp.sms.common.pojo;

/**
 * 单据操作模式
 *
 * @author yadda
 */
public enum BillOperateType {

    VIEW(1, "查看"), ADD(2, "新增"), EDIT(3, "编辑");

    private int number;
    private String desc;

    public int value() {
        return this.number;
    }

    BillOperateType(int number, String desc) {
        this.number = number;
        this.desc = desc;
    }
}
