package com.example.data_trans.user;

import com.example.data_trans.DataTransApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
            String message = scanner.nextLine();
            String userlist = "";

            // 'exit' 입력 시 루프 종료
            if ("퇴장".equalsIgnoreCase(message)) {
                System.out.println("프로그램을 종료합니다.");
                exitchat(restTemplate, headers,userId);
                break;
            } else if ("유저".equals(message)) {
                // 유저 목록을 요청하는 부분
                getUsersList(restTemplate, headers);
            } else if ("선택".equals(message)) {
                getUsersList(restTemplate, headers);
                System.out.println("보낼유저 선택");
                userlist = scanner.nextLine();
                System.out.println("메세지");
                message = scanner.nextLine();
                selectmessage(restTemplate,headers,message,userId,userlist);
            } else if("파일".equals(message)){
                getUsersList(restTemplate, headers);
                System.out.println();
                System.out.print("전송할 파일 경로 입력 : " );
                String filePath = scanner.nextLine();
                System.out.print("누구에게 보낼 것인가");
                String recipientId = scanner.nextLine();
                String url = getfileurl(restTemplate,headers,userId,recipientId);

                System.out.println("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
                System.out.println(url);
                System.out.println("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
                sendFile(restTemplate, headers,url, filePath);
            }else {
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
    }

    //선택 전송
    private static void selectmessage (RestTemplate restTemplate, HttpHeaders headers, String message, String userId,String userlist){
        String url = "http://localhost:8080/main/selectuser?message=" + message + "&userId=" + userId +"&userlist="+userlist;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    //퇴장
    private static void exitchat (RestTemplate restTemplate, HttpHeaders headers,String userId){
        String url =  url = "http://localhost:8080/main/exit?userId=" + userId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    private static void sendFile(RestTemplate restTemplate, HttpHeaders headers, String url, String filePath) {


        // 파일을 읽어서 byte[]로 변환
        File file = new File(filePath);
        byte[] fileContent = new byte[(int) file.length()];
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStream.read(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("User SERVER ================================================================================");
        System.out.println(file.getName());

        // 파일을 포함한 데이터 생성
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(fileContent) {
            @Override
            public String getFilename() {
                return file.getName();
            }
        });

        // HTTP 헤더 설정
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // POST 요청 보내기
        try {

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            // 서버 응답 출력
            System.out.println("Server response: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Error during file transmission: " + e.getMessage());
        }
    }

    private static String getfileurl(RestTemplate restTemplate, HttpHeaders headers, String userId, String recipientId){
        String url = "http://localhost:8080/main/getfileurl?recipientId=" + recipientId + "&userId="+userId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return response.getBody();
    }

}
