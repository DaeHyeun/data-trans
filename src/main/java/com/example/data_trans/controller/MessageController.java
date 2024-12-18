package com.example.data_trans.controller;

import com.example.data_trans.model.MessageDto;
import com.example.data_trans.service.MessageQueueService;
import com.example.data_trans.service.MessageTopicService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {

    private final MessageQueueService messageQueueService;
    private final MessageTopicService messageTopicService;

    // 메시지 전송 (POST 요청으로 메시지를 보내는 API)
    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageDto messageDto) {
        String topicName = "testTopic"; // 예시 토픽 이름
        messageTopicService.sendMessageTopic(topicName, messageDto);
        return "Message sent to topic: " + topicName;
    }

    // 동적 리스너 등록 (이 API는 토픽을 구독하도록 트리거함)
    @PostMapping("/subscribe")
    public String subscribeToTopic(@RequestParam String topicName) {
        messageTopicService.addDynamicTopicListener(topicName);
        return "Subscribed to topic: " + topicName;
    }

}