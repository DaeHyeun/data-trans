package com.example.data_trans.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.lang.IllegalStateException;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueService {

    private final JmsTemplate jmsTemplateQueue;

    // procedure: 메시지를 큐에 전송
    public void sendMessageQueue(MessageDto messageDto) {
        String queueName = messageDto.getQueueName(); // MessageDto에서 큐 이름을 받음
        if (queueName == null || queueName.isEmpty()) {
            throw new IllegalStateException("Queue name must be set before sending a message.");
        }
        System.out.println("큐 프로시져 ");
        System.out.println("큐 이름 : " + queueName);
        System.out.println("메세지 제목 : " + messageDto.getTitle());
        System.out.println("메세지 내용 : " + messageDto.getContent());
        System.out.println();
        jmsTemplateQueue.convertAndSend(queueName, messageDto); // 지정된 큐에 메시지 전송
    }

    // consumer: 동적으로 큐에서 메시지를 받는 메서드
    public String consumeMessageQueue(String queueName) {
        if (queueName == null || queueName.isEmpty()) {
            throw new IllegalStateException("Queue name must be set before consuming messages.");
        }

        // 메시지를 수신하는 로직
        try {
            Message message = jmsTemplateQueue.receive(queueName); // 지정된 큐에서 메시지 받기
            System.out.println(message.getClass());

            System.out.println();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                return textMessage.getText(); // 메시지 내용 반환
            } else {
                return "Message received is not of type TextMessage.";
            }
        } catch (JMSException e) {
            e.printStackTrace();
            return "Error receiving message.";
        }
    }



}