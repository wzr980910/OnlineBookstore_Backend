package com.bookStore.config;

import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Configuration
public class GlobalExceptionHandler {

    //处理参数校验异常

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder errorInfo=new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            // 获取校验失败的字段名
            String fieldName = fieldError.getField();
            // 获取校验失败的提示信息
            String errorMessage = fieldError.getDefaultMessage();
            // 拼接错误信息
            errorInfo.append(fieldName).append(": ").append(errorMessage).append("; ");
        }
        RestResult restResult = new RestResult();
        restResult.setMessage(errorInfo.toString());
        return restResult;
    }
    // 其他异常处理方法...
    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        StringBuilder errorInfo=new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            errorInfo.append(violation.getMessage());
        }
        RestResult restResult = new RestResult();
        restResult.setMessage(errorInfo.toString());
        restResult.setCode(ResultCode.PARAM_ERROR.getCode());
        return restResult;
    }
}