package site.lgzzk;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWTUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.jsonwebtoken.Claims;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.lgzzk.framework.security.UserDetailsServiceImpl;
import site.lgzzk.framework.security.UserLogin;
import site.lgzzk.framework.web.service.TokenService;
import site.lgzzk.system.entity.SysUser;
import site.lgzzk.system.mapper.SysUserMapper;
import site.lgzzk.system.service.SysUserService;

import java.util.Date;

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

    @Test
    public void jwt() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("admin");
        UserLogin userLogin = new UserLogin(sysUser);
        System.out.println(tokenService.createToken(userLogin));
    }

    @Test
    public void parseToken() {
        Claims claims = tokenService.parseToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5MDI1ODM1OSwiZXhwIjoxNjkwMjU4NDc5fQ.-fkdXXAEne78M3K7nnL1JyLdctWt7YZeR6Nn9zVFMb_h0VYUVYQDGy0ZEJrII078mvVhGtzRsf2JIEXacCeWqQ");
    }

    @Test
    public void DateTime() {
        System.out.println(new Date());
    }

    @Test
    public void verify() {
        byte[] bytes = "lgzzk.common.site".getBytes();
        System.out.println(JWTUtil.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoibGd6emsiLCJleHAiOjE2OTAxMDQwMDV9.VCcAk2c6t55I4PQAokSstFl2C843P4X-Qtq6ylxGigw", bytes));
    }

    @Test
    public void sysUserService() {
        System.out.println(sysUserService.list());
    }
}
