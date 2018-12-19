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
public class FormActionKey implements Serializable {
    /**
     * 菜单id(参考t_menu表form_action_id引用此id,表示menu菜单下挂的功能按钮) 明细菜单，即二级菜单配置
此class_id跟t_form_class中class_id没有必然的联系.
但对于t_form_class有定义的业务单元，一般用其class_id.
对于使用通用的list，bill展现的菜单form_action_id必须配置为其class_id
     */
    private Integer classId;

    /**
     * 功能显示顺序
必须至少配置一个index=0的功能项，一般为查看权限。
没有此权限项或没有此项权限表示用户用户不能进入该功能，前端也不展现其关联菜单
     */
    private Integer index;

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