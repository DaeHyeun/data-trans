package com.example.data_trans.factory;

import java.util.HashMap;
import java.util.List;

public class Module1 extends AbstractModule<List<Integer>> {

    private List<List<Integer>> listOfIntegerLists;  // List<List<Integer>> 타입으로 변경

    public Module1 (List<List<Integer>> parameter){
        this.listOfIntegerLists = parameter;
    }


    //추상클래스 구현 List -> Map으로 저장
    @Override
    public HashMap<String, List<Integer>> collect() {
        // 1. createMap()을 호출하여 HashMap 생성
        HashMap<String, List<Integer>> map = createMap();

        // 2. listOfIntegerLists에서 각 리스트를 HashMap에 추가
        for (int i = 0; i < listOfIntegerLists.size(); i++) {
            map.put("List" + (i + 1), listOfIntegerLists.get(i));
        }

        // 3. 완성된 HashMap을 반환
        return map;
    }
}