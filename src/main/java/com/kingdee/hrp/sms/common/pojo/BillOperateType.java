package com.kingdee.hrp.sms.common.pojo;

/**
 * 单据操作模式
 *
 * @author yadda
 */
public enum BillOperateType {

    VIEW(1, "查看"), EDIT(2, "新增"), ADD(3, "编辑");

    private Integer number;
    private String desc;

    public int value() {
        return this.number;
    }

    BillOperateType(Integer number, String desc) {
        this.number = number;
        this.desc = desc;
    }
}
