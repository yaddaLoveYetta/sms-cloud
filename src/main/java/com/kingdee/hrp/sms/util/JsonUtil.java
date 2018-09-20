package com.kingdee.hrp.sms.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
import com.kingdee.hrp.sms.common.pojo.Condition;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * JSON--Object转换工具类
 *
 * @author le.xiao
 */
public final class JsonUtil {

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
     * @param jsonStr    json字符串
     * @param targetType 目标bean类型
     * @param <T>        目标bean类型
     * @return 目标bean
     */
    public static <T> T json2Bean(String jsonStr, Class<T> targetType) {
        try {
            return mapper.readValue(jsonStr, targetType);
        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }
    }

    /**
     * json转换成集合类型
     * <p>
     * eg:  List<Condition> conditions = JsonUtil.json2Collection(condition, List.class, Condition.class);
     *
     * @param jsonStr        json字符串
     * @param collectionType 目标集合类型 eg: List.class
     * @param elementType    目标集合类型子项类型 eg:String.class   List<String></>
     * @param <T>            eg:List<String>
     * @return 转换后集合类型
     */
    public static <T extends Collection> T json2Collection(String jsonStr, Class<? extends Collection> collectionType,
            Class<?>... elementType) {

        T target;

        if (StringUtils.isBlank(jsonStr)) {
            throw new BusinessLogicRunTimeException("待转换json字符串不能为空");
        }

        try {

            JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionType, elementType);

            target = mapper.readValue(jsonStr, javaType);

        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }

        return target;
    }

    /**
     * json转Map
     * <p>
     * eg: Map<String, Condition> map = json2Map(s2, Map.class, String.class, Condition.class);
     *
     * @param jsonStr   json字符串
     * @param mapType  Map或其子类
     * @param keyType   Map key类型
     * @param valueType Map value类型
     * @param <T>       Map 类型
     * @return Map
     */
    public static <T extends Map> T json2Map(String jsonStr, Class<T> mapType, Class<?> keyType,
            Class<?> valueType) {

        try {

            JavaType javaType = mapper.getTypeFactory().constructParametricType(mapType, keyType, valueType);

            return mapper.readValue(jsonStr, javaType);

        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }

    }

    public static void main(String[] args) throws JsonProcessingException {

        Condition condition = new Condition();
        condition.setFieldKey("number").setValue("122").setNeedConvert(false).setLeftParenTheses("(")
                .setLinkType(Condition.LinkType.AND).setLogicOperator(Condition.LogicOperator.EQUAL)
                .setRightParenTheses(")");

        Condition condition2 = new Condition();
        condition2.setFieldKey("number").setValue("222").setNeedConvert(true).setLeftParenTheses("(")
                .setLinkType(Condition.LinkType.OR).setLogicOperator(Condition.LogicOperator.NOT_EQUAL)
                .setRightParenTheses(")");

        List<Condition> conditionList = new ArrayList<>();
        conditionList.add(condition);
        conditionList.add(condition2);

        Map<String, Condition> conditionMap = new HashMap<>();

        conditionMap.put("c1", condition);
        conditionMap.put("c2", condition2);

        ObjectMapper objectMapper = new ObjectMapper();

        String s = objectMapper.writeValueAsString(conditionList);
        String s2 = objectMapper.writeValueAsString(conditionMap);

        System.out.println(s);
        System.out.println(s2);

        List<Condition> conditions = json2Collection(s, List.class, Condition.class);

        Map<String, Condition> map = json2Map(s2, Map.class, String.class, Condition.class);

        System.out.println(conditions);
        System.out.println(map);
        System.out.println(map.getClass().getName());

    }
}
