package com.example.data_trans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DataTransApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataTransApplication.class, args);
		// 첫 번째 애플리케이션 포트 8080 설정
		Map<String, Object> properties = new HashMap<>();
		properties.put("server.port", 8080);

		SpringApplication app = new SpringApplication(Main.class);
		app.setDefaultProperties(properties);
		app.run(args);

		System.out.println("사용자1 서버 실행 - 포트 8080");
	}

}
