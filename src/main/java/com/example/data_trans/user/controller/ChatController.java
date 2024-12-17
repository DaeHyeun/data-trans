package com.example.data_trans.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @RequestMapping("/receiveMessage")
    public String receiveMessage(@RequestParam String message, @RequestParam String userId) {
        System.out.println(userId + " : " + message);
        return "메시지 수신 완료";
    }

    @RequestMapping("/open")
    public void openchat(@RequestParam String message){
        System.out.println(message);
    }
    @PostMapping("/sendFile")
    public String receiveFile( @RequestParam("file") MultipartFile file) {
        try {
            // 파일을 byte[]로 처리
            byte[] fileContent = file.getBytes();

            System.out.println("chat===================================================");
            System.out.println(file.getName());

            // 파일을 서버에 저장 (예: 특정 디렉토리에 저장)
            // 파일 이름 중복 처리: UUID를 이용해 파일 이름을 변경
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get("D:/download/" + fileName);

            Files.write(path, fileContent);  // 파일 저장

            // 로그 출력
            System.out.println("Received file: " + fileName);

            return "File received and saved successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to save the file";
        }
    }

}
