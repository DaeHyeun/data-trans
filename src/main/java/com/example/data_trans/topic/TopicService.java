package com.example.data_trans.topic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicService {
    private final JmsTemplate jmsTemplate;
    private final ActiveMQConnectionFactory connectionFactory;
    private final Map<String, MessageListenerContainer> listenerContainers = new HashMap<>();

    // 동적으로 Topic 구독을 시작하는 메서드
    public void subscribeToTopic(String topicName) {
        try {
            if (!listenerContainers.containsKey(topicName)) {
                SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
                container.setDestinationName(topicName);
                container.setConnectionFactory(connectionFactory);
                container.setMessageListener(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            // 메시지를 처리하는 로직 (예: 메시지를 로그에 기록)
                            if (message instanceof TextMessage) {
                                String messageText = ((TextMessage) message).getText();
                                log.info("Received message from topic {}: {}", topicName, messageText);
                            }
                        } catch (JMSException e) {
                            log.error("Error processing message", e);
                        }
                    }
                });
                container.start(); // Topic을 구독 시작
                listenerContainers.put(topicName, container);
                log.info("Subscribed to topic: " + topicName);
            }
        } catch (Exception e) {
            log.error("Error subscribing to topic", e);
        }
    }

    // Topic 구독 해제
    public void unsubscribeFromTopic(String topicName) {
        MessageListenerContainer container = listenerContainers.get(topicName);
        if (container != null) {
            container.stop(); // 구독 중지
            listenerContainers.remove(topicName);
            log.info("Unsubscribed from topic: " + topicName);
        }
    }

    // 토픽에 메시지 전송
    public void sendMessageToTopic(String topicName, String messageContent) {
        try {
            // 메시지를 생성하고 토픽으로 전송
            jmsTemplate.convertAndSend(topicName, messageContent);
            log.info("Message sent to topic: {} with content: {}", topicName, messageContent);
        } catch (Exception e) {
            log.error("Error sending message to topic", e);
        }
    }


}
