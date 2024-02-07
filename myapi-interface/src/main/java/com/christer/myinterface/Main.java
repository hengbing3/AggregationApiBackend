package com.christer.myinterface;

import com.christer.myinterface.client.MyApiClient;
import com.christer.myinterface.model.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-06 16:20
 * Description:
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        String accessKey = "abin";
        String secretKey = "abcdefg";
        MyApiClient myApiClient = new MyApiClient(accessKey, secretKey);
        String name = myApiClient.getNameByGet("阿槟");
        log.info(name);

        String nameByPost = myApiClient.getNameByPost("阿槟 post");
        log.info(nameByPost);

        User user = new User();
        user.setUsername("阿槟json");
        String usernameByPost = myApiClient.getUsernameByPost(user);
        log.info(usernameByPost);
    }
}
