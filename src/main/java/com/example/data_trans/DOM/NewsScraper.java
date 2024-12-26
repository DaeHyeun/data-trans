package com.example.data_trans.DOM;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsScraper {
    public static void main(String[] args) {
        String url = "https://news.naver.com/section/101";  // 사용할 뉴스 사이트 URL로 바꿔주세요.
        String keyword = "시";  // 검색할 키워드

        try {
            // 뉴스 웹사이트의 HTML을 가져오기
            Document document = Jsoup.connect(url).get();

            // 기사 제목을 추출 (예시: <h2 class="headline">태그에 있는 기사 제목을 가져옵니다)
            Elements articles = document.select("strong.sa_text_strong");
            Elements articles1 = document.select("sa_text_lede");
            System.out.println(articles);
            System.out.println();
            System.out.println(keyword + " 검색내용");

            // 추출한 기사 제목을 반복하며 특정 키워드가 포함된 기사 찾기
            for (Element article : articles) {
                String title = article.text();  // 기사 제목 텍스트
                if (title.contains(keyword)) {  // 제목에 키워드가 포함된 경우
                    System.out.println("기사 제목: " + title);
                }
            }

            System.out.println("================================================================= article1");
            System.out.println(articles1);
            System.out.println();
            System.out.println(keyword + " 검색내용");
            for (Element article : articles1){
                String title = article.text();
                if(title.contains(keyword)){
                    System.out.println("기사제목 : " + title);
                }
            }
        } catch (IOException e) {
            System.err.println("웹 페이지를 가져오는 데 실패했습니다.");
            e.printStackTrace();
        }
    }
}
