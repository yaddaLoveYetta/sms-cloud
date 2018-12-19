package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_menu]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Menu implements Serializable {
    /**
     * 菜单id
1-100 一下的id预留作为一级菜单id，二级菜单id从10001开始
建议后两位作为二级菜单id，即每个一级菜单最多支持99个二级菜单
     */
    private Integer id;

    /**
     * 菜单名字
     */
    private String name;

    /**
     * 菜单显示顺序
     */
    private Integer index;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单链接地址
     */
    private String url;

    /**
     * 上级菜单id
     */
    private Integer parentId;

    /**
     * 菜单描述
     */
    private String desc;

    /**
     * 是否显示
     */
    private Boolean visible;

    /**
     * 关联t_form_action表class_id,用于验证权限(只对二级菜单有用,影响菜单权限控制)
对于使用通用的list，bill展现的菜单form_action_id必须配置为其class_id,
每个菜单项配置的值不可以相同，及时验证相同的权限项也请在t_form_action中增加classId配置
     */
    private Integer formActionId;

    private static final long serialVersionUID = 1L;

    /**
     * t_menu
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        name("name", "name", "VARCHAR", true),
        index("index", "index", "INTEGER", true),
        icon("icon", "icon", "VARCHAR", false),
        url("url", "url", "VARCHAR", false),
        parentId("parent_id", "parentId", "INTEGER", false),
        desc("desc", "desc", "VARCHAR", true),
        visible("visible", "visible", "BIT", false),
        formActionId("form_action_id", "formActionId", "INTEGER", false);

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