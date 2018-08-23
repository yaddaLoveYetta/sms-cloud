package com.kingdee.hrp.sms.common.enums;

/**
 * 业务类型枚举
 *
 * @author le.xiao
 */
public enum ClassType {

    /**
     * 用户
     */
    USER(1001, "用户"),
    /**
     * 角色
     */
    ROLE(1002, "角色"),
    /**
     * 角色类别
     */
    ROLE_TYPE(1003, "角色类别"),
    /**
     * 医院维护的供应商类别
     */
    SUPPLIER_TYPE(1004, "供应商类别"),
    /**
     * 医院维护的自己的(HRP)供应商
     */
    HOSPITAL_SUPPLIER(1005, "医院供应商"),
    /**
     * 物料
     */
    ITEM(1006, "物料"),
    /**
     * 医院维护的自己物料与供应商的关系
     */
    APPROVED_SUPPLIER(1007, "中标库"),
    /**
     * 与供应商有合作关系的医院
     */
    PARTNER_SUPPLIER(1008, "合作医院"),
    /**
     * 医院的合作供应商
     */
    PARTNER_HOSPITAL(1009, "合作供应商"),
    /**
     * 单位
     */
    UNIT(1010, "单位"),
    /**
     * 职员
     */
    EMP(1011, "职员"),
    /**
     * 医院资料
     */
    HOSPITAL(1012, "医院资料"),
    /**
     * 供应商资料
     */
    SUPPLIER(1013, "供应商资料"),
    /**
     * 物料证件类别
     */
    MATERIAL_DOCUMENT_TYPE(1014, "物料证件类别"),
    /**
     * 物料证件
     */
    MATERIAL_DOCUMENT(1015, "物料证件"),
    /**
     * 供应商物料
     */
    SUPPLIER_MATERIAL(1016, "供应商物料"),
    /**
     * 系统常量
     */
    ASSISTANCE(1080, "系统常量"),
    /**
     * 采购订单
     */
    PURCHASE_ORDER(2001, "采购订单"),
    /**
     * 供应商向医院发送的希望成为其供应商的申请
     */
    COOPERATION_APPLY(3001, "申请记录"),
    /**
     * 系统各类型的通知消息
     */
    MESSAGE(4001, "消息通知");
    
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
