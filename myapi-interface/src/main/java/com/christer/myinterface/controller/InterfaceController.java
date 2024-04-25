package com.christer.myinterface.controller;


import com.christer.myapiclientsdk.response.ResultResponse;
import com.christer.myinterface.utils.RequestUtils;
import com.christer.myinterface.utils.ResponseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-25 22:38
 * Description:
 */
@RestController
public class InterfaceController {

    /**
     * 生成摸鱼日历
     * @return 响应数据
     */
    @GetMapping("/moyu")
    public ResultResponse generateCalendar() {
        final String json = RequestUtils.get("https://api.vvhan.com/api/moyu?type=json");
        ResultResponse baseResponse = new ResultResponse();
        baseResponse.setData(ResponseUtils.responseToMap(json));
        return baseResponse;
    }
}
