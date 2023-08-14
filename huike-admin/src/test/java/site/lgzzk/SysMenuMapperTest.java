package site.lgzzk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.lgzzk.system.mapper.SysMenuMapper;

@SpringBootTest
public class SysMenuMapperTest {

    @Autowired
    SysMenuMapper sysMenuMapper;


    @Test
    public void selectUserPerms(){
        System.out.println(sysMenuMapper.selectPermsByUserId(2L));
    }

}
