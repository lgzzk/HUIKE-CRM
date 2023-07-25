package site.lgzzk.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import site.lgzzk.common.core.domain.Result;
import site.lgzzk.common.core.domain.model.LoginBody;
import site.lgzzk.common.exception.CustomException;

@Service
public class SysLoginService {

	@Autowired
    StringRedisTemplate redisTemplate;

    public String login(LoginBody loginBody) {
        String captcha = redisTemplate.opsForValue().get(loginBody.getUuid());
        if (!captcha.equals(loginBody.getCaptcha())) {
            throw new CustomException("验证码错误");
        }
        return null;
    }
}
