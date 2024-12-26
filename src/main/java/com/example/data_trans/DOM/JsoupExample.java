package com.example.data_trans.DOM;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

public class JsoupExample {
    public static void main(String[] args) {
        try {
            // HTML 파일을 Jsoup로 파싱
            File htmlFile = new File("C:\\Users\\HCNC\\Desktop\\data trans\\src\\main\\resources\\templates\\person.html");
            Document document = Jsoup.parse(htmlFile, "UTF-8");

            // "name"과 "age" 정보 추출
            Element nameElement = document.getElementById("name");
            Element ageElement = document.getElementById("age");

            // 값 출력
            if (nameElement != null && ageElement != null) {
                String name = nameElement.text();
                String age = ageElement.text();

                System.out.println("Name: " + name);
                System.out.println("Age: " + age);
            } else {
                System.out.println("Could not find person information.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
