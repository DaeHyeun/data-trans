package com.example.data_trans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class DataTransApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DataTransApplication.class);
		app.setDefaultProperties(Map.of("server.port", 8080));  // 포트 8080 설정
		app.run(args);

		System.out.println("메인 서버 실행 - 포트 8080");
	}
}
