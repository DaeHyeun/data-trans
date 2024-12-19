/*
package com.example.data_trans;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListenerService {
    // 메시지 리스너
    @JmsListener(destination = "#{topicName}", containerFactory = "containerFactoryTopic")
    public void receiveMessage(String message) {
        log.info("Received message from topic: " + message);
    }
}
*/
