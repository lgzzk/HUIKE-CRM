package site.lgzzk.framework.security.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import site.lgzzk.common.core.domain.Result;
import site.lgzzk.common.core.domain.model.UserLogin;
import site.lgzzk.common.core.redis.RedisCache;
import site.lgzzk.common.utils.ServletUtils;
import site.lgzzk.framework.web.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static site.lgzzk.common.constant.Constants.LOGIN_USER_KEY;
import static site.lgzzk.common.constant.Constants.TOKEN;
import static site.lgzzk.common.constant.RedisConstants.LOGIN_TOKENS;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(TOKEN);
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        Claims claims = null;
        try {
            claims = tokenService.parseToken(token);
        } catch (Exception ignored) {
            ServletUtils.write(response, Result.fail(UNAUTHORIZED.value(), "认证失败, 无法访问系统资源"));
            return;
        }

        String loginToken = LOGIN_TOKENS + claims.get(LOGIN_USER_KEY);
        UserLogin userLogin = redisCache.getCacheObject(loginToken, UserLogin.class);
        if (userLogin == null) {
            ServletUtils.write(response, Result.fail(UNAUTHORIZED.value(), "认证失败, 请重新登录"));
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLogin, null, userLogin.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
