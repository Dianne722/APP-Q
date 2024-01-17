package com.example.myapp;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 设置可见性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 忽略大小写
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        // 取消含有不存在的字段时失败
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 开启小驼峰转换
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        // 将日期序列化为可读字符串而不是时间戳
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //设置时间模块(格式化，不设置，则输出默认格式)
        JavaTimeModule timeModule = new JavaTimeModule();
        // LocalDateTime
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"))));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"))));
        // LocalDate
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("Asia/Shanghai"))));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("Asia/Shanghai"))));

        // 设置自定义时间模块
        objectMapper.registerModule(timeModule);
    }

    /**
     * 将对象序列化为 JSON 字符串
     *
     * @param object 对象
     * @return json字符串
     */
    public static String toJson(Object object) {
        try {
            if (object == null) {
                return null;
            }
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将 JSON 字符串反序列化为对象
     *
     * @param json      json字符串
     * @param valueType 对象类型
     * @param <T>       t
     * @return t
     */
    public static <T> T toBean(String json, Class<T> valueType) {
        try {
            if (json == null || json.isEmpty()) {
                return null;
            }
            return objectMapper.readValue(json, valueType);

        } catch (Exception e) {
            Log.i("http", e.toString());
            return null;
        }
    }

    /**
     * 将 JSON 字符串反序列化为对象，泛型类型版本
     * @param json          json字符串
     * @param valueTypeRef  对象类型
     * @return              t
     * @param <T>           t
     */
    public static <T> T toBean(String json, TypeReference<T> valueTypeRef) {
        try {
            if (json == null || json.isEmpty()) {
                return null;
            }
            return objectMapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取Integer值
     *
     * @param json      json字符串
     * @param fieldName 字段名
     * @return integer||null
     */
    public static Integer getIntValue(String json, String fieldName) {
        try {
            if (json == null || json.isEmpty()) {
                return null;
            }
            if (fieldName == null || fieldName.isEmpty()) {
                return null;
            }
            JsonNode jsonNode = objectMapper.readTree(json);
            if (jsonNode == null) {
                return null;
            }

            return jsonNode.get(fieldName)
                    .asInt();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取String值
     *
     * @param json      json字符串
     * @param fieldName 字段名
     * @return string||null
     */
    public static String getStrValue(String json, String fieldName) {
        try {
            if (json == null || json.isEmpty()) {
                return null;
            }
            if (fieldName == null || fieldName.isEmpty()) {
                return null;
            }
            JsonNode jsonNode = objectMapper.readTree(json);
            if (jsonNode == null) {
                return null;
            }

            return jsonNode.get(fieldName)
                    .asText();
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * json字符串转 JsonNode
     *
     * @param json json字符串
     * @return JsonNode对象 || null
     */
    public static JsonNode getJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
