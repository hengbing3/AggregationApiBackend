package com.christer.myinterface.controller;

import com.christer.myinterface.model.User;
import com.christer.myinterface.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-06 15:34
 * Description:
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/")
    public String getNameByGet(String name) {
        return "GET 你的名字是:" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是:" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
        // 1.拿到这五个我们可以一步一步去做校验,比如 accessKey 我们先去数据库中查一下
        // 从请求头中获取参数
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = null;
        try {
            body = URLDecoder.decode(request.getHeader("body"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        if (!accessKey.equals("abin") ) {
            throw new RuntimeException("无权限!");
        }
        // 随机数可以存储到Redis或者HashMap 校验是否被使用过，可以过5min清空一次数据,用于防止重放
        if (nonce.length() != 6) {
            throw new RuntimeException("无权限");
        }
        // 只对一分钟以内的请求有效 随机数和时间戳可以配合，防止重放
        if ((System.currentTimeMillis() / 1000) - Long.parseLong(timestamp) > 1 * 60) {
            throw new RuntimeException("无权限");
        }
        String genSign = SignUtils.genSign(body, "abcdefg");
        if (!sign.equals(genSign)) {
            throw new RuntimeException("无权限");
        }
        //
        return "POST 你的用户名是:" + user.getUsername();
    }
}
