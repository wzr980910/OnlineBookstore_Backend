package com.bookStore.util.result;

import java.io.Serializable;

/**
 *
 * @author Smile
 * @version 1.0
 * @date 2020/12/9 17:00
 */
public class RestResult implements Serializable {

    private Integer code;

    private String message;

    private Object data;


    public RestResult() {
    }

    /** 构造方法
     * 自定义信息返回，无状态码
     * @param message
     */
    public RestResult(String message) {
        this.message = message;
    }

    /** 构造方法
     * 自定义状态码、信息返回
     * @param code
     * @param message
     */
    public RestResult(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    /** 构造方法
     * 使用已经定义好的code返回，code里面已经定义好信息和状态码
     * @param code
     */
    public RestResult(ResultCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    /** 构造方法
     * 使用已经定义好的code返回，code里面已经定义好信息和状态码，并携带数据对象
     * @param code
     * @param data
     */
    public RestResult(ResultCode code, Object data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    /** 构造方法
     * 自定义信息返回，无状态码，并携带数据对象
     * @param message
     * @param data
     */
    public RestResult(String message,Object data) {
        this.message = message;
        this.data = data;
    }

    /** 返回成功
     * 已经定义好成功状态码和成功信息
     * 无需参数
     * @return
     */
    public static RestResult success(){
        return new RestResult(ResultCode.SUCCESS);
    }

    /** 返回成功
     * 已经定义好成功状态码和成功信息
     * 参数 Object
     * @param data
     * @return
     */
    public static RestResult success(Object data){
        return new RestResult(ResultCode.SUCCESS,data);
    }

    /** 返回成功
     * 使用定义好的code，自定义message
     * 参数 ResultCode, String
     * @param code
     * @param message
     * @return
     */
    public static RestResult success(ResultCode code, String message){
        RestResult result = new RestResult();
        result.setCode(code.getCode());
        result.setMessage(message);
        return result;
    }

    /** 返回成功
     * 使用定义好的code，自定义message、数据对象data
     * 参数 ResultCode, String, Object
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static RestResult success(ResultCode code,String message,Object data){
        RestResult result = new RestResult();
        result.setCode(code.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /** 返回失败
     * 自定义message返回
     * 参数 String
     * @param message
     * @return
     */
    public static RestResult failure(String message){
        return new RestResult();
    }

    /** 返回失败
     * 自定义code、message返回
     * 参数 Integer,String
     * @param code
     * @param message
     * @return
     */
    public static RestResult failure(Integer code, String message){
        return new RestResult(code,message);
    }
    /** 返回失败
     * 使用已经定义好的code返回，code里面已经定义好信息和状态码
     * 参数 ResultCode
     * @param code
     * @return
     */
    public static RestResult failure(ResultCode code){
        return new RestResult(code.getCode(),code.getMessage());
    }

    /** 返回失败
     * 使用已经定义好的code返回，code里面已经定义好信息和状态码，并携带数据对象
     * 参数ResultCode, Object
     * @param code
     * @param data
     * @return
     */
    public static RestResult failure(ResultCode code,Object data){
        return new RestResult(code,data);
    }

    /** 返回失败
     * 返回定义好的code,自定义message
     * 参数 ResultCode, String
     * @param code
     * @param message
     * @return
     */
    public static RestResult failure(ResultCode code,String message){
        RestResult result = new RestResult();
        result.setCode(code.getCode());
        result.setMessage(message);
        return result;
    }

    /** 返回失败
     * 使用定义好的code,自定义message,可携带数据对象
     * 参数 ResultCode, String, Object
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static RestResult failure(ResultCode code,String message,Object data){
        RestResult result = new RestResult();
        result.setCode(code.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
