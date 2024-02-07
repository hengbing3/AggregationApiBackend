package com.christer.myinterface;

import com.christer.myapiclientsdk.client.MyApiClient;
import com.christer.myapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MyInterfaceApplicationTests {

    @Resource
    private MyApiClient myApiClient;
    @Test
    void contextLoads() {
        String result1 = myApiClient.getNameByGet("binGet");
        User user = new User();
        user.setUsername("我是阿槟");
        String result2 = myApiClient.getNameByPost("阿宾post");
        String result3 = myApiClient.getUsernameByPost(user);


        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

}
