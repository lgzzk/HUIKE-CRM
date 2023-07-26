package site.lgzzk.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import site.lgzzk.common.core.domain.model.LoginBody;
import site.lgzzk.common.exception.CustomException;
import site.lgzzk.common.utils.JSONUtil;
import site.lgzzk.framework.security.UserLogin;

import static site.lgzzk.common.constant.RedisConstants.CAPTCHA_KEY;
import static site.lgzzk.common.constant.RedisConstants.LOGIN_TOKENS;

@Service
public class SysLoginService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    public String login(LoginBody loginBody) {
        String captchaKey = CAPTCHA_KEY + loginBody.getUuid();
        String captcha = redisTemplate.opsForValue().get(captchaKey);

        if (captcha == null) {
            throw new CustomException("验证码已失效");
        }
        if (!captcha.equals(loginBody.getCaptcha())) {
            throw new CustomException("验证码错误");
        }

        redisTemplate.delete(loginBody.getUuid());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginBody.getUserName(), loginBody.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserLogin userLogin = (UserLogin) authenticate.getPrincipal();
        String loginToken = LOGIN_TOKENS + userLogin.getSysUser().getUserId();
        redisTemplate.opsForValue().set(loginToken, JSONUtil.toJSONString(userLogin));
        return tokenService.createToken(userLogin);
    }
}
