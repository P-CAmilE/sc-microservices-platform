package com.scmp.file.utils;

import com.scmp.domain.ResultVO;
import com.scmp.domain.exception.UserRepeatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        ResultVO<String> resultVO = null;
        HttpStatus status = HttpStatus.OK;
        if (exception instanceof MissingServletRequestParameterException){
            // 请求参数错误异常
            resultVO = new ResultVO<>(400,"请求参数错误",exception.toString());
            status = HttpStatus.BAD_REQUEST;
        } else {
            // 其他异常
            exception.printStackTrace();
            resultVO = new ResultVO<>(500,"服务器错误",exception.toString());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultVO, status != null ? status : HttpStatus.OK);
    }
}
