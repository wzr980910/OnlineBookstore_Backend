package com.bookStore.entity;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/13/10:04
 * @Description:
 */
public enum CodeNumEntity implements CodeNum{
    SUCCESS("200","请求成功"),
    FAIL("400","操作失败"),
    DATABASE_ERROR("301","数据库查询错误"),
    USER_ERROR("302","用户名或密码错误"),
    USER_EXISTS("303","用户已存在"),
    SERVER_ERROR("500","内部服务器错误");

    private String code;
    private String message;

    CodeNumEntity(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
