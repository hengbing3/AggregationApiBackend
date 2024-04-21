package com.christer.myapiclientsdk.request;

import com.christer.myapiclientsdk.response.ResultResponse;
import lombok.Setter;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-21 21:16
 * Description:
 */
@Setter
public class CurrentRequest extends BaseRequest<ResultResponse> {

    private String method;

    private String path;

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Class<ResultResponse> getResponseClass() {
        return ResultResponse.class;
    }
}
