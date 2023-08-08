package site.lgzzk.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.lgzzk.common.core.domain.model.LoginBody;
import site.lgzzk.common.core.redis.RedisCache;
import site.lgzzk.common.exception.CustomException;
import site.lgzzk.common.core.domain.model.UserLogin;

import static site.lgzzk.common.constant.RedisConstants.CAPTCHA_KEY;
import static site.lgzzk.common.constant.RedisConstants.LOGIN_TOKENS;

@Service
public class SysLoginService {

    @Autowired
    RedisCache redisCache;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    public String login(LoginBody loginBody) {
        String captchaKey = CAPTCHA_KEY + loginBody.getUuid();
        String captcha = redisCache.getCacheString(captchaKey);

        if (captcha == null) {
            throw new CustomException("验证码已失效");
        }
        redisCache.delete(captchaKey);
        if (!captcha.equals(loginBody.getCaptcha())) {
            throw new CustomException("验证码错误");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginBody.getUserName(), loginBody.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserLogin userLogin = (UserLogin) authenticate.getPrincipal();
        return tokenService.createToken(userLogin);
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserLogin userLogin = (UserLogin) authentication.getPrincipal();
        String loginToken = LOGIN_TOKENS + userLogin.getUuid();
        redisCache.delete(loginToken);
    }


}
