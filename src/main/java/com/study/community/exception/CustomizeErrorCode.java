package com.study.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTIOM_NOT_FOUND(2001, "你找的问题不在了，要不要换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "未登陆不能进行评论，请先登陆"),
    SYS_ERROR(2004, "服务器冒烟了，要不然你稍后试试！！！CustomizeErrorCode"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了，要不换个试试"),
    CONTENT_IS_EMPTY(2007, "输入的内容不能为空")
    ;


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    //属性代表枚举的内容，要提供get方法给外面使用
    private String message;
    private Integer code;

    /**
     * 构造方法是给枚举自己用的
     *
     * @param code
     * @param message
     */
    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
