package com.example.data_trans.USER1;


import com.example.data_trans.DataTransApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class UserServer {
    public static void main(String[] args) {
        // 두 번째 애플리케이션 포트 8081 설정
        Map<String, Object> properties = new HashMap<>();
        properties.put("server.port", 8081);

        SpringApplication app = new SpringApplication(DataTransApplication.class);
        app.setDefaultProperties(properties);
        app.run(args);

        System.out.println("메인 서버 실행 - 포트 8081");

    }
}
