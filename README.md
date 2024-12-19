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

https://tychejin.tistory.com/424
 
-- ActiveMQ 사용법 --

Doker에서 
docker run -p 61616:61616 -p 8161:8161 rmohr/activemq

DataTransApplication 실행

User 멀티 인스턴스로 실행(2개)

각자의 이름과 받는사람 이름이 일치해야 전송됨 




