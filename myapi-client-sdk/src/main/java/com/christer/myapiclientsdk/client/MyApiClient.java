package com.christer.myapiclientsdk.client;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.christer.myapiclientsdk.model.User;
import com.christer.myapiclientsdk.uitls.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MyApiClient {

    private final String accessKey;

    private final String secretKey;

    public static final String GATEWAY_HOST = "http://localhost:8090";

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
        String result = HttpUtil.get(GATEWAY_HOST + "/api/name/", hashMap);
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
        String result = HttpUtil.post(GATEWAY_HOST + "/api/name/", paramMap);
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
        final HttpRequest httpRequest = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .addHeaders(getHeaderMap(jsonStr))
                .charset(StandardCharsets.UTF_8)
                .body(jsonStr);
        String result = "";
        try (final HttpResponse httpResponse = httpRequest.execute()) {
            log.info("响应状态码:{}", httpResponse.getStatus());
            result = httpResponse.body();
            if (httpResponse.getStatus() != 200) {
                result = "服务器内部错误:" + extractErrorMessage(result);
            }
            log.info(result);
        } catch (Exception e) {
            log.error("响应发生异常:", e);
            throw new RuntimeException("响应失败:" + e.getMessage());
        }
        return result;
    }

    public String extractErrorMessage(String html) {
        Document doc = Jsoup.parse(html);
        // 尝试根据具体的HTML结构来提取错误信息
        Element errorElement = doc.select("div[style='white-space:pre-wrap;']").first();
        if (errorElement != null) {
            // 返回错误信息文本
            return errorElement.text();
        } else {
            // 如果找不到指定元素，返回默认错误信息或进行其他处理
            return "未能解析错误信息" + html;
        }
    }
}