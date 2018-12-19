package com.kingdee.hrp.sms.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 由数据库表[t_form_field]生成
 * @author yadda
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class FormField extends FormFieldKey implements Serializable {
    /**
     * 标示模板字段所在的页面（0是表头，1是第一个子表，2是第二个子表，以此类推...）
     */
    private Integer page;

    /**
     * 字段名(显示的字段名)
     */
    private String name;

    /**
     * 物理表中的字段名
     */
    private String sqlColumnName;

    /**
     * 字段类型(数字，文本等)1:数字2:文本3:日期4:布尔,用于前端解析
     */
    private Integer dataType;

    /**
     * 控件类型，指示前端展示时的特殊类型，比如多选框，基础资料选择框等
     */
    private Integer ctrlType;

    /**
     * 字段的后台查询顺序，index是用于前端界面展示时的tab顺序ctrl_index用于后端查询构建查询脚本时模板的遍历顺序，处理因为可能涉及关联其他表查询，连接表时的顺序不可随意改变时产生的错误
     */
    private Integer ctrlIndex;

    /**
     * 前端字段的显示顺序
     */
    private Integer index;

    /**
     * 字段显示性:用于根据角色类别控制字段显示性。
采用三位二进制方式配置
如1(001)表示该字段只有系统用户能可见；2(010)表示该字段只有医院用户可见；3(011)表示该字段系统用户与医院用户都可见
参考t_assistance,type=20
     */
    private Integer display;

    /**
     * 字段在前端页面显示的宽度(单位px)
     */
    private Integer showWidth;

    /**
     * 标示是否是引用类型
1:引用基础资料
     */
    private Integer lookUpType;

    /**
     * 如果是引用类型，改字段指示引用类型的业务类型class_id
     */
    private Integer lookUpClassId;

    /**
     * 引用的表名(表示此字段是要关联表查询)
     */
    private String srcTable;

    /**
     * 关联表别名(用于关联多次同一张表时)
     */
    private String srcTableAlis;

    /**
     * 关联表的字段名,当src_table有值时，此字段必须配置
     */
    private String srcField;

    /**
     * 关联表显示的字段名,当src_table有值时，此字段为必填,即显示src_table表的display_field字段
     */
    private String displayField;

    /**
     * 关联表显示的附加字段,如关联基础资料时一般需要显示基础资料的名称与代码display_field配置成显示name,display_ext可配置成显示number
     */
    private String displayExt;

    /**
     * 表链接类型(默认 INNER JOIN)
     */
    private String joinType;

    /**
     * 可附加过滤条件
     */
    private String filter;

    /**
     * 默认值(控件初始化时候的默认值)
     */
    private String defaultValue;

    /**
     * 最大值(数值类型字段使用)
     */
    private Double maxValue;

    /**
     * 最小值(数值类型字段使用)
     */
    private Double minValue;

    /**
     * 是否必录： 采用二进制方式配置, 参考t_assistance,type=25
     */
    private Integer mustInput;

    /**
     * 是否需要保存(指保存到数据表中)
     */
    private Boolean needSave;

    /**
     * 字段长度
     */
    private Integer length;

    /**
     * 字段锁定性:表示字段页面展现的控制可编辑形式。
采用二进制方式配置
如3表示新增修改时都锁定
参考t_assistance,type=15
     */
    private Integer lock;

    /**
     * 是否可作为过滤条件:表示字段页面展现时是否可以作为过滤字段。
采用二进制方式配置
1：表示系统角色时作为过滤字段
2：表示医院角色时作为过滤字段
4：表示供应商角色时作为过滤字段
参考t_assistance,type=36
如果配置为7 （1+2+4）即表示三种角色时都作为过滤字段
     */
    private Integer isCondition;

    /**
     * 是否统计项，0：否，1：是
     */
    private Integer isCount;

    private static final long serialVersionUID = 1L;

    /**
     * t_form_field
     */
    public enum Column {
        classId("class_id", "classId", "INTEGER", false),
        key("key", "key", "VARCHAR", true),
        page("page", "page", "INTEGER", false),
        name("name", "name", "VARCHAR", true),
        sqlColumnName("sql_column_name", "sqlColumnName", "VARCHAR", false),
        dataType("data_type", "dataType", "INTEGER", false),
        ctrlType("ctrl_type", "ctrlType", "INTEGER", false),
        ctrlIndex("ctrl_index", "ctrlIndex", "INTEGER", false),
        index("index", "index", "INTEGER", true),
        display("display", "display", "INTEGER", false),
        showWidth("show_width", "showWidth", "INTEGER", false),
        lookUpType("look_up_type", "lookUpType", "INTEGER", false),
        lookUpClassId("look_up_class_id", "lookUpClassId", "INTEGER", false),
        srcTable("src_table", "srcTable", "VARCHAR", false),
        srcTableAlis("src_table_alis", "srcTableAlis", "VARCHAR", false),
        srcField("src_field", "srcField", "VARCHAR", false),
        displayField("display_field", "displayField", "VARCHAR", false),
        displayExt("display_ext", "displayExt", "VARCHAR", false),
        joinType("join_type", "joinType", "VARCHAR", false),
        filter("filter", "filter", "VARCHAR", true),
        defaultValue("default_value", "defaultValue", "VARCHAR", false),
        maxValue("max_value", "maxValue", "DOUBLE", false),
        minValue("min_value", "minValue", "DOUBLE", false),
        mustInput("must_input", "mustInput", "INTEGER", false),
        needSave("need_save", "needSave", "BIT", false),
        length("length", "length", "INTEGER", true),
        lock("lock", "lock", "INTEGER", true),
        isCondition("is_condition", "isCondition", "INTEGER", false),
        isCount("is_count", "isCount", "INTEGER", false);

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