package com.christer.myapicommon.exception;

import com.christer.myapicommon.common.ResultCode;
import lombok.Getter;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-13 15:57
 * Description:
 * 自定义异常工具类
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 异常状态码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResultCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(String message) {
        super(message);
        // -9999 自定义异常
        this.code = -9999;
    }

    public BusinessException(ResultCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public BusinessException(ResultCode errorCode, String message, Throwable e) {
        super(message, e);
        this.code = errorCode.getCode();
    }

}
