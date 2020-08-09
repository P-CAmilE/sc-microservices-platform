package com.scmp.domain.exception;

public class UserRepeatException extends GlobalExceptionBase {

    public UserRepeatException(int code, String message) {
        super(code, message);
    }

}
