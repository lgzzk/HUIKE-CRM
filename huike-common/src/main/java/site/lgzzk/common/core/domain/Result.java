package site.lgzzk.common.core.domain;


import lombok.Data;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Data
public class Result<T> {

    private String massage;
    private Integer code;
    private T data;

    public static final String SUCCESS_MSG = "ok";
    public static final String ERROR_MSG = "fail";

    private Result() {
    }

    public static <T> Result<T> build(Integer code, String massage, T data) {
        Result<T> result = new Result<>();
        result.massage = massage;
        result.code = code;
        result.data = data;
        return result;
    }

    public static <T> Result<T> ok() {
        return ok(SUCCESS_MSG);
    }

    public static <T> Result<T> ok(T data) {
        return ok(SUCCESS_MSG, data);
    }

    public static <T> Result<T> ok(String massage) {
        return ok(massage, null);
    }

    public static <T> Result<T> ok(String massage, T data) {
        return build(OK.value(), massage, data);
    }

    public static <T> Result<T> fail() {
        return fail(ERROR_MSG);
    }

    public static <T> Result<T> fail(String msg) {
        return fail(INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        return build(code, msg, null);
    }


}
