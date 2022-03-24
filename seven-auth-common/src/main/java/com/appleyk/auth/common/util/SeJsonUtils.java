package com.appleyk.auth.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>json正反序列化工具类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:31
 */
public class SeJsonUtils {
    /**定义jackson对象*/
    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        /** 忽略JSON中没有的字段，防止反序列化对象的时候报找不到属性字段的异常*/
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        /**如果json字符串中含有新行时，加上这个*/
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        /**空值转换异常*/
        MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true) ;
    }

    private SeJsonUtils(){

    }

    /**
     * 将对象转换成json字符串。
     * @param data 对象
     */
    public static String object2Json(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     * @param jsonData json数据
     * @param beanType 对象类型
     */
    public static <T> T json2Pojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * @param jsonData json字符串
     * @param beanType 对象类型
     */
    public static <T> List<T> json2List(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串转Map集合
     */
    public static Map<String, Object> json2Map(String jsonStr) throws IOException {
        Map<String, Object> map = MAPPER.readValue(jsonStr, Map.class);
        return map;
    }

    /**
     * json字符串转List集合
     */
    public static List<String> json2List(String jsonStr) throws IOException {
        return MAPPER.readValue(jsonStr, new TypeReference<List<String>>() {});
    }
}
