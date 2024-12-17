package com.example.data_trans.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @RequestMapping("/receiveMessage")
    public String receiveMessage(@RequestParam String message, @RequestParam String userId) {
        System.out.println(userId + " : " + message);
        return "메시지 수신 완료";
    }

    @RequestMapping("/open")
    public void openchat(@RequestParam String message){
        System.out.println(message);
    }

}
