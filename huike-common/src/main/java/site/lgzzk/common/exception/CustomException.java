package site.lgzzk.common.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {

	private Integer code;
    private String message;

    public CustomException(String message, Integer code) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
