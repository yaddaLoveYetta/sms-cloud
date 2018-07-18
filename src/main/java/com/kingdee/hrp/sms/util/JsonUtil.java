package com.kingdee.hrp.sms.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.pojo.Condition;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JSON--Object转换工具类
 *
 * @author le.xiao
 */
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * bean转json字符串
     *
     * @param obj bean
     * @return json字符串
     */
    public static String bean2Json(Object obj) {

        StringWriter sw = new StringWriter();

        try {
            JsonGenerator gen = new JsonFactory().createGenerator(sw);
            mapper.writeValue(gen, obj);
            gen.close();
            return sw.toString();
        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }

    }

    /**
     * json字符串转bean
     *
     * @param jsonStr     json字符串
     * @param targetClass 目标bean类型
     * @param <T>         目标bean类型
     * @return 目标bean
     */
    public static <T> T json2Bean(String jsonStr, Class<T> targetClass) {
        try {
            return mapper.readValue(jsonStr, targetClass);
        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }
    }

    /**
     * json转换成集合类型
     *
     * @param jsonStr         json字符串
     * @param collectionClass 目标集合类型 eg: List.class
     * @param elementClasses  目标集合类型子项类型 eg:String.class   List<String></>
     * @param <T>             eg:List<String>
     * @return 转换后集合类型
     */
    public static <T extends Collection> T jsonToCollection(String jsonStr, Class<? extends Collection> collectionClass,
                                                            Class<?>... elementClasses) {

        T target;

        if (StringUtils.isBlank(jsonStr)) {
            throw new BusinessLogicRunTimeException("待转换json字符串不能为空");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

            target = objectMapper.readValue(jsonStr, javaType);

        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }

        return target;
    }

    public static void main(String[] args) throws JsonProcessingException {

        Condition condition = new Condition();
        condition.setFieldKey("number").setValue("122").setNeedConvert(false).setLeftParenTheses("(").setLinkType(Condition.LinkType.AND).setLogicOperator(Condition.LogicOperator.EQUAL).setRightParenTheses(")");

        Condition condition2 = new Condition();
        condition2.setFieldKey("number").setValue("122").setNeedConvert(false).setLeftParenTheses("(").setLinkType(Condition.LinkType.AND).setLogicOperator(Condition.LogicOperator.EQUAL).setRightParenTheses(")");


        List<Condition> conditionList = new ArrayList<>();
        conditionList.add(condition);
        conditionList.add(condition2);

        ObjectMapper objectMapper = new ObjectMapper();

        String s = objectMapper.writeValueAsString(conditionList);

        System.out.println(s);

        List<Condition> conditions = jsonToCollection(s, List.class, Condition.class);

        System.out.println(conditions);

    }
}
