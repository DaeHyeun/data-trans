package com.example.data_trans.exception;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ExceptionExample {
    public static void main(String[] args) {
        // 예외 케이스를 반복문으로 처리
        System.out.println();
        for (int caseNumber = 1; caseNumber <= 5; caseNumber++) {
            try {
                switch (caseNumber) {
                    case 1:
                        // 예외 1: IOException 발생 시나리오 (파일 읽기 실패)
                        System.out.println("Case " + caseNumber + ": 파일 읽기 예외 발생");
                        FileReader fr = new FileReader("nonexistent_file.txt"); // 존재하지 않는 파일 열기
                        break;
                    case 2:
                        // 예외 2: SQLException 발생 시나리오 (잘못된 DB 연결)
                        System.out.println("Case " + caseNumber + ": DB 연결 예외 발생");
                        Connection conn = DriverManager.getConnection("jdbc:invalid_url", "user", "password"); // 잘못된 URL
                        break;
                    case 3:
                        // 예외 3: NullPointerException 발생 시나리오 (null 객체 참조)
                        System.out.println("Case " + caseNumber + ": null 객체 참조 예외 발생");
                        String str = null;
                        int length = str.length(); // null 객체 참조
                        break;
                    case 4:
                        // 예외 4: ArrayIndexOutOfBoundsException 발생 시나리오 (배열 범위 초과)
                        System.out.println("Case " + caseNumber + ": 배열 인덱스 초과 예외 발생");
                        int[] arr = new int[3];
                        arr[5] = 10; // 배열 크기 초과
                        break;
                    case 5:
                        // 예외 5: ArithmeticException 발생 시나리오 (0으로 나누기)
                        System.out.println("Case " + caseNumber + ": 산술 연산 예외 발생");
                        int result = 10 / 0; // 0으로 나누기
                        break;
                    default:
                        System.out.println("잘못된 case 번호입니다.");
                }
            } catch (IOException e) {
                System.out.println("IOException 발생, 예외 메시지: " + e.getMessage());
                // 예외 발생 시 대체 코드
                System.out.println("대체 코드: 다른 파일을 열거나 파일 경로를 다시 확인하세요.");
                // 예를 들어, 다른 파일로 시도할 수 있음
                try {
                    FileReader fr = new FileReader("C:\\Users\\HCNC\\Desktop\\전송파일\\새 텍스트 문서.txt");  // 예비 파일로 시도
                    System.out.println("파일을 성공적으로 열었습니다.");
                } catch (IOException ex) {
                    System.out.println("예비 파일도 열 수 없습니다. " + ex.getMessage());
                }
            } catch (SQLException e) {
                System.out.println("SQLException 발생, 예외 메시지: " + e.getMessage());
                // 예외 발생 시 대체 코드
                System.out.println("대체 코드: 데이터베이스 연결을 다시 시도합니다.");
                try {
                    // 재시도 코드 예시 (URL, 사용자명, 비밀번호 재검토 후)
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "C##HCNC", "1234");
                    System.out.println("데이터베이스 연결 성공!");
                } catch (SQLException ex) {
                    System.out.println("재시도 후에도 데이터베이스 연결에 실패했습니다: " + ex.getMessage());
                }
            } catch (NullPointerException e) {
                System.out.println("NullPointerException 발생, 예외 메시지: " + e.getMessage());
                // 예외 발생 시 대체 코드
                System.out.println("대체 코드: 객체를 null이 아닌 값으로 초기화합니다.");
                String str = "기본값";
                int length = str.length();
                System.out.println("새로운 문자열 길이: " + length);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ArrayIndexOutOfBoundsException 발생, 예외 메시지: " + e.getMessage());
                // 예외 발생 시 대체 코드
                System.out.println("대체 코드: 배열 크기를 확인하거나 안전한 범위 내에서 인덱스를 사용합니다.");
                int[] arr = new int[3];
                if (arr.length > 2) {
                    arr[2] = 10; // 안전한 인덱스 사용
                    System.out.println("배열의 2번 인덱스에 값이 성공적으로 할당되었습니다.");
                } else {
                    System.out.println("배열의 크기가 충분하지 않아 할당할 수 없습니다.");
                }
            } catch (ArithmeticException e) {
                System.out.println("ArithmeticException 발생, 예외 메시지: " + e.getMessage());
                // 예외 발생 시 대체 코드
                System.out.println("대체 코드: 0으로 나누는 문제를 피하기 위해 조건을 추가합니다.");
                int denominator = 1;  // 0 대신 다른 값 사용
                int result = 10 / denominator;
                System.out.println("결과: " + result);
            } catch (Exception e) {
                System.out.println("기타 예외 발생: " + e.getMessage());
                // 기타 예외 처리
                System.out.println("대체 코드: 예기치 않은 오류가 발생했습니다. 시스템 관리자에게 문의하세요.");
            } finally {
                // 예외가 발생하든 안 하든 항상 실행되는 블록
                System.out.println("Case " + caseNumber + " 처리 완료\n");
            }
        }
    }

}
