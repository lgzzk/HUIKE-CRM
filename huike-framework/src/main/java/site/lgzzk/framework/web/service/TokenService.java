package site.lgzzk.framework.web.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.lgzzk.common.core.domain.model.UserLogin;

import java.util.Date;

import static site.lgzzk.common.constant.Constants.LOGIN_USER_KEY;

@Component
public class TokenService {

    @Value("${token.header}")
    private String header;
    @Value("${token.secret}")
    private String secret;
    @Value("${token.expiration}")
    private long expiration;

    public static final long MILLS_IN_SECONDS = 1000L;
    public static final long MILLS_IN_MINUTES = MILLS_IN_SECONDS * 60L;

    public String createToken(UserLogin userLogin) {
        return Jwts.builder()
                .claim("username", userLogin.getUsername())
                .claim(LOGIN_USER_KEY,userLogin.getSysUser().getUserId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * MILLS_IN_MINUTES))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
