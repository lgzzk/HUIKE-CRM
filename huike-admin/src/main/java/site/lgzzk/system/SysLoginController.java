package site.lgzzk.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.lgzzk.common.core.domain.Result;
import site.lgzzk.common.core.domain.model.LoginBody;
import site.lgzzk.framework.web.service.SysLoginService;

import java.util.HashMap;

import static site.lgzzk.common.constant.Constants.TOKEN;

@RestController
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/login")
    public Result<HashMap<String, String>> login(@RequestBody LoginBody loginBody) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TOKEN, sysLoginService.login(loginBody));
        return Result.ok(map);
    }

    @GetMapping("/test")
    @PreAuthorize("hasAnyAuthority('system')")
    public Result test() {
        return Result.ok(new Integer(100));
    }

    @GetMapping("/logout")
    public Result logout() {
        sysLoginService.logout();
        return Result.ok();
    }

}
