package com.kingdee.hrp.sms.common.pojo;

/**
 * @author yadda(silenceisok@163.com)
 * @date 2018/7/17 22:19
 */
public enum DisplayType {

/*    20	1		查看时对于平台用户显示
20	2		新增时对于平台用户显示
20	4		编辑时对于平台用户显示
20	8		查看时对于供应商用户显示
20	16		新增时对于供应商用户显示
20	32		编辑时对于供应商用户显示
20	64		查看时对于医院用户显示
20	128		新增时对于医院用户显示
20	256		编辑时对于医院用户显示*/

    VIEW_SYSTEM_SHOW(1, "查看时对于平台用户显示"), ADD_SYSTEM_SHOW(2, "新增时对于平台用户显示"), EDIT_SYSTEM_SHOW(4, "编辑时对于平台用户显示"),
    VIEW_SUPPLIER_SHOW(8, "查看时对于供应商用户显示"), ADD_SUPPLIER_SHOW(16, "新增时对于供应商用户显示"), EDIT_SUPPLIER_SHOW(32, "编辑时对于供应商用户显示"),
    VIEW_HOSPITAL_SHOW(64, "查看时对于医院用户显示"), ADD_HOSPITAL_SHOW(128, "新增时对于医院用户显示"), EDIT_HOSPITAL_SHOW(256, "编辑时对于医院用户显示");

    private Integer number;
    private String desc;

    public int value() {
        return this.number;
    }

    DisplayType(Integer number, String desc) {
        this.number = number;
        this.desc = desc;
    }
}
