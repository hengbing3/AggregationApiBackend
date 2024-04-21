package com.christer.myapiclientsdk.service.impl;

import com.christer.myapiclientsdk.client.MyApiClient;
import com.christer.myapiclientsdk.exception.ApiException;
import com.christer.myapiclientsdk.request.BaseRequest;
import com.christer.myapiclientsdk.request.CurrentRequest;
import com.christer.myapiclientsdk.response.ResultResponse;
import com.christer.myapiclientsdk.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-21 21:30
 * Description:
 */
@Service
@Slf4j
public class BaseServiceImpl implements BaseService {

    @Override
    public <T extends ResultResponse> T request(BaseRequest<T> request) throws ApiException {
        final Class<T> responseClass = request.getResponseClass();
        T res;
        try {
            res = responseClass.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            log.error("API请求异常：{0}", e);
            throw new ApiException("API 请求失败");
        }
        // 1.判断请求类型
        // 2.获取响应体
        // 3.拼装请求参数
        // 4.根据请求类型发送请求
        // 5. 根据返回参数 转为 hashMap 并封装参数
        return res;
    }

    @Override
    public <T extends ResultResponse> T request(MyApiClient myApiClient, BaseRequest<T> request) throws ApiException {
        checkApiClientConfig(myApiClient);
        return request(request);
    }

    private void checkApiClientConfig(MyApiClient myApiClient) {

    }
}
