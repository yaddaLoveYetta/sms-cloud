package com.kingdee.hrp.sms.common.enums;

/**
 * 系统预留权限项掩码
 * 请不要将个性化功能权限项加入
 *
 * @author yadda
 */
public enum AccessMask {

    /* 系统预留权限项
        查看:1;
        新增:2;
        修改:4;
        删除:8;
        审核:16;
        反审核:32;
        禁用:64;
        反禁用:128;
        刷新:256;
        同步:512;
        导入:1024;
        导出:2048;*/

    /**
     * 查看
     */
    VIEW(1, "查看"),
    /**
     * 新增
     */
    ADD(2, "新增"),
    /**
     * 修改
     */
    EDIT(4, "修改"),
    /**
     * 删除
     */
    DEL(8, "删除"),
    /**
     * 审核
     */
    CHECK(16, "审核"),
    /**
     * 反审核
     */
    UN_CHECK(32, "反审核"),
    /**
     * 禁用
     */
    FORBID(64, "禁用"),
    /**
     * 反禁用
     */
    UN_FORBID(128, "反禁用"),
    /**
     * 同步
     */
    SYNC(1, "同步"),
    /**
     * 导入
     */
    IMPORT(1024, "导入"),
    /**
     * 导出
     */
    EXPORT(2048, "导出"),
    /**
     * 其他,不是系统预留权限项，由业务系统自行处理
     */
    OTHER(0, "其他");

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

    private AccessMask(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public static AccessMask getAccessMask(int number) {

        for (AccessMask accessMaskEnum : AccessMask.values()) {
            if (accessMaskEnum.number == number) {
                return accessMaskEnum;
            }
        }
        return AccessMask.OTHER;
    }

    @Override
    public String toString() {
        return "AccessMask{" +
                "number=" + number +
                ", name='" + name + '\'' +
                '}';
    }

}
