# data-trans

rest-api, gRPC, MQ 연습

규칙
1. 메인 서버 1개
2. 사용자 서버 3개(user1, user2, user3)
3. 메세지 전송을 기반으로 시작
4. 특정 1인에게 메세지 전송
5. 파일 전송(크기 조절로 동기,비동기 비교 큰거 먼저 보내서 도착하는 순서 비교, checkSum계산)

개별 추가
1. MQ시 오프라인에서 온 메세지 가지고 오기 
2. gRPC시 사용자끼라 요청해서 콜 

rest-api -> Grpc -> MQ --> 세가지를 섞어가면서 진행 

각 설치 과정은 README에 기록

restAPI
