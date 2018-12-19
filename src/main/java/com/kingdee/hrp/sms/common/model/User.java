package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_user]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class User implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 是否为管理员(1:管理员2:非管理员)

注册用户都是管理员
     */
    private Boolean isAdmin;

    /**
     * 关联组织
对于供应商用户此字段记录t_supplier供应商资料id
对于医院用户此字段记录t_hospital医院资料id
对于系统用户改字段为0
     */
    private Long org;

    /**
     * 是否禁用(0可用1禁用，默认0可用)
     */
    private Boolean status;

    private static final long serialVersionUID = 1L;

    /**
     * t_user
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        name("name", "name", "VARCHAR", true),
        userName("user_name", "userName", "VARCHAR", false),
        password("password", "password", "VARCHAR", true),
        mobile("mobile", "mobile", "VARCHAR", false),
        isAdmin("is_admin", "isAdmin", "BIT", false),
        org("org", "org", "BIGINT", false),
        status("status", "status", "BIT", true);

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