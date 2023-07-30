package site.lgzzk.common.core.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {

    @Autowired
    StringRedisTemplate redisTemplate;


    public void setCacheObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value));
    }

    public void setCacheObject(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value), timeout, unit);
    }

    public void setCacheString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setCacheString(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public <T> T getCacheObject(String key, Class<T> clazz) {
        return JSON.parseObject(redisTemplate.opsForValue().get(key), clazz);
    }

    public String getCacheString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

}
