package site.lgzzk.common.core.domain.model;

import lombok.Data;

@Data
public class LoginBody {

    private String uuid;
    private String captcha;
    private String userName;
    private String password;


}
