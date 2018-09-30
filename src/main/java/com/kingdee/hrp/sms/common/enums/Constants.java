package com.kingdee.hrp.sms.common.enums;

import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.scm.enums.OrderDeliveryStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统常量
 * <p>
 * 主要是对应t_assistance表中各枚举常量
 *
 * @author yadda
 */
public final class Constants {

    /**
     * 系统预留权限项掩码
     * 请不要将个性化功能权限项加入
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

    /**
     * 单据操作模式
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

    /**
     * 业务类型枚举
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
         * 医院供应商资质类别
         */
        HOSPITAL_SUPPLIER_QUALIFICATION_TYPE(1016, "医院供应商资质类别"),
        /**
         * 医院供应商资质
         */
        HOSPITAL_SUPPLIER_QUALIFICATION(1017, "医院供应商资质"),
        /**
         * 供应商证件类别
         */
        SUPPLIER_QUALIFICATION_TYPE(1018, "供应商证件类别"),
        /**
         * 供应商证件
         */
        SUPPLIER_QUALIFICATION(1019, "供应商证件"),

        /**
         * 供应商物料
         */
        SUPPLIER_MATERIAL(1020, "供应商物料"),
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

    /**
     * 供应商向医院申请成为供应商的申请记录状态
     * <p>
     * 值跟t_assistance表 type=43一致
     */

    public enum CooperationApplyStatus {

        /**
         * 未处理状态的申请
         */
        UN_PROCESSED(1, "待处理"),
        /**
         * 同意
         */
        AGREE(2, "同意"),
        /**
         * 拒绝
         */
        DISAGREE(3, "拒绝");

        private static Logger logger = LoggerFactory.getLogger(OrderDeliveryStatus.class);

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

        public int value() {
            return this.getNumber();
        }

        CooperationApplyStatus(int number, String name) {
            this.number = number;
            this.name = name;
        }

        public static CooperationApplyStatus getCooperationApplyStatus(int number) {

            for (CooperationApplyStatus d : CooperationApplyStatus.values()) {
                if (d.number == number) {
                    return d;
                }
            }
            logger.error("CooperationApplyStatus不存状态值:" + number);
            throw new BusinessLogicRunTimeException("CooperationApplyStatus不存状态值:" + number);
        }
    }

    /**
     * 字段控件类型，控制前端显示实现样式
     * <p>
     * 值跟t_assistance表 type=30一致
     */
    public enum CtrlType {

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

        public int value() {
            return this.number;
        }

        private CtrlType(int number, String name) {
            this.number = number;
            this.name = name;
        }

        /**
         * 获取字段数据类型，默认文本
         *
         * @param number 类型代码
         * @return CtrlType
         */
        public static CtrlType getType(int number) {

            for (CtrlType d : CtrlType.values()) {
                if (d.number == number) {
                    return d;
                }
            }
            return CtrlType.TEXT;
        }
    }

    /**
     * 显示性控制
     * <p>
     * 值跟t_assistance表 type=20一致
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

        VIEW_SYSTEM_SHOW(1, "查看时对于平台用户显示"), ADD_SYSTEM_SHOW(2, "新增时对于平台用户显示"),
        EDIT_SYSTEM_SHOW(4, "编辑时对于平台用户显示"), VIEW_SUPPLIER_SHOW(8, "查看时对于供应商用户显示"),
        ADD_SUPPLIER_SHOW(16, "新增时对于供应商用户显示"), EDIT_SUPPLIER_SHOW(32, "编辑时对于供应商用户显示"),
        VIEW_HOSPITAL_SHOW(64, "查看时对于医院用户显示"), ADD_HOSPITAL_SHOW(128, "新增时对于医院用户显示"),
        EDIT_HOSPITAL_SHOW(256, "编辑时对于医院用户显示");

        private Integer number;
        private String desc;

        public int value() {
            return this.number;
        }

        public String desc() {
            return this.desc;
        }

        DisplayType(Integer number, String desc) {
            this.number = number;
            this.desc = desc;
        }
    }

    /**
     * 功能所属组织类别
     * <p>
     * 采用三位二进制设计，000，第一位标示系统管理员所属(001即1),第二位标示医院所属(010即2),第三位标示供应商所属(100即4)
     * 衍生出各组所属组合，如系统管理员及医院所属(001+010=011即3)
     */
    public enum FormActionOwnerType {

        /**
         * 只有系统管理员可以使用的功能
         */
        SYSTEM(1, "系统管理员"),
        /**
         * 只有医院可以使用的功能
         */
        HOSPITAL(2, "医院"),
        /**
         * 只有供应商可以使用的功能
         */
        SUPPLIER(4, "供应商"),
        /**
         * 只有系统管理员或医院可以使用的功能
         */
        SYSTEM_HOSPITAL(3, "系统管理员及医院"),
        /**
         * 只有系统管理员或供应商可以使用的功能
         */
        SYSTEM_SUPPLIER(5, "系统管理员及供应商"),
        /**
         * 只有医院或供应商可以使用的功能
         */
        HOSPITAL_SUPPLIER(6, "医院及供应商"),
        /**
         * 系统管理员或医院或供应商可以使用的功能
         */
        SYSTEM_HOSPITAL_SUPPLIER(7, "系统管理员及医院及供应商"),
        /**
         * 没有归属的功能，系统管理员，医院，供应商都不可以使用
         */
        NOT_SUPPORT(0, "功能没有归属");

