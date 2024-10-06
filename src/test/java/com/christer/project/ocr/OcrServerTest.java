package com.christer.project.ocr;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-10-06 19:16
 * Description:
 */

public class OcrServerTest {

    public static void main(String[] args) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("img", "/Users/christer/Downloads/general_ocr_001.png");
        String jsonStr = JSONUtil.toJsonStr(hashMap);
        String post = HttpUtil.post("http://127.0.0.1:6666/ocr", jsonStr);
        System.out.println(post);
    }


}
