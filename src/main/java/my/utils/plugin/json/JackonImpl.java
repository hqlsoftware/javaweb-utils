package my.utils.plugin.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import my.utils.utils.LogUtil;
import my.utils.utils.StringUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.TimeZone;

/**
 *
 *
 * @author heqilin
 * date:  2018-12-25 ok
 **/
public class JackonImpl implements IJson {

    private ObjectMapper mapper;

    public JackonImpl() {
        mapper = new ObjectMapper();

        // 允许单引号、允许不带引号的字段名称
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //在序列化时日期格式默认为 yyyy-MM-dd'T'HH:mm:ss.SSSZ
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 空值处理为空串
//        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object value, JsonGenerator jsonGenerator,
//                                  SerializerProvider provider) throws IOException {
//                jsonGenerator.writeString("");
//            }
//        });


        // 设置时区 getTimeZone("GMT+8:00")
        mapper.setTimeZone(TimeZone.getDefault());
    }

    /**
     * 生成json字符串
     **/
    @Override
    public String toJson(Object obj) {
        String result = null;
        try {
            result = mapper.writeValueAsString(obj);
        } catch (Exception ex) {
            LogUtil.error(null,ex);
        }
        return result;
    }
    /**
     * 生成对象实体
     **/
    @Override
    public <T> T toBean(Object jsonStr,Class<T> tClass) {
        try {
            if (StringUtil.isNotEmpty(jsonStr)) {
                return mapper.readValue(jsonStr.toString(), tClass);
            }
        } catch (Exception ex) {
            LogUtil.error(null,ex);
        }
        return null;
    }

    /**
     * 生成对象实体
     **/
    @Override
    public <T> List<T> toList(Object jsonStr,Class<T> tClass){
        try {
            if(StringUtil.isNotEmpty(jsonStr)){
                /**TypeReference<T> jsonTypeReference1 = new TypeReference<T>() {
                    @Override
                    public Type getType() {
                        return getListType(tClass);
                    }
                };*/
                JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, tClass);
                return mapper.readValue(jsonStr.toString(),javaType);
            }
        } catch (Exception ex) {
            LogUtil.error(null,ex);
        }
        return null;
    }

    public static void main(String[] args)
    {
        //TypeReference<List<int>> typeReference = new TypeReference<List<int>>(){};
        TypeReference<List<Integer>> typeReference1 = new TypeReference<List<Integer>>() {
        };
        ParameterizedType type = (ParameterizedType)typeReference1.getType();
        for (Type tmpType : type.getActualTypeArguments()) {
            Class<?> tmpType1 = (Class<?>) tmpType;
            System.out.println(tmpType1);
        }
    }
}
