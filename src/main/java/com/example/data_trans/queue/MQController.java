package com.example.data_trans.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mq")
@RequiredArgsConstructor
public class MQController {

    private final QueueService queueService;

    // 큐 이름과 메시지를 함께 받는 엔드포인트
    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageDto messageDto) {
        try {
            queueService.sendMessageQueue(messageDto); // 큐 이름과 메시지를 함께 보내는 메서드 호출
            return "Message sent to the queue: " + messageDto.getQueueName() + " successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending message to the queue.";
        }
    }

    // 메시지 수신 (consumer)
    @PostMapping("/receive")
    public String receiveMessage(@RequestParam String queueName) {
        try {
            String message = queueService.consumeMessageQueue(queueName); // 큐 이름을 이용해 메시지 수신
            return message != null ? message : "No message received.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error receiving message from the queue.";
        }
    }

}
