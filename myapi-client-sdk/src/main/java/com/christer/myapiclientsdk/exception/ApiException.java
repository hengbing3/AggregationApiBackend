package com.christer.myapiclientsdk.exception;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-21 21:47
 * Description:
 * API请求异常
 */
public class ApiException extends RuntimeException{

    private static final long serialVersionUID = 4718906671366494905L;
    /**
     * 异常状态码
     */
    private final int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(ResultCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public ApiException(String message) {
        super(message);
        // -9999 自定义异常
        this.code = -9999;
    }

    public ApiException(ResultCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public ApiException(ResultCode errorCode, String message, Throwable e) {
        super(message, e);
        this.code = errorCode.getCode();
    }
}
