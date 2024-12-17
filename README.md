# data-trans

rest-api, gRPC, MQ 연습

service interface
user interface

규칙
1. 메인 서버 1개
2. 사용자 서버 3개(user1, user2, user3, usser4)
3. 메세지 전송을 기반으로 시작
4. 특정 1인에게 메세지 전송
   특정 2인에게 메세지 전송
5. 파일 전송(checkSum계산)

개별 추가
1. MQ시 오프라인에서 온 메세지 가지고 오기 
2. gRPC시 사용자끼리 요청해서 콜 (메세지 전달하기)

rest-api -> MQ -> Grpc --> 세가지를 섞어가면서 진행 

각 설치 과정은 README에 기록

ActiveMQ
https://tychejin.tistory.com/424

docker run -p 61616:61616 -p 8161:8161 rmohr/activemq

implementation 'org.springframework.boot:spring-boot-starter-activemq'

spring.application.name=data-trans
server.port=8080

spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=C##ERP
spring.datasource.password=1234
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true

spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.user=admin
spring.activemq.password=admin
activemq.queue.name=sample-queue
activemq.topic.name=sample-topic

logging.level.root=info

test) 

post
http://localhost:8080/send/message/topic 
{
  "title" : "[Topic] Message Title",
  "content" : "[Topic] Message Content"
}

http://localhost:8080/send/message/queue
post
{
    "title" : "[Queue] Message Title ",
    "content" : "[Queue] Message Content"
}

com.example.chat
│
├── controller
│   ├── MessageController.java  // 메시지 발송 관련 REST API
│   └── ChatController.java    // 채팅방 관련 API (예: 채팅방 생성, 참가 등)
│
├── service
│   ├── MessageQueueService.java  // 큐 메시지 서비스
│   ├── MessageTopicService.java  // 토픽 메시지 서비스
│   └── UserService.java          // 사용자 관리 서비스 (로그인, 등록, 상태 관리)
│
├── model
│   ├── MessageDto.java          // 메시지 데이터 전송 객체
│   └── User.java                // 사용자 정보 모델 (예: 사용자 ID, 상태 등)
│
├── config
│   └── ActiveMQConfig.java      // ActiveMQ 설정 (예: 큐, 토픽 이름, 연결 설정 등)
│
└── repository
    └── UserRepository.java      // 사용자 저장소 (JPA 또는 MongoDB 사용 가능)