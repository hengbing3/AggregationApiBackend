package com.christer.myapiclientsdk.exception;

import lombok.Data;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-13 16:45
 * Description:
 * 错误返回体
 */
@Data
public class ErrorResponse {
    /**
     * 错误信息
     */
    private String message;
    /**
     * 错误状态码
     */
    private int code;
}
