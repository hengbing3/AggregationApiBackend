package com.christer.myapiclientsdk.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.christer.myapiclientsdk.client.MyApiClient;
import com.christer.myapiclientsdk.exception.ApiException;
import com.christer.myapiclientsdk.exception.ErrorResponse;
import com.christer.myapiclientsdk.exception.ResultCode;
import com.christer.myapiclientsdk.request.BaseRequest;
import com.christer.myapiclientsdk.request.CurrentRequest;
import com.christer.myapiclientsdk.response.ResultResponse;
import com.christer.myapiclientsdk.service.BaseService;
import com.christer.myapiclientsdk.uitls.SignUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-21 21:30
 * Description:
 */
@Service
@Slf4j
@Getter
@Setter
public class BaseServiceImpl implements BaseService {

    private static final String gatewayHost = "xxx";

    private MyApiClient myApiClient;

    @Override
    public <T extends ResultResponse> T request(BaseRequest<T> request) throws ApiException {
        // 1.校验请求参数是否存在
        if (myApiClient == null || CharSequenceUtil.hasBlank(myApiClient.getAccessKey(), myApiClient.getSecretKey())) {
            throw new ApiException(ResultCode.NO_AUTH_ERROR, "请先配置密钥AccessKey/SecretKey");
        }
        final String path = request.getPath().trim();
        final String method = request.getMethod();
        if (!StringUtils.hasText(path)) {
            throw new ApiException(ResultCode.PARAMS_ERROR, "请求路径不存在！");
        }
        if (!StringUtils.hasText(method)) {
            throw new ApiException(ResultCode.PARAMS_ERROR, "请求方式不存在！");
        }
        // 2.获取响应实体类（通过反射）
        final Class<T> responseClass = request.getResponseClass();
        T res;
        try {
            res = responseClass.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            log.error("API请求异常：{0}", e);
            throw new ApiException("API 请求失败");
        }
        // 3.封装请求体
        HttpRequest httpRequest;
        switch (method) {
            case "GET":
                httpRequest = HttpRequest.get(buildUrlBuilder(request, path).toString());
                break;
            case "POST":
                httpRequest = HttpRequest.post(gatewayHost + path);
                break;
            default:
                throw new ApiException(ResultCode.OPERATION_ERROR, "不支持该请求方式！");
        }
        Map<String, Object> data = new HashMap<>();
        // 4.处理请求
        data = doRequest(request, httpRequest, data);
        // 5.返回数据
        res.setData(data);
        return res;
    }

    /**
     * 处理请求
     * @param request 基础request
     * @param httpRequest  http请求
     * @param data 返回数据
     * @return 封装好data的map
     * @param <T> {@link T}
     */
    private <T extends ResultResponse> Map<String, Object> doRequest(final BaseRequest<T> request, final HttpRequest httpRequest, Map<String, Object> data) {
        try (final HttpResponse response = httpRequest.addHeaders(buildHeaders(JSONUtil.toJsonStr(request), myApiClient))
                .body(JSONUtil.toJsonStr(request.getRequestParams()))
                .execute()) {

            final String body = response.body();
            if (response.getStatus() != 200) {
                ErrorResponse errorResponse = JSONUtil.toBean(body, ErrorResponse.class);
                data.put("errorMessage", errorResponse.getMessage());
                data.put("code", errorResponse.getCode());
            } else {
                data = parseJsonData(data, body);
            }
        } catch (Exception e) {
            throw new ApiException(ResultCode.SYSTEM_ERROR, "请求发生错误！");
        }
        return data;
    }

    private static Map<String, Object> parseJsonData(Map<String, Object> data, final String body) {
        try {
            // 尝试解析为JSON对象
            data = new Gson().fromJson(body, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            log.error("返回json 解析失败:{0}", e);
            // 解析失败，将body作为普通字符串处理
            data.put("value", body);
        }
        return data;
    }


    /**
     * 获取请求头
     *
     * @param body        请求体
     * @param myApiClient  api客户端
     * @return {@link Map}<{@link String}, {@link String}>
     */
    private Map<String, String> buildHeaders(String body, MyApiClient myApiClient) {
        Map<String, String> hashMap = new HashMap<>(4);
        hashMap.put("accessKey", myApiClient.getAccessKey());
        String encodedBody = SecureUtil.md5(body);
        hashMap.put("body", encodedBody);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtils.genSign(encodedBody, myApiClient.getSecretKey()));
        return hashMap;
    }

    private static <T extends ResultResponse> StringBuilder buildUrlBuilder(final BaseRequest<T> request, final String path) {
        final StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(gatewayHost);
        if (urlBuilder.toString().endsWith("/") && path.startsWith("/")) {
            urlBuilder.setLength(urlBuilder.length() - 1);
        }
        urlBuilder.append(path).append("?");
        final Map<String, Object> requestParams = request.getRequestParams();
        for (final Map.Entry<String, Object> entry : requestParams.entrySet()) {
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return urlBuilder;
    }

    @Override
    public <T extends ResultResponse> T request(MyApiClient myApiClient, BaseRequest<T> request) throws ApiException {
        checkApiClientConfig(myApiClient);
        return request(request);
    }

    private void checkApiClientConfig(MyApiClient myApiClient) {
        if (myApiClient == null && this.getMyApiClient() == null) {
            throw new ApiException(ResultCode.NO_AUTH_ERROR, "请先配置密钥AccessKey/SecretKey");
        }

        if (myApiClient != null && CharSequenceUtil.hasBlank(myApiClient.getAccessKey(), myApiClient.getSecretKey())) {
            this.setMyApiClient(myApiClient);
        }
    }
}
