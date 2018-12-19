package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_form_action]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class FormAction extends FormActionKey implements Serializable {
    /**
     * 功能名称
     */
    private String name;

    /**
     * 操作方法名(前端使用)
     */
    private String text;

    /**
     * 权限掩码(每一个权限掩码代表了一个功能)
     */
    private Integer accessMask;

    /**
     * 关联权限掩码总和。
如有删除权限则一定具备查看及修改权限，
所以删除权限的access_use应配置成查看及修改权限的
access_mask之和
     */
    private Integer accessUse;

    /**
     * 按钮显示性控制(参考t_assistance表type=20配置)

查看按钮只在列表显示（所有角色类别用户）
新增按钮在列表及新增界面显示（所有角色类别用户）
修改按钮在列表及修改界面显示（所有角色类别用户）
删除按钮只在列表显示（所有角色类别用户）
禁用按钮只在列表显示（所有角色类别用户）
反禁用按钮只在列表显示（所有角色类别用户）
刷新按钮在列表、新增及修改界面显示（所有角色类别用户）

     */
    private Integer display;

    /**
     * 权限所属角色类别，
可指定该权限只能由哪些类别的角色可用
采用三位二进制描述
001（1）:系统类别角色可用
010（2）:医院类别角色可用
100（4）:供应商类别角色可用
还可以有其他组合设置如
011（3）: 系统类别角色与医院类别角色可用
111（7）:三类角色类别都可用

默认三类角色都可用
     */
    private Integer ownerType;

    /**
     * 按钮分组，前端渲染菜单使用

将功能按钮的group值设置为一样，表名这些功能按钮创建一个按钮组
如将 审核与反审核功能按钮 的group都设置为1，则前端会将这两个按钮创建到一组，下拉方式打开组内按钮。

当业务单据上的菜单项（按钮组为1项）超过五项后，其他的按钮会统一放在一个按钮组

     */
    private Integer group;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 描述
     */
    private String desc;

    /**
     * 按钮打开的url(如列表页面的新增修改按钮通常是打开另外一个页面)
当同时配置了url与api_url时以url为准，即有限打开页面

前端不完全依赖该配置，有前端本身处理逻辑
     */
    private String url;

    /**
     * 按钮点击时请求的后台api地址(如保存，审核，删除等按钮点击时需请求后台api处理)

当同时配置了url与api_url时以url为准，即有限打开页面
     */
    private String apiUrl;

    /**
     * 是否需要授权，0：不需要，1：需要。默认1
有些功能可能不需要授权，如导入导出可以不用进行权限控制。
有些功能可能验证的是相同的权限，只需要授权一个就可以了，如列表页面的新增与新增页面的保存功能，都验证是否有新增权限，此时保存功能就可以不需要授权，保存功能只需要保证与新增功能有一样的权限掩码即可，不需在角色授权页面显示保存权限项
     */
    private Boolean needAuthorization;

    private static final long serialVersionUID = 1L;

    /**
     * t_form_action
     */
    public enum Column {
        classId("class_id", "classId", "INTEGER", false),
        index("index", "index", "INTEGER", true),
        name("name", "name", "VARCHAR", true),
        text("text", "text", "VARCHAR", true),
        accessMask("access_mask", "accessMask", "INTEGER", false),
        accessUse("access_use", "accessUse", "INTEGER", false),
        display("display", "display", "INTEGER", false),
        ownerType("owner_type", "ownerType", "INTEGER", false),
        group("group", "group", "INTEGER", true),
        icon("icon", "icon", "VARCHAR", false),
        desc("desc", "desc", "VARCHAR", true),
        url("url", "url", "VARCHAR", false),
        apiUrl("api_url", "apiUrl", "VARCHAR", false),
        needAuthorization("need_authorization", "needAuthorization", "BIT", false);

        private static final String BEGINNING_DELIMITER = "`";

        private static final String ENDING_DELIMITER = "`";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}