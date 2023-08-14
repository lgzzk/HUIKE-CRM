package site.lgzzk.common.core.domain;


import java.util.HashMap;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

public class Result extends HashMap<String, Object> {

    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";

    public static final String SUCCESS_MSG = "ok";
    public static final String ERROR_MSG = "fail";


    private Result() {
    }

    private Result(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (data != null) super.put(DATA_TAG, data);
    }

    public static Result ok() {
        return Result.ok(SUCCESS_MSG);
    }

    public static Result ok(Object data) {
        return Result.ok(SUCCESS_MSG, data);
    }

    public static Result ok(String msg) {
        return Result.ok(msg, null);
    }

    public static Result ok(String msg, Object data) {
        return new Result(OK.value(), msg, data);
    }

    public static Result fail() {
        return Result.fail(ERROR_MSG);
    }

    public static Result fail(String msg) {
        return new Result(INTERNAL_SERVER_ERROR.value(), msg, null);
    }

    public static Result fail(String msg, int code) {
        return new Result(code, msg, null);
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
