package com.bookStore.advice;

import com.bookStore.exception.BizException;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Configuration
@Slf4j
public class GlobalExceptionAdvice {

    //方法请求参数校验异常

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder errorInfo = new StringBuilder();
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

    // 对象属性校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        StringBuilder errorInfo = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            errorInfo.append(violation.getMessage());
        }
        RestResult restResult = new RestResult();
        restResult.setMessage(errorInfo.toString());
        restResult.setCode(ResultCode.PARAM_ERROR.getCode());
        return restResult;
    }

    //参数不合法异常
    @ExceptionHandler(IllegalArgumentException.class)
    public RestResult handleIllegalArgumentException(HttpRequest request, IllegalArgumentException ex) {
        URI uri = request.uri();
        log.info(uri.toString(), ex);
        return new RestResult(ResultCode.PARAM_TYPE_BIND_ERROR, uri);
    }

    //自定义异常
    @ExceptionHandler(BizException.class)
    public RestResult handleBizException(HttpRequest request, BizException ex) {
        URI uri = request.uri();
        log.info(uri.toString(), ex);
        return new RestResult(ex.getResultCode(), uri);
    }

    //加密、解密、安全验证异常
    @ExceptionHandler(GeneralSecurityException.class)
    public RestResult handleGeneralSecurityException(HttpRequest request, GeneralSecurityException ex) {
        URI uri = request.uri();
        log.info(uri.toString(), ex);
        return new RestResult(ex.getMessage(), uri);
    }

    //输入输出异常
    @ExceptionHandler(IOException.class)
    public RestResult handleIOException(HttpRequest request, IOException ex) {
        URI uri = request.uri();
        log.info(uri.toString(), ex);
        return new RestResult(ex.getMessage(), uri);
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    public RestResult handleNullPointerException(HttpRequest request, NullPointerException ex) {
        URI uri = request.uri();
        log.info(uri.toString(), ex);
        return new RestResult(ex.getMessage(), uri);
    }
}
