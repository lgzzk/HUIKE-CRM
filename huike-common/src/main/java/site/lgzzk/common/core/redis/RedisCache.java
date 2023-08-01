package site.lgzzk.common.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    public void setCacheObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setCacheObject(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public void setCacheString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void setCacheString(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> opsForValue = redisTemplate.opsForValue();
        return opsForValue.get(key);
    }

    public String getCacheString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

}
