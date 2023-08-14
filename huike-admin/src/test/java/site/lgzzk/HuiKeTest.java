package site.lgzzk;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import site.lgzzk.common.core.domain.Result;
import site.lgzzk.common.core.domain.entity.SysUser;
import site.lgzzk.common.core.domain.model.UserLogin;
import site.lgzzk.common.core.redis.RedisCache;
import site.lgzzk.framework.web.service.TokenService;
import site.lgzzk.framework.web.service.UserDetailsServiceImpl;
import site.lgzzk.system.mapper.SysUserMapper;
import site.lgzzk.system.service.SysUserService;

import java.util.Date;

import static site.lgzzk.common.constant.Constants.LOGIN_USER_KEY;
import static site.lgzzk.common.constant.RedisConstants.LOGIN_TOKENS;

@SpringBootTest
public class HuiKeTest {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    TokenService tokenService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisCache redisCache;

    @Test
    public void jwt() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("admin");
        UserLogin userLogin = new UserLogin(sysUser);
        System.out.println(tokenService.createToken(userLogin));
    }

    @Test
    public void parseToken() {
        Claims claims = tokenService.parseToken("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwibG9naW5fdXNlcl9rZXkiOjEsImlhdCI6MTY5MDYxNzc3OSwiZXhwIjoxNjkwNjE5NTc5fQ.nbfry8Zkmd4uABO32WnCtOqYSiNSWFUv0udnL-9vkGky_kheuY4owO69igCVezAoa8zGM7nnlWVLgetMLeMlRQ");
        String loginUserKey = LOGIN_TOKENS + claims.get(LOGIN_USER_KEY).toString();
        UserLogin userLogin = redisCache.getCacheObject(loginUserKey, UserLogin.class);
        System.out.println(userLogin);
    }

    @Test
    public void DateTime() {
        System.out.println(new Date());
    }

    @Test
    public void verify() {
        String loginToken = LOGIN_TOKENS + 1;
        SysUser sysUser = new SysUser();
        sysUser.setUserName("admin");
        sysUser.setPassword("password");
        sysUser.setEmail("email");
//        redisCache.setCacheObject("user", new UserLogin(sysUser));
//        redisCache.setCacheString("user", "asdasdasdas啊实打实的111");
//        System.out.println(redisTemplate.opsForValue().get("user"));
//        Object user = redisCache.getCacheObject("user");
        Object user = redisTemplate.opsForValue().get("user");
        System.out.println((UserLogin) user);
//        System.out.println(redisCache.getCacheString("user"));
//        redisCache.delete("user");
//        System.out.println(JSONUtil.parseObject(JSONUtil.toJSONString(sysUser), SysUser.class));
//        System.out.println(redisCache.getCacheString(loginToken));
//        UserLogin userLogin = redisCache.getCacheObject(loginToken);
    }

    @Test
    public void sysUserService() {
        System.out.println(sysUserService.list());
    }


    @Test
    public void redisTemplate() {
        String s = stringRedisTemplate.opsForValue().get("captcha_key:0e40802242e248d48a5dc20b872d5b64");
        System.out.println(s);
    }

    @Test
    public void JSONUtil() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("admin");
        sysUser.setPassword("password");
        sysUser.setEmail("email");
        UserLogin userLogin = new UserLogin(sysUser);
        userLogin.setUuid(IdUtil.simpleUUID());
        redisCache.setCacheObject("user", userLogin);
//        redisTemplate.opsForValue().set("user", sysUser);
//        redisTemplate.opsForValue().get("user");
//        System.out.println(redisTemplate.opsForValue().get("user"));
    }

    @Test
    public void userLogin() {
        System.out.println(redisCache.getCacheObject("login_tokens:eaa653fba9d949bb84ba750343d42c67", UserLogin.class));
    }


    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(Result.ok()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
