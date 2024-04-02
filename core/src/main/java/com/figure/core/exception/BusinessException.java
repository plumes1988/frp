package com.figure.core.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class BusinessException extends RuntimeException {

    private Integer code;
    private String message;

    public BusinessException() {
    }

    public BusinessException(int code, String errmsg) {
        super(errmsg);
        this.code = code;
        this.message = errmsg;
        log.error("An error occurred BusinessException", "{" +
                "\"code\":" + code +
                ", \"message\":\"" + message + "\"" + "}");
    }

    public BusinessException(String message, Object... params) {
        super(String.format(message, params));
    }

    public BusinessException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"message\":\"" + message + "\"" + "}";
    }


    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
