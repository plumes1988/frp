package com.figure.core.exception.handler;

import com.figure.core.constant.HttpStatusConstant;
import com.figure.core.exception.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> handelException(
            Exception ex) {
        ex.printStackTrace();
        log.error("An error occurred", ex);
        ApiError apiError = new ApiError(HttpStatusConstant.UNKONW_ERROR_CODE,HttpStatusConstant.UNKONW_ERROR,ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

}
