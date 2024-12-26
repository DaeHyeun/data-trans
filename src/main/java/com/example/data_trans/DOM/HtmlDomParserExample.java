package com.example.data_trans.DOM;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class HtmlDomParserExample {
    public static void main(String[] args) {
        try {
            // HTML 파일을 파싱하기 위한 DocumentBuilderFactory 생성
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // person.html 파일을 Document 객체로 변환
            File htmlFile = new File("C:\\Users\\HCNC\\Desktop\\data trans\\src\\main\\resources\\templates\\Person Information.html"); // 실제 HTML 파일 경로에 맞게 설정
            Document document = builder.parse(htmlFile);
            // HTML 문서에서 "name"과 "age" 값을 추출
            Node nameNode = document.getElementById("name");
            Node ageNode = document.getElementById("age");

            // 값을 출력
            if (nameNode != null && ageNode != null) {
                String name = nameNode.getTextContent();
                String age = ageNode.getTextContent();

                System.out.println("Name: " + name);
                System.out.println("Age: " + age);
            } else {
                System.out.println("Could not find person information.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
