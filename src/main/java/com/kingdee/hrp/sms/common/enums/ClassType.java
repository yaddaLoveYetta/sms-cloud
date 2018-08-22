package com.kingdee.hrp.sms.common.enums;

/**
 * 业务类型枚举
 *
 * @author le.xiao
 */
public enum ClassType {

    USER(1001, "用户"), ROLE(1002, "角色"), ROLE_TYPE(1003, "角色类别"),
    SUPPLIER_TYPE(1004, "供应商类别"), HOSPITAL_SUPPLIER(1005, "医院供应商"),
    ITEM(1006, "物料"), APPROVED_SUPPLIER(1007, "中标库"),
    PARTNER_SUPPLIER(1008, "合作医院"), PARTNER_HOSPITAL(1009, "合作供应商"),
    UNIT(1010, "单位"), EMP(1011, "职员"), HOSPITAL(1012, "医院资料"),
    SUPPLIER(1013, "供应商资料"), MATERIAL_DOCUMENT_TYPE(1014, "物料证件类别"),
    MATERIAL_DOCUMENT(1015, "物料证件"), SUPPLIER_MATERIAL(1016, "供应商物料"),
    ASSISTANCE(1080, "系统常量"), PURCHASE_ORDER(2001, "采购订单"),
    COOPERATION_APPLY(3001, "申请记录"), MESSAGE(4001, "消息通知");
    /**
     * 业务类别id
     */
    private int classId;
    /**
     * 业务类别名称
     */
    private String className;

    ClassType(Integer classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    public int classId() {
        return classId;
    }

    private static ClassType getClassType(int classId) {

        for (ClassType classType : ClassType.values()) {
            if (classType.classId == classId) {
                return classType;
            }
        }
        return null;
    }
}
