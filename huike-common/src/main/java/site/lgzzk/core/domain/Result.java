package site.lgzzk.core.domain;

import java.util.HashMap;

import static site.lgzzk.constant.HttpStatus.ERROR;
import static site.lgzzk.constant.HttpStatus.SUCCESS;

public class Result extends HashMap<String, Object> {

    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";

    public static final String SUCCESS_MSG = "操作成功";
    public static final String ERROR_MSG = "操作失败";


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
        return new Result(SUCCESS, msg, data);
    }

    public static Result fail() {
        return Result.fail(ERROR_MSG);
    }

    public static Result fail(String msg) {
        return new Result(ERROR, msg, null);
    }

}
