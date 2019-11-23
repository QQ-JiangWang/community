package com.longrise.community.exception;

public enum CustomizeErrorCode implements  ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你的问题不在了，要不要换个试试？"),
    ;
    private String messger;
    private Integer code;
    @Override
    public String getMessger() {
        return messger;
    }

    @Override
    public Integer getCode() {
        return code;
    }
    CustomizeErrorCode(Integer code,String messger){
        this.code = code;
        this.messger = messger;
    }
}
