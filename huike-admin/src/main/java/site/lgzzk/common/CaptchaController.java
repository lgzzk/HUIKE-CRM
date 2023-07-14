package site.lgzzk.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class CaptchaController {

    @GetMapping("captchaImage")
    public void captchaImage(HttpServletResponse response, HttpSession session) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(300, 100);
            session.setAttribute("CAPTCHA", lineCaptcha.getCode());
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            lineCaptcha.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("checkCaptcha")
    public String checkCaptcha(HttpSession session, HttpServletRequest request){
        System.out.println(request.getSession() == session);
        return session.getAttribute("CAPTCHA").toString();
    }

}
