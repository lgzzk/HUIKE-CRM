package site.lgzzk.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.lgzzk.common.core.domain.Result;

import java.util.concurrent.TimeUnit;

import static site.lgzzk.common.constant.RedisConstants.CAPTCHA_KEY;
import static site.lgzzk.common.constant.RedisConstants.CAPTCHA_TTL;

@RestController
public class CaptchaController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("captchaImage")
    public Result captchaImage() {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(120, 50);
        String uuid = IdUtil.simpleUUID();
        String captcha_key = CAPTCHA_KEY + uuid;
        String captchaCode = captcha.getCode();
        redisTemplate.opsForValue().set(captcha_key, captchaCode, CAPTCHA_TTL, TimeUnit.MINUTES);
        String base64 = Base64Utils.encodeToString(captcha.getImageBytes());
        return Result.ok()
                .put("uuid", uuid)
                .put("img", "data:image/jpg;base64,"+base64);
    }
}
