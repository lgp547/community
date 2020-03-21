package com.study.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTIOM_NOT_FOUND("你找的问题不在了，要不要换个试试?");

    @Override
    public String getMessage(){
        return message;
    }

    private String message;

    CustomizeErrorCode(String message){
        this.message = message;
    }
}
