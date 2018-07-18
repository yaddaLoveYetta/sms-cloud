package com.kingdee.hrp.sms.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingdee.hrp.sms.common.exception.BusinessLogicRunTimeException;
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
     * son字符串转bean
     *
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends Collection> T stringToList(String str, Class<T> collectionClass,
            Class<?>... elementClasses) {

        T target;

        if (!StringUtils.isNotBlank(str)) {
            //return new ArrayList<T>();
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

            //JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            target = objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            throw new BusinessLogicRunTimeException(e.getMessage(), e);
        }

        return target;
    }
}
