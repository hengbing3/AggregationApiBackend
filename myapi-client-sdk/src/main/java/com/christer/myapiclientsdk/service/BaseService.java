package com.christer.myapiclientsdk.service;

import cn.hutool.http.HttpResponse;
import com.christer.myapiclientsdk.client.MyApiClient;
import com.christer.myapiclientsdk.exception.ApiException;
import com.christer.myapiclientsdk.request.BaseRequest;
import com.christer.myapiclientsdk.response.ResultResponse;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-21 21:30
 * Description:
 */
public interface BaseService {


    /**
     * 通用请求
     *
     * @param request 要求
     * @return {@link HttpResponse}
     * @throws ApiException 业务异常
     */

    <T extends ResultResponse> T request(BaseRequest<T> request) throws ApiException;

    /**
     * 通用请求
     *
     * @param myApiClient api客户端
     * @param request     要求
     * @return {@link T}
     * @throws ApiException 业务异常
     */
    <T extends ResultResponse> T request(MyApiClient myApiClient, BaseRequest<T> request) throws ApiException;

    /**
     * 生成摸鱼日历
     * @return ResultResponse
     */
     ResultResponse generateCalendar();
}
