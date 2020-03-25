package com.study.community.exception;

public class CustomizeException extends RuntimeException{
    //要把消息显示出来的
    private String message;

    @Override
    public String getMessage(){
        return message;
    }

    public CustomizeException(String message) {
        this.message = message;
    }

    /**
     * ----------------------------------分割线-----------------------------
     *  这个语句是为了捕捉自定义的错误代码。
     *  实现少写代码
     *  赋值进来需要的
     */
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }



}
