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
public class BizException extends RuntimeException{
    private Integer errorCode;
    private String msg;
    public BizException(String msg){
        super(msg);
    }
    public BizException(Integer errorCode, String msg){
        super(msg);
        this.errorCode=errorCode;
        this.msg=msg;
    }
    public BizException(ResultCode resultCode){
        super(resultCode.getMessage());
        this.errorCode=resultCode.getCode();
        this.msg=resultCode.getMessage();
    }
}
