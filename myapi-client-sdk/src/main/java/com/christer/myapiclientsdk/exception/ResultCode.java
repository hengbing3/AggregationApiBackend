package com.christer.myapiclientsdk.exception;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-13 15:58
 * Description:
 */
public enum ResultCode {
    /**
     * api状态码
     */
    SUCCESS(200, "ok"),
    /**
     * 请求参数错误
     */
    PARAMS_ERROR(40000, "请求参数错误"),
    /**
     * 未登录
     */
    NOT_LOGIN_ERROR(40100, "未登录"),
    /**
     * 无权限
     */
    NO_AUTH_ERROR(40101, "无权限"),
    /**
     * 请求数据不存在
     */
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    /**
     * 禁止访问
     */
    FORBIDDEN_ERROR(40300, "禁止访问"),
    /**
     * 系统错误
     */
    SYSTEM_ERROR(50000, "系统内部异常"),
    /**
     * 操作错误
     */
    OPERATION_ERROR(50001, "操作失败");

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private ResultCode(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
