package com.scmp.domain;

/**
 * @author Coconut Tree
 * 统一返回结果中的 response_code
 */
public enum CodeEnum {
    SUCCESS(0),
    ERROR(1);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
