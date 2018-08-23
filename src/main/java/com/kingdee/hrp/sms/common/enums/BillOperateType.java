package com.kingdee.hrp.sms.common.enums;

/**
 * 单据操作模式
 *
 * @author yadda
 */
public enum BillOperateType {

    /**
     * 不支持的类别
     */
    NOT_SUPPORT(-1, "不支持的类别"),
    /**
     * 查看
     */
    VIEW(0, "查看"),
    /**
     * 新增
     */
    ADD(1, "新增"),
    /**
     * 编辑
     */
    EDIT(2, "编辑");

    private int number;
    private String desc;

    public int value() {
        return this.number;
    }

    BillOperateType(int number, String desc) {
        this.number = number;
        this.desc = desc;
    }

    public static BillOperateType getBillOperateType(int number) {

        for (BillOperateType billOperateType : BillOperateType.values()) {
            if (billOperateType.number == number) {
                return billOperateType;
            }
        }
        return BillOperateType.NOT_SUPPORT;
    }
}
