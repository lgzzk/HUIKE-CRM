package site.lgzzk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import site.lgzzk.framework.security.UserDetailsServiceImpl;
import site.lgzzk.system.mapper.SysUserMapper;
import site.lgzzk.system.service.SysUserService;

@SpringBootTest
public class HuiKeTest {

    @Autowired
    SysUserMapper sysUserMapper;


    @Autowired
    SysUserService sysUserService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Test
    public void  passwordEncoder(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//        String encode = passwordEncoder.encode("admin123");
//        $2a$10$Zqk0af9KUT4MJuLysl/pS.JycKmHaH6bMHS6Kv8bl55Te8WC/.G/2
//        $2a$10$WgRzi7RC/XVxSY04pW25pObikiwSk92m1RiancyNUvA92z3QJyWI6
        System.out.println(passwordEncoder.matches("admin123", "$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2"));
    }

    @Test
    public void sysUserService() {
        System.out.println(sysUserService.list());
    }
}
