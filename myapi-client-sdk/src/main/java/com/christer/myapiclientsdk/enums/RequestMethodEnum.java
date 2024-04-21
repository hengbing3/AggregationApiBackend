package com.christer.myapiclientsdk.enums;

import lombok.Getter;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-21 21:23
 * Description:
 */
@Getter
public enum RequestMethodEnum {
    /**
     * GET请求
     */
    GET("GET", "GET请求"),
    POST("POST", "POST请求");
    private final String text;
    private final String value;

    RequestMethodEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

}
