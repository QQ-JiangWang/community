package com.longrise.community.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;
    public CustomizeException(ICustomizeErrorCode iCustomizeErrorCode){
        this.message = iCustomizeErrorCode.getMessage();
        this.code = iCustomizeErrorCode.getCode();
    }
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
