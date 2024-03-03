package com.christer.project;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SpringbootApiBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testAddFlowGroup() {
         Map<String, Object> params = new HashMap<>();
         params.put("deptId", "1");
         params.put("deptName", "API开放平台");
         params.put("type", "开放");
        HttpResponse httpResponse = HttpRequest.post("http://localhost:6699/flowable-ui/userGroup")
                .form(params)
                .charset(StandardCharsets.UTF_8)
                .execute();
        Assertions.assertEquals(200,httpResponse.getStatus());
    }

}
