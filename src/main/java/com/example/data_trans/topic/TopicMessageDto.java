package com.example.data_trans.topic;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopicMessageDto {
    private String topicName;  // Topic 이름 추가
    private String title;      // 메시지 제목
    private String content;    // 메시지 내용
}
