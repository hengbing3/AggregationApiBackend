package com.christer.myapiclientsdk.exception;

import lombok.Data;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-13 16:45
 * Description:
 */
@Data
public class ErrorResponse {
    private String message;
    private int code;
}
