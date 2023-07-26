package site.lgzzk.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class JSONUtil {

    private static final JsonMapper jsonMapper = new JsonMapper();

    public static String toJSONString(Object obj) {
        try {
            return jsonMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        try {
            return jsonMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
