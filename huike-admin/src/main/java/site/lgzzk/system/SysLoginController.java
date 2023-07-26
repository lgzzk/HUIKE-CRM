package site.lgzzk.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.lgzzk.common.core.domain.Result;
import site.lgzzk.common.core.domain.model.LoginBody;
import site.lgzzk.framework.web.service.SysLoginService;

import static site.lgzzk.common.constant.Constants.TOKEN;

@RestController
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginBody loginBody) {
        return Result.ok().put(TOKEN, sysLoginService.login(loginBody));
    }

}
