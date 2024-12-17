package com.example.data_trans.main.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class MainController {

    HashMap<String, Integer> users = new HashMap<>();

    @RequestMapping("/port")
    public void port (@RequestParam String userId, @RequestParam int port){
        users.put(userId,port);
        System.out.println(userId);
        System.out.println(port);
        // 만약 users 맵에 더 이상 다른 사용자가 없다면, 예외처리
        if (users.size() == 0) {
            throw new RuntimeException("현재 채팅방에 다른 사용자가 없습니다.");  // 예외 던지기
        }

        // users 맵에 다른 사용자들이 있다면, 각 사용자에게 메시지를 보내기
        for (Map.Entry<String, Integer> entry : users.entrySet()) {
            // key와 value를 각각 처리
            String key = entry.getKey();
            int value = entry.getValue();
            String url = "http://localhost:" + value + "/chat/open?message=" + userId + "님이 채팅방에 입장하였습니다.";

            // RestTemplate을 사용하여 GET 요청 보내기
            RestTemplate restTemplate = new RestTemplate();
            try {
                String response = restTemplate.getForObject(url, String.class);
                System.out.println("Response from " + url + ": " + response);
            } catch (HttpServerErrorException e) {
                System.err.println("Server error while calling: " + url);
                System.err.println("Error message: " + e.getMessage());
                System.err.println("Response body: " + e.getResponseBodyAsString());
                // 필요하면 추가적인 로깅을 할 수 있습니다.
            } catch (Exception e) {
                System.err.println("An error occurred while sending request to: " + url);
                e.printStackTrace();
            }
        }


    }

    @RequestMapping("/exit")
    public void exitchat (@RequestParam String userId){
        users.remove(userId);
        // users 맵에 다른 사용자들이 있다면, 각 사용자에게 메시지를 보내기
        for (Map.Entry<String, Integer> entry : users.entrySet()) {
            // key와 value를 각각 처리
            String key = entry.getKey();
            int value = entry.getValue();
            String url = "http://localhost:" + value + "/chat/open?message=" + userId + "님이 채팅방을 나갔습니다.";

            // RestTemplate을 사용하여 GET 요청 보내기
            RestTemplate restTemplate = new RestTemplate();
            try {
                String response = restTemplate.getForObject(url, String.class);
                System.out.println("Response from " + url + ": " + response);
            } catch (HttpServerErrorException e) {
                System.err.println("Server error while calling: " + url);
                System.err.println("Error message: " + e.getMessage());
                System.err.println("Response body: " + e.getResponseBodyAsString());
                // 필요하면 추가적인 로깅을 할 수 있습니다.
            } catch (Exception e) {
                System.err.println("An error occurred while sending request to: " + url);
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/message")
    public void message (@RequestParam String message, @RequestParam String userId){
        String url = "";
        for (Map.Entry<String, Integer> entry : users.entrySet()) {
            // key와 value를 각각 처리
            String key = entry.getKey();
            int value = entry.getValue();
            url = "http://localhost:"+value+"/chat/receiveMessage?message=" + message +"&userId="+userId;
            // RestTemplate을 사용하여 GET 요청 보내기
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
        }
    }

    @RequestMapping("/users")
    public HashMap<String, Integer> getUsers (){
        return users;
    }

    @RequestMapping("/selectuser")
    public String selectusermessage (@RequestParam String message, @RequestParam String userId, @RequestParam String userlist){
        String[] strarr = userlist.split(",");
        String url = "";
        for (Map.Entry<String, Integer> entry : users.entrySet()) {
            // key와 value를 각각 처리
            String key = entry.getKey();
            int value = entry.getValue();
            for (int i = 0; i < strarr.length; i++) {
                if(strarr[i].equals(key)){
                    url = "http://localhost:"+value+"/chat/receiveMessage?message=" + message +"&userId="+userId;
                    RestTemplate restTemplate = new RestTemplate();
                    String response = restTemplate.getForObject(url, String.class);
                }
            }
        }
        return "메세지 수신 완료";
    }

    @PostMapping("/sendFile")
    public String sendFile(@RequestParam("file") MultipartFile file, @RequestParam String userId, @RequestParam String recipientId) {
        String url = "";
        System.out.println(file);
        System.out.println(userId);
        try {
            // recipientId에 URL 인코딩을 적용
            String encodedRecipientId = URLEncoder.encode(recipientId, StandardCharsets.UTF_8.toString());
            String encodedUserId = URLEncoder.encode(userId, StandardCharsets.UTF_8.toString());

            for (Map.Entry<String, Integer> entry : users.entrySet()) {
                // key와 value를 각각 처리
                String key = entry.getKey();
                int value = entry.getValue();
                if (encodedRecipientId.equals(key)) {
                    System.out.println("value =============================================" + value);
                    url = "http://localhost:" + value + "/sendFile/?userId=" + encodedUserId + "&recipientId=" + encodedRecipientId;
                    System.out.println(url);
                }
            }

            // 파일을 멀티파트로 전송하는 설정
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", file);

            System.out.println("Main===================================================");
            System.out.println(file.getName());
            System.out.println(file.getContentType());

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // 요청 엔티티 설정
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // RestTemplate을 이용하여 다른 서버로 POST 요청을 통해 파일을 전송
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // 다른 서버로부터 받은 응답을 반환
            return response.getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Error encoding URL parameters";
        }
    }

    @RequestMapping("/getfileurl")
    public String getfileurl (@RequestParam String recipientId , @RequestParam String userId){
        String url = "";
        for (Map.Entry<String, Integer> entry : users.entrySet()) {
            // key와 value를 각각 처리
            String key = entry.getKey();
            int value = entry.getValue();
            if (recipientId.equals(key)) {
                System.out.println("value =============================================" + value);
                url = "http://localhost:" + value + "/chat/sendFile";
                System.out.println(url);
            }
        }
        return url;
    }



}
