package com.kingdee.hrp.sms.util;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author le.xiao
 */
public class LombokPlugin extends PluginAdapter {

    private String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        //Mapper class文件的注释
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine("* Created by Mybatis Generator on " + date2Str(new Date()));
        interfaze.addJavaDocLine("* 请不要改动此文件");
        interfaze.addJavaDocLine("* @author le.xiao");
        interfaze.addJavaDocLine("*/");
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        addImportedType(topLevelClass, introspectedTable);
        addAnnotation(topLevelClass, introspectedTable);

        return true;

    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {

        addImportedType(topLevelClass, introspectedTable);
        addAnnotation(topLevelClass, introspectedTable);

        return true;
    }

    private void addImportedType(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        //添加domain的import
        topLevelClass.addImportedType("lombok.Getter");
        topLevelClass.addImportedType("lombok.Setter");
        topLevelClass.addImportedType("lombok.ToString");
        topLevelClass.addImportedType("lombok.experimental.Accessors");
    }

    private void addAnnotation(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        //添加domain的注解
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");
        topLevelClass.addAnnotation("@ToString");
        topLevelClass.addAnnotation("@Accessors(chain = true)");
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成getter
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成setter
        return false;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
            IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        if (introspectedColumn.getJdbcType() == 91) {
            // Date
            field.addAnnotation("@JsonFormat(pattern = \"yyyy-MM-dd\")");
            field.addAnnotation("@DateTimeFormat(pattern = \"yyyy-MM-dd\")");

            addDateTimeFormatImportedType(topLevelClass);

        } else if (introspectedColumn.getJdbcType() == 92) {
            // Time
            field.addAnnotation("@JsonFormat(pattern = \"HH:mm:ss\")");
            field.addAnnotation("@DateTimeFormat(pattern = \"HH:mm:ss\")");

            addDateTimeFormatImportedType(topLevelClass);

        } else if (introspectedColumn.getJdbcType() == 93) {
            // DateTime || timestamp
            field.addAnnotation("@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");
            field.addAnnotation("@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");

            addDateTimeFormatImportedType(topLevelClass);

        }

        return true;

    }

    private void addDateTimeFormatImportedType(TopLevelClass topLevelClass) {

        topLevelClass.addImportedType("com.fasterxml.jackson.annotation.JsonFormat");
        topLevelClass.addImportedType("org.springframework.format.annotation.DateTimeFormat");

    }
}
