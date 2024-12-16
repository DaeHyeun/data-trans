package com.example.data_trans.DBtest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void sendMessagesToApi() throws Exception {
        // DB에서 메시지 리스트 가져오기
        List<Message> messages = messageRepository.findAll();
        for (Message me : messages ){
            System.out.println(me.getTitle());
        }

    }

}
