package com.study.community.exception;

public class CustomizeException extends RuntimeException{

    private String message;
    private Integer code;

    /**
     *  这个语句是为了捕捉自定义的错误代码。
     *  实现少写代码
     *  赋值进来需要的
     */
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage(){
        return message;
    }

    public Integer getCode() {
        return code;
    }



}
