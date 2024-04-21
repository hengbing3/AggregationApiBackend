package com.christer.myapiclientsdk.request;

import cn.hutool.json.JSONUtil;
import com.christer.myapiclientsdk.response.ResultResponse;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-21 17:28
 * Description:
 * 抽象请求类
 */
@Setter
public abstract class BaseRequest<T extends ResultResponse> {

    private Map<String, Object> requestParams = new HashMap<>();

    /**
     * get方法
     *
     * @return {@link }
     */
    public abstract String getMethod();

    /**
     * 获取路径
     *
     * @return {@link String}
     */
    public abstract String getPath();

    /**
     * 获取响应类
     *
     * @return {@link Class}<{@link T}>
     */
    public abstract Class<T> getResponseClass();

    @JsonAnyGetter
    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

}
