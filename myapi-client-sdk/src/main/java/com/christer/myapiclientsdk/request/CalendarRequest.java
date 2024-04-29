package com.christer.myapiclientsdk.request;

import com.christer.myapiclientsdk.enums.RequestMethodEnum;
import com.christer.myapiclientsdk.response.ResultResponse;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-29 22:09
 * Description:
 * 生成日历请求类
 */
public class CalendarRequest extends BaseRequest<ResultResponse> {
    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }

    @Override
    public String getPath() {
        return "/moyu";
    }

    @Override
    public Class<ResultResponse> getResponseClass() {
        return ResultResponse.class;
    }
}
