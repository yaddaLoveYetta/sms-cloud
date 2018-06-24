package com.kingdee.hrp.sms.common.pojo;

/**
 * 系统预留权限项掩码
 * 请不要将个性化功能权限项加入
 *
 * @author yadda
 */
public enum AccessMaskEnum {

    // 系统预留权限项
    /*    查看:1;
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
    VIEW(1, "查看"), ADD(2, "新增"), EDIT(4, "修改"), DEL(8, "删除"), CHECK(16, "审核"), UNCHECK(32, "反审核"),
    FORBID(64, "禁用"), UNFORBID(128, "反禁用"), SYNC(1, "同步"), IMPORT(1024, "导入"), EXPORT(2048, "导出"), OTHER(0, "其他");

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

    private AccessMaskEnum(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public static AccessMaskEnum getAccessMaskEnum(int number) {

        for (AccessMaskEnum accessMaskEnum : AccessMaskEnum.values()) {
            if (accessMaskEnum.number == number) {
                return accessMaskEnum;
            }
        }
        return AccessMaskEnum.OTHER;
    }

    @Override
    public String toString() {
        return "AccessMaskEnum{" +
                "number=" + number +
                ", name='" + name + '\'' +
                '}';
    }

}
