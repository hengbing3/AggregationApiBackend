package com.christer.myinterface.utils;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-25 22:40
 * Description:
 */
@Slf4j
public class RequestUtils {



    /**
     * get请求
     *
     * @param url url
     * @return {@link String}
     */
    public static String get(String url) {
        String body = HttpRequest.get(url).execute().body();
        log.info("【interface】：请求地址：{}，响应数据：{}", url, body);
        return body;
    }
}
