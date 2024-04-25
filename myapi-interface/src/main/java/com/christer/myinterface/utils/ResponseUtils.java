package com.christer.myinterface.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-25 22:42
 * Description:
 */
public class ResponseUtils {

    public static Map<String, Object> responseToMap(String response) {
        return new Gson().fromJson(response, new TypeToken<Map<String, Object>>() {
        }.getType());
    }
}
