package com.bookStore.util.result;

/**
 *
 * @author Smile
 * @version 1.0
 * @date 2020/12/9 16:45
 */
public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(200,"成功"),
    FILE_UPLOAD_SUCCESS(2,"文件上传成功"),
    /* 未知异常：100-199 */
    UNKNOWN_ERROR(100,"未知错误"),
    OPERATION_FAILURE(101,"操作失败"),
    /* 参数错误：1001-1999 */
    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_BLANK(1002,"参数为空"),
    PARAM_TYPE_BIND_ERROR(1003,"参数类型错误"),
    PARAM_NOT_COMPLETE(1004,"参数缺失"),
    PARAM_ERROR(1005,"参数校验异常"),
    /* 用户错误：2001-2099*/
    USER_NOT_LOGGED_IN(2001,"用户未登录，访问的内容需要验证，请登录"),
    USER_LOGIN_ERROR(2002,"密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003,"账号已被禁用"),
    USER_NOT_EXIST(2004,"用户不存在"),
    USER_HAS_EXISTED(2005,"用户已存在"),
    USER_REPEAT_SUBMIT(2006,"数据重复提交"),
    USER_NO_ACCESS(2007,"用户无权限访问"),
    /* 服务器错误：3001-3099 */
    TOKEN_CANT_CREATE(3001,"token创建异常"),
    TOKEN_CANT_GENERATE_SIGNATURE(3002,"token签名生成异常"),
    TOKEN_EXPIRED(3003,"token已过期失效"),
    TOKEN_CANT_DECODE(3004,"token解码异常"),
    TOKEN_MISMATCH_ALGORITHM(3005,"token解码算法不匹配"),
    TOKEN_CANT_VERIFY_SIGNATURE(3006,"token签名验证异常"),
    TOKEN_INVALID_CLAIM(3007,"token载荷失效"),
    TOKEN_CANT_VERIFY(3008,"token验证异常"),
    TOKEN_EXCEPTION(3009,"token异常"),
    TOKEN_MISMATCH_AUDIENCE(3010,"token签发对象不匹配"),
    IP_ADDRESS_ERROR(3011,"IP错误，访问失败"),
    REQUEST_ERROR(3012,"请求错误"),
    FILE_UPLOAD_IO_ERROR(3013,"文件删除IO流错误"),
    FILE_UPLOAD_OSS_ERROR(3014,"文件上传OSS错误，未知错误"),
    DATA_INTEGRITY_VIOLATION(3015,"违反数据完整性"),
    NULL_POINT(3016,"空指针异常"),
    TOKEN_IS_NULL(3017,"token为空"),
    AUTHENTICATION_ERROR(3018,"身份验证异常"),
    AUTHORIZATION_ERROR(3019,"身份授权异常"),
    SERVLET_ERROR(3020,"Servlet异常"),
    SHIRO_ERROR(3021,"Shiro内部异常"),
    ASSERT_ERROR(3022,"断言异常"),
    FILE_UPLOAD_FAILURE(3013,"文件上传失败"),
    /* 数据库错误：4001-4099*/
    DB_INSERT_ERROR(4001,"数据库插入报错"),
    DB_DELETE_ERROR(4002,"数据库删除异常"),
    DB_UPDATE_ERROR(4003,"数据库修改异常"),
    DB_SELECT_ERROR(4004,"数据库查询异常"),
    DB_SELECT_ONE_ERROR(4005,"查询结果不唯一"),
    DB_ERROR(4000,"数据库出错"),
    /* 订单状态错误*/
    ORDER_STATUS_ERROR(5001,"订单状态异常"),
    /*库存不足*/
    STOCK_NUM_ZERO(6001,"库存不足");
    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
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
