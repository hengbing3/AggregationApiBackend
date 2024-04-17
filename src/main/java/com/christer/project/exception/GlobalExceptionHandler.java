package com.christer.project.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.christer.project.common.CommonResult;
import com.christer.project.common.ResultBody;
import com.christer.project.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Christer
 * @version 1.0
 * @date 2023-09-04 21:48
 * Description:
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public CommonResult<Void> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException: " + e.getMessage(), e);
        return ResultBody.failed(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(com.christer.myapicommon.exception.BusinessException.class)
    public CommonResult<Void> commonBusinessExceptionHandler(com.christer.myapicommon.exception.BusinessException e) {
        log.error("BusinessException: " + e.getMessage(), e);
        return ResultBody.failed(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public CommonResult<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultBody.failed(ResultCode.FAILED, e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public CommonResult<Void> bindExceptionHandler(BindException e) {
        log.error("BindException", e);
        final BindingResult result = e.getBindingResult();
        final StringBuilder errBuilder = new StringBuilder();
        for (FieldError fieldError : result.getFieldErrors()) {
            errBuilder.append(" ").append(fieldError.getDefaultMessage());
        }
        return ResultBody.failed(ResultCode.PARAMS_ERROR, errBuilder.toString());
    }

    /**
     * 未登录异常
     * @param e
     * @return
     */
    @ExceptionHandler(NotLoginException.class)
    public CommonResult<String> notLoginException(NotLoginException e) {
        // 打印堆栈信息
        log.error("未登录错误:", e);
        return ResultBody.failed(ResultCode.TOKEN_FAILED, "请进行登录操作");
    }
}
