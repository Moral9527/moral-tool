package icu.moral.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class JacksonUtils {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //空值或""不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException ex) {
            log.error("JacksonUtils.fromJson ErrorData=[{}], ErrorMsg=[{}]", json, ex.getMessage(), ex);
        }
        return null;
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException ex) {
            log.error("JacksonUtils.fromJson ErrorData=[{}], ErrorMsg=[{}]", json, ex.getMessage(), ex);
        }
        return null;
    }

    public static <T> List<T> list(String json, Class<T> clazz) {
        return fromJson(json, TypeFactory.defaultInstance().constructParametricType(ArrayList.class, getJavaType(clazz)));
    }

    public static <K, V> Map<K, V> map(String json, Class<K> keyClazz, Class<V> valclazz) {
        return fromJson(json, TypeFactory.defaultInstance().constructParametricType(HashMap.class, getJavaType(keyClazz), getJavaType(valclazz)));
    }

    public static Map<String, String> map(String json) {
        return map(json, String.class, String.class);
    }

    public static <T> T fromJson(String json, JavaType javaType) {
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException ex) {
            log.error("JacksonUtils.fromJson ErrorData=[{}], ErrorMsg=[{}]", json, ex.getMessage(), ex);
        }
        return null;
    }

    public static <T> String toJson(T entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (IOException ex) {
            log.error("JacksonUtils.toJson ErrorData=[{}], ErrorMsg=[{}]", entity, ex.getMessage(), ex);
        }
        return null;
    }


    public static JavaType getJavaType(Type type) {
        //判断是否带有泛型
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            //获取泛型类型
            Class rowClass = (Class) ((ParameterizedType) type).getRawType();
            JavaType[] javaTypes = new JavaType[actualTypeArguments.length];
            for (int i = 0; i < actualTypeArguments.length; i++) {
                //泛型也可能带有泛型，递归获取
                javaTypes[i] = getJavaType(actualTypeArguments[i]);
            }
            return TypeFactory.defaultInstance().constructParametricType(rowClass, javaTypes);
        } else {
            //简单类型直接用该类构建JavaType
            Class cla = (Class) type;
            return TypeFactory.defaultInstance().constructParametricType(cla, new JavaType[0]);
        }
    }
}

