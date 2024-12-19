package com.example.data_trans.topic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    // 동적으로 Topic 구독
    @PostMapping("/subscribe")
    public String subscribeToTopic(@RequestParam String topicName) {
        try {
            topicService.subscribeToTopic(topicName); // 동적으로 Topic 구독
            return "Subscribed to topic: " + topicName;
        } catch (Exception e) {
            log.error("Error subscribing to topic", e);
            return "Error subscribing to topic.";
        }
    }

    // 동적으로 Topic 구독 해제
    @PostMapping("/unsubscribe")
    public String unsubscribeFromTopic(@RequestParam String topicName) {
        try {
            topicService.unsubscribeFromTopic(topicName); // 동적으로 Topic 구독 해제
            return "Unsubscribed from topic: " + topicName;
        } catch (Exception e) {
            log.error("Error unsubscribing from topic", e);
            return "Error unsubscribing from topic.";
        }
    }

    // 토픽에 메시지 전송
    @PostMapping("/send")
    public String sendMessage(@RequestParam String topicName, @RequestBody String messageContent) {
        try {
            topicService.sendMessageToTopic(topicName, messageContent);
            return "Message sent to topic: " + topicName;
        } catch (Exception e) {
            log.error("Error sending message to topic", e);
            return "Error sending message to topic.";
        }
    }
}
