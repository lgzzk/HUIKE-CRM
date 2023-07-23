package site.lgzzk.framework.web.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.lgzzk.common.core.domain.Result;
import site.lgzzk.common.exception.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public Result customException(CustomException e){
        return Result.fail(e.getMessage());
    }

}
