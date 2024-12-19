package com.example.data_trans;

import java.io.File;
import java.util.Scanner;

public class User {
    private Consumer consumer;
    private Procedure procedure;

    public User(String name, String cusTopic) {
        // 각 사용자에 대한 Consumer와 Procedure 객체를 초기화합니다.
        this.consumer = new Consumer(name, cusTopic);
        this.procedure = new Procedure(name, cusTopic, "",  null);
    }

    public void startChat(String name) {
        // Consumer를 별도의 스레드로 실행 (메시지 수신)
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        // 사용자로부터 메시지를 입력받고, 이를 Procedure에 전달하여 메시지 전송
        Scanner scanner = new Scanner(System.in);
        System.out.println("메시지를 입력하세요 ('exit' 입력 시 종료): ");
        while (true) {
            System.out.print(name + " : ");
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                break; // exit 입력 시 채팅 종료
            } else if (message.equalsIgnoreCase("파일")) {
                // Prompt user to select a file to send
                System.out.println("파일을 선택하세요 (파일 경로 입력): ");
                String filePath = scanner.nextLine();
                File file = new File(filePath);
                procedure.setFile(file); // Set the file to send
                new Thread(procedure).start();
            } else {
                procedure.setFile(null);
                procedure.setMessage(message);
                new Thread(procedure).start(); // Send a regular text message
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        // 사용자 이름을 입력받고, 채팅 시작
        Scanner scanner = new Scanner(System.in);
        System.out.println("사용자 이름을 입력하세요: ");
        String name = scanner.nextLine();
        System.out.println("토픽 입력");
        String cusTopic = scanner.nextLine();
        User user = new User(name, cusTopic);
        user.startChat(name); // 채팅 시작
    }
}
