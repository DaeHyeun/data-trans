package com.example.data_trans.queue;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {
    private String queueName; // 큐 이름 추가
    private String title;     // 메시지 제목
    private String content;   // 메시지 내용
}
