package com.example.data_trans.main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class MainController {

    HashMap<String, Integer> users = new HashMap<>();

    @RequestMapping("/port")
    public String port (@RequestParam String userId, @RequestParam int port){
        users.put(userId,port);
        System.out.println(userId);
        System.out.println(port);
        return "연결 완료";
    }

    @RequestMapping("/message")
    public String message (@RequestParam String message, @RequestParam String userId){
        String url = "";
        for (Map.Entry<String, Integer> entry : users.entrySet()) {
            // key와 value를 각각 처리
            String key = entry.getKey();
            int value = entry.getValue();
            url = "http://localhost:"+value+"/chat/receiveMessage?message=" + message +"&userId="+userId;
            // RestTemplate을 사용하여 GET 요청 보내기
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
        }
        return "메세지 수신 완료";
    }

    @RequestMapping("/users")
    public HashMap<String, Integer> getUsers (){
        return users;
    }
}
