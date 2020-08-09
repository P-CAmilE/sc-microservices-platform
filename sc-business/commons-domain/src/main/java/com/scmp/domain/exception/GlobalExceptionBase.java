package com.scmp.domain.exception;

public class GlobalExceptionBase extends RuntimeException {

    private int code;
    private String message;

    public GlobalExceptionBase(int code, String message) {
        super(message);
        this.code    = code;
        this.message = message;
    }

    public GlobalExceptionBase() {
        super("Undefined Exception");
        this.code    = 400;
        this.message = "Undefined Exception";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
