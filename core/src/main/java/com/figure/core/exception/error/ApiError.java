package com.figure.core.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ApiError {
    private int status;//状态码
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;//时间戳
    private String message;//错误提示
    private String debugMessage;//调试信息
    private List<ApiSubError> subErrors;//子错误消息

    private ApiError() {
        timestamp = new Date();
    }

    public ApiError(int status) {
        this();
        this.status = status;
    }

    ApiError(int status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(int status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