        private int number;
        private String desc;

        FormActionOwnerType(int number, String desc) {
            this.number = number;
            this.desc = desc;
        }

        public static FormActionOwnerType getFormActionOwnerType(int number) {

            for (FormActionOwnerType d : FormActionOwnerType.values()) {
                if (d.number == number) {
                    return d;
                }
            }
            return FormActionOwnerType.NOT_SUPPORT;
        }
    }

    /**
     * 消息中心消息状态
     * <p>
     * 值跟t_assistance表 type=41一致
     */
    public enum MessageStatus {

        /**
         * 未处理状态
         */
        UN_PROCESSED(1, "未处理"),
        /**
         * 已处理状态
         */
        PROCESSED(2, "已处理");

        private static Logger logger = LoggerFactory.getLogger(MessageType.class);

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

        public int value() {
            return this.number;
        }

        MessageStatus(int number, String name) {
            this.number = number;
            this.name = name;
        }

        public static MessageStatus getMessageStatus(int number) {

            for (MessageStatus d : MessageStatus.values()) {
                if (d.number == number) {
                    return d;
                }
            }
            logger.error("MessageStatus不存状态值:" + number);
            throw new BusinessLogicRunTimeException("MessageStatus不存状态值:" + number);
        }

    }

    /**
     * 消息中心消息类别
     * <p>
     * 值跟t_assistance表 type=40一致
     */
    public enum MessageType {

        /**
         * 供应商向医院发送的希望成为其供应商的申请
         */
        COOPERATION_APPLICATION(1, "供应商向医院发送的希望成为其供应商的申请"),
        /**
         * 医院处理了供应商的申请消息
         */
        COOPERATION_APPLICATION_PROCESSED(2, "医院处理了供应商发送成为其供应商的申请");

        private static Logger logger = LoggerFactory.getLogger(MessageType.class);

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

        public int value() {
            return this.number;
        }

        MessageType(int number, String name) {
            this.number = number;
            this.name = name;
        }

        public static MessageType getMessageType(int number) {

            for (MessageType d : MessageType.values()) {
                if (d.number == number) {
                    return d;
                }
            }
            logger.error("MessageType不存状态值:" + number);
            throw new BusinessLogicRunTimeException("MessageType不存状态值:" + number);
        }
    }

    /**
     * 必录性控制
     * <p>
     * 值跟t_assistance表 type=25一致
     */
    public enum MustInputType {

    /*
        1		新增时对于系统管理员必填
        2		编辑时对于系统管理员用户必填
        4		新增时对于供应商用户必填
        8		编辑时对于供应商用户必填
        16		新增时对于医院用户必填
        32		编辑时对于医院用户必填
    */

        ADD_SYSTEM_MUST(1, "新增时对于系统管理员必填"), EDIT_SYSTEM_MUST(2, "编辑时对于系统管理员用户必填"),
        ADD_SUPPLIER_MUST(4, "新增时对于供应商用户必填"), EDIT_SUPPLIER_MUST(8, "编辑时对于供应商用户必填"),
        ADD_HOSPITAL_MUST(16, "新增时对于医院用户必填"), EDIT_HOSPITAL_MUST(32, "编辑时对于医院用户必填");

        private Integer number;
        private String desc;

        public int value() {
            return this.number;
        }

        public String desc() {
            return this.desc;
        }

        MustInputType(Integer number, String desc) {
            this.number = number;
            this.desc = desc;
        }
    }

    /**
     * 用户角色类型枚举
     */
    public enum UserRoleType {

        /**
         * 不支持的用户类别
         */
        NOT_SUPPORT(0, "不支持的类别"),
        /**
         * 系统管理员角色类型
         */
        SYSTEM(1, "系统管理员"),
        /**
         * 医院角色类型
         */
        HOSPITAL(2, "医院"),
        /**
         * 供应商角色类型
         */
        SUPPLIER(3, "供应商"),
        /**
         * 游客-暂时未设计
         */
        GUEST(4, "游客");

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

        public int value() {
            return this.number;
        }

        UserRoleType(int number, String name) {
            this.number = number;
            this.name = name;
        }

        public static UserRoleType getUserRoleType(int number) {

            for (UserRoleType userRoleTypeEnum : UserRoleType.values()) {
                if (userRoleTypeEnum.number == number) {
                    return userRoleTypeEnum;
                }
            }
            return UserRoleType.NOT_SUPPORT;
        }

    }

    /**
     * 各类文件存放路径根目录(业务放可能增加子目录)<br/>
     * eg:<br/>
     * 供应商logo图片存放路径 /1013/<br/>
     * 医院logo图片存放路径 /1012/<br/>
     * 供应商证件附件存放路径 /supplier/attachment/<br/>
     * 医院供应商资质附件存放路径/hospital/supplier/attachment/<br/>
     */
    public enum FilePath {

        /**
         * 供应商证件附件存放路径
         */
        SUPPLIER_QUALIFICATION_ATTACHMENT("/supplier/attachment/", "供应商证件附件存放路径"),
        /**
         * 医院供应商资质附件存放路径
         */
        HOSPITAL_SUPPLIER_QUALIFICATION_ATTACHMENT("/hospital/supplier/attachment/", "医院供应商资质附件存放路径");

        private String path;
        private String desc;

        FilePath(String path, String desc) {
            this.path = path;
            this.desc = desc;
        }

        public String path() {
            return this.path;
        }
    }

}
