package site.lgzzk.framework.web.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.lgzzk.common.core.domain.Result;
import site.lgzzk.common.exception.CustomException;

import static site.lgzzk.common.constant.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result accessDeniedException(AccessDeniedException e) {
        return Result.fail(e.getMessage(), FORBIDDEN);
    }

    @ExceptionHandler(CustomException.class)
    public Result customException(CustomException e) {
        if (e.getCode() == null) {
            return Result.fail(e.getMessage());
        }
        return Result.fail(e.getMessage(), e.getCode());
    }

}
