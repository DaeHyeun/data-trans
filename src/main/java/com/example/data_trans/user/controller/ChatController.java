package com.example.data_trans.user.controller;

import com.example.data_trans.main.util.FileChecksumUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.security.NoSuchAlgorithmException;

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
    public String receiveFile( @RequestParam("file") MultipartFile file,  @RequestParam("checksum") String checksum) {
        try {
            // 파일을 byte[]로 처리
            byte[] fileContent = file.getBytes();

            // 파일을 서버에 저장 (예: 특정 디렉토리에 저장)
            // 파일 이름 중복 처리: UUID를 이용해 파일 이름을 변경
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get("D:/download/" + fileName);

            // 파일 저장
            Files.write(path, fileContent);

            // 서버에서 파일의 체크섬 계산
            String fileChecksum = FileChecksumUtil.calculateChecksum(path.toFile(), "SHA-256");
            System.out.println("Calculated checksum: " + fileChecksum);

            // 클라이언트에서 보낸 체크섬과 비교
            if (!fileChecksum.equals(checksum)) {
                // 체크섬이 일치하지 않으면 파일을 삭제하고 에러 메시지 반환
                Files.delete(path);
                return "Checksum mismatch. File is corrupted or altered.";
            }

            // 체크섬이 일치하면 성공 메시지 반환
            System.out.println("File received and saved successfully: " + fileName);
            return "File received and saved successfully";
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Failed to save the file";
        }
    }

}
