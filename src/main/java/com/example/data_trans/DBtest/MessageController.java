package com.example.data_trans.DBtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/send/messages")
    public void sendMessages() {
        try {
            messageService.sendMessagesToApi();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
