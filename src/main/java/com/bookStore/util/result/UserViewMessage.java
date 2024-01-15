package com.bookStore.util.result;

/**
 *
 * @author Smile
 * @version 1.0
 * @date 2020/12/29 17:01
 */
public enum UserViewMessage {

    UNKNOWN_ERROR(200,"未知错误，我们的工作人员正在全力排查，请您稍后再试"),
    FILE_UPLOAD_FAILURE(200,"上传失败，请稍后再试"),
    OPERATION_SUCCESS(200,"操作成功"),
    OPERATION_FAILURE(200,"操作失败"),
    LOGIN_SUCCESS(200,"登录成功"),
    LOGIN_FAILURE(200,"登陆失败"),
    NETWORK_ERROR(200,"网络问题，请稍后再试"),
    DATA_ERROR(200,"数据异常，请您重新提交"),
    LOGON_EXPIRATION(200,"您尚未登录或者登陆已过期，请您重新登录后再试"),
    DATA_NOT_EXIST(200,"数据不存在"),
    NO_ACCESS(200,"您暂无权限访问，请联系上级管理员"),
    LOGIN_STATUS_EXCEPTION(200,"您的登录状态异常，请重新登录后再试");

    private Integer code;
    private String message;

    UserViewMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
