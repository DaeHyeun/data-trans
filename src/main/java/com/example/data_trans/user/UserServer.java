package com.example.data_trans.user;

import com.example.data_trans.DataTransApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class UserServer {
    public static void main(String[] args ) {
        // Scanner 객체를 사용하여 사용자 입력을 받음
        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 ID와 포트를 입력받음
        System.out.print("이름: ");
        String userId = scanner.nextLine();

        System.out.print("포트: ");
        int port = scanner.nextInt();

        // Spring Boot 애플리케이션 실행
        SpringApplication app = new SpringApplication(UserServer.class);
        app.setDefaultProperties(Map.of("server.port", port));  // 포트 설정
        app.run(args);

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 요청 헤더 준비
        HttpHeaders headers = new HttpHeaders();

        // 초기 서버 요청 (사용자 ID와 포트)
        sendInitialRequest(restTemplate, headers, userId, port);

        // 메시지 보내는 부분 반복
        while (true) {
            // 사용자로부터 메시지 입력 받기
            System.out.print("메시지 입력: ");
            String message = scanner.nextLine();

            // 'exit' 입력 시 루프 종료
            if ("exit".equalsIgnoreCase(message)) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if ("유저".equals(message)) {
                // 유저 목록을 요청하는 부분
                getUsersList(restTemplate, headers);
            } else {
                // 일반 메시지 보내는 부분
                sendMessage(restTemplate, headers, message, userId);
            }
        }

        // Scanner 닫기
        scanner.close();
    }

    // 초기 서버 요청 (사용자 ID와 포트)
    private static void sendInitialRequest(RestTemplate restTemplate, HttpHeaders headers, String userId, int port) {
        String url = "http://localhost:8080/main/port?userId=" + userId + "&port=" + port;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println("서버 응답: " + response.getBody());
    }

    // 유저 목록을 가져오는 요청
    private static void getUsersList(RestTemplate restTemplate, HttpHeaders headers) {
        String url = "http://localhost:8080/main/users";
        ResponseEntity<HashMap> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), HashMap.class);
        HashMap<String, Integer> users = response.getBody();

        if (users != null) {
            users.forEach((key, value) -> System.out.println(key + ": " + value));
        } else {
            System.out.println("응답 데이터가 없습니다.");
        }
    }

    // 일반 메시지를 서버로 보내는 요청
    private static void sendMessage(RestTemplate restTemplate, HttpHeaders headers, String message, String userId) {
        String url = "http://localhost:8080/main/message?message=" + message + "&userId=" + userId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println("서버 응답: " + response.getBody());
    }

}
