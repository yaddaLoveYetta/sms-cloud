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

    /**
     * is Lombok available
     */
    private static boolean lombokAvailable = false;

    /**
     * is Jackson available
     */
    private static boolean jacksonAvailable = false;

    /**
     * is fastJson available
     */
    private static boolean fastJsonAvailable = false;

    /**
     * is springDateFormatter available
     */
    private static boolean springDateFormatterAvailable = false;

    static {

        try {
            //
            //判断类是否存在可使用的方法
            // lombokAvailable = null != Class.forName("lombok.Lombok");
            // Thread.currentThread().getContextClassLoader().loadClass("lombok.Lombok");
            // Class.forName,loadClass 区别参见
            // https://blog.csdn.net/x_iya/article/details/73199280
            Thread.currentThread().getContextClassLoader().loadClass("lombok.Lombok");
            lombokAvailable = true;
        } catch (Throwable t) {
            lombokAvailable = false;
        }

        try {
            Thread.currentThread().getContextClassLoader().loadClass("com.fasterxml.jackson.databind.ObjectMapper");
            jacksonAvailable = true;
        } catch (Throwable t) {
            jacksonAvailable = false;
        }

        try {
            Thread.currentThread().getContextClassLoader().loadClass("com.alibaba.fastjson.JSONObject");
            fastJsonAvailable = true;
        } catch (Throwable t) {
            fastJsonAvailable = false;
        }

        try {
            Thread.currentThread().getContextClassLoader()
                    .loadClass("org.springframework.format.datetime.DateFormatter");
            springDateFormatterAvailable = true;
        } catch (Throwable t) {
            springDateFormatterAvailable = false;
        }

    }

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

        if (lombokAvailable) {
            addLombokImportedType(topLevelClass, introspectedTable);
            addLombokAnnotation(topLevelClass, introspectedTable);
        }

        return true;

    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {

        if (lombokAvailable) {
            addLombokImportedType(topLevelClass, introspectedTable);
            addLombokAnnotation(topLevelClass, introspectedTable);
        }

        return true;
    }

    private void addLombokImportedType(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        //添加domain的import
        topLevelClass.addImportedType("lombok.Getter");
        topLevelClass.addImportedType("lombok.Setter");
        topLevelClass.addImportedType("lombok.ToString");
        topLevelClass.addImportedType("lombok.experimental.Accessors");
    }

    private void addLombokAnnotation(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        //添加domain的注解
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");
        topLevelClass.addAnnotation("@ToString(callSuper = true)");
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

            if (fastJsonAvailable) {
                addFastJsonDateTimeFormatImportedType(topLevelClass);
                field.addAnnotation("@JSONField(format = \"yyyy-MM-dd\")");
            }

            if (jacksonAvailable) {
                addJacksonDateTimeFormatImportedType(topLevelClass);
                field.addAnnotation("@JsonFormat(pattern = \"yyyy-MM-dd\",timezone=\"GMT+8\")");
            }

            if (springDateFormatterAvailable) {
                addSpringDateTimeFormatImportedType(topLevelClass);
                field.addAnnotation("@DateTimeFormat(pattern = \"yyyy-MM-dd\")");
            }

        } else if (introspectedColumn.getJdbcType() == 92) {
            // Time

            if (fastJsonAvailable) {
                addFastJsonDateTimeFormatImportedType(topLevelClass);
                field.addAnnotation("@JSONField(format = \"HH:mm:ss\")");
            }

            if (jacksonAvailable) {
                addJacksonDateTimeFormatImportedType(topLevelClass);
                field.addAnnotation("@JsonFormat(pattern = \"HH:mm:ss\",timezone=\"GMT+8\")");
            }

            if (springDateFormatterAvailable) {
                addSpringDateTimeFormatImportedType(topLevelClass);
                field.addAnnotation("@DateTimeFormat(pattern = \"HH:mm:ss\")");
            }

        } else if (introspectedColumn.getJdbcType() == 93) {
            // DateTime || timestamp

            if (fastJsonAvailable) {
                addFastJsonDateTimeFormatImportedType(topLevelClass);
                field.addAnnotation("@JSONField(format = \"yyyy-MM-dd HH:mm:ss\")");
            }

            if (jacksonAvailable) {
                addJacksonDateTimeFormatImportedType(topLevelClass);
                field.addAnnotation("@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\",timezone=\"GMT+8\")");
            }

            if (springDateFormatterAvailable) {
                addSpringDateTimeFormatImportedType(topLevelClass);
                field.addAnnotation("@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");
            }

        }

        return true;

    }

    private void addJacksonDateTimeFormatImportedType(TopLevelClass topLevelClass) {

        if (jacksonAvailable) {
            topLevelClass.addImportedType("com.fasterxml.jackson.annotation.JsonFormat");
        }

    }

    private void addFastJsonDateTimeFormatImportedType(TopLevelClass topLevelClass) {

        if (fastJsonAvailable) {
            topLevelClass.addImportedType("com.alibaba.fastjson.annotation.JSONField");
        }
    }

    private void addSpringDateTimeFormatImportedType(TopLevelClass topLevelClass) {

        if (springDateFormatterAvailable) {
            topLevelClass.addImportedType("org.springframework.format.annotation.DateTimeFormat");
        }
    }

}
