package com.bookStore.exception;

import com.bookStore.util.result.ResultCode;

/**
 * ClassName: DBUpdateException
 * Package: com.bookStore.exception
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/24 -23:34
 * @Version: v1.0
 */
public class DBOperateException extends RuntimeException{
    private Integer errorCode;
    private String msg;
    public DBOperateException(String msg){
        super(msg);
    }
    public DBOperateException(Integer errorCode, String msg){
        super(msg);
        this.errorCode=errorCode;
        this.msg=msg;
    }
    public DBOperateException(ResultCode resultCode){
        super(resultCode.getMessage());
        this.errorCode=resultCode.getCode();
        this.msg=resultCode.getMessage();
    }
}
