package com.christer.myapiclientsdk.client;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.christer.myapiclientsdk.model.User;
import com.christer.myapiclientsdk.uitls.SignUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MyApiClient {

    private final String accessKey;

    private final String secretKey;

    public MyApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 使用GET方法从服务器获取名称信息
     *
     * @param name
     * @return
     */
    public String getNameByGet(String name) {
        // 可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        String result = HttpUtil.get("http://localhost:8213/api/name/", hashMap);
        log.info(result);
        return result;
    }

    /**
     * 使用POST方法从服务器获取名称信息
     *
     * @param name
     * @return
     */
    public String getNameByPost(String name) {
        final HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post("http://localhost:8213/api/name/", paramMap);
        log.info(result);
        return result;
    }

    private Map<String, String> getHeaderMap(String body) {
        // 创建一个新的 HashMap 对象
        Map<String, String> hashMap = new HashMap<>();
        // 将 "accessKey" 和其对应的值放入 map 中
        hashMap.put("accessKey", accessKey);
        // 注意：密钥不能直接发送
//        hashMap.put("secretKey", secretKey);
        // 生成长度为 6 位的随机字符串
        hashMap.put("nonce", RandomUtil.randomNumbers(6));
        // 请求体内容
        try {
            hashMap.put("body", URLEncoder.encode(body, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // 设置当前时间戳
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtils.genSign(body, secretKey));
        // 返回构造的请求头 map
        return hashMap;
    }

    /***
     * 使用post方法，发送user对象，并返回结果
     * @param user
     * @return
     */
    public String getUsernameByPost(User user) {
        String jsonStr = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8213/api/name/user")
                .addHeaders(getHeaderMap(jsonStr))
                .charset(StandardCharsets.UTF_8)
                .body(jsonStr)
                .execute();
        log.info("响应状态码:{}", httpResponse.getStatus());
        final String result = httpResponse.body();
        log.info(result);
        return result;
    }
}