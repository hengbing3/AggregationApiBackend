package com.christer.myapicommon.common;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-13 16:12
 * Description:
 */
public class ResultBody {

    private ResultBody() {
    }
    /**
     * 请求成功返回
     * public和返回值间的<T>指定的这是一个泛型方法，这样才可以在方法内使用T类型的变量
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }


    public static <T> CommonResult<T> failed(Integer code, String message) {
        return new CommonResult<>(code, message, null);
    }

    public static <T> CommonResult<T> failed(ResultCode code, String message) {
        return new CommonResult<>(code.getCode(), message, null);
    }


    public static <T> CommonResult<T> failed(String msg, T data) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), msg, data);
    }

    public static <T> CommonResult<T> failed(ResultCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), null);
    }


    public static <T> CommonResult<T> failed(ResultCode errorCode, T data) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), data);
    }
}
