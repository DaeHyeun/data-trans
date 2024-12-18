package com.example.data_trans.service;
import com.example.data_trans.model.MessageDto;
import jakarta.jms.ObjectMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageTopicService {

    private final JmsTemplate jmsTemplateTopic;

    @Autowired
    private JmsListenerEndpointRegistry registry;
    @Autowired
    private JmsListenerContainerFactory<?> containerFactoryTopic;

    // 메시지 전송
    public void sendMessageTopic(String topicName, MessageDto messageDto) {
        log.info("Message sent to topic: {}", messageDto.toString());
        jmsTemplateTopic.convertAndSend(topicName, messageDto);

    }

    // 동적 토픽 리스너 추가
    public void addDynamicTopicListener(String topicName) {
        // 동적 토픽에 대해 새로운 엔드포인트 생성
        SimpleJmsListenerEndpoint endpoint = createDynamicListenerEndpoint(topicName);

        // 컨테이너를 등록하여 리스닝을 시작
        registry.registerListenerContainer(endpoint, containerFactoryTopic);
        log.info("Dynamic listener added for topic: {}", topicName);
    }

    // 동적 토픽 리스너 엔드포인트 생성
    private SimpleJmsListenerEndpoint createDynamicListenerEndpoint(String topicName) {
        // SimpleJmsListenerEndpoint 사용하여 동적 토픽 리스너 엔드포인트 생성
        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
        endpoint.setDestination(topicName);  // 동적으로 토픽 설정
        endpoint.setId(topicName);  // 엔드포인트 ID 설정 (여기서 topicName을 ID로 사용)
        endpoint.setMessageListener(message -> {
            try {
                if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    MessageDto messageDto = (MessageDto) objectMessage.getObject(); // MessageDto로 캐스팅
                    log.info("Received Message from Topic {}: {}", topicName, messageDto.toString());
                } else {
                    log.error("Received message is not an ObjectMessage: {}", message);
                }
            } catch (Exception e) {
                log.error("Error processing message from topic: {}", topicName, e);
            }
        });

        return endpoint;
    }


}