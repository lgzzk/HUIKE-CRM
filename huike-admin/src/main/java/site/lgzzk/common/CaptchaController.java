package site.lgzzk.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.lgzzk.core.domain.Result;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static site.lgzzk.constant.RedisConstants.CAPTCHA_KEY;
import static site.lgzzk.constant.RedisConstants.CAPTCHA_TTL;

@RestController
public class CaptchaController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("captchaImage")
    public Result captchaImage() {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(250, 80);
        String uuid = IdUtil.simpleUUID();
        String captcha_key = CAPTCHA_KEY + uuid;
        String captchaCode = captcha.getCode();
        redisTemplate.opsForValue().set(captcha_key, captchaCode, CAPTCHA_TTL, TimeUnit.MINUTES);
        String base64 = Base64Utils.encodeToString(captcha.getImageBytes());
        Result result = Result.ok();
        result.put("uuid", uuid);
        result.put("img", base64);
        return result;
    }
}
