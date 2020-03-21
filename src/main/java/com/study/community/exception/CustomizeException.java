package com.study.community.exception;

public class CustomizeException extends RuntimeException{
    //要把消息显示出来的
    private String message;

    //赋值进来需要的
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }

}
