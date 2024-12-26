package com.example.data_trans.factory;

import java.util.HashMap;
import java.util.Queue;

public class Module5 extends AbstractModule<Queue<String>>{

    private Queue<String> stringQueue;

    public Module5 (Queue<String> parameter){
        this.stringQueue = parameter;
    }

    @Override
    public HashMap<String, Queue<String>> collect() {
        // HashMap에 Queue<String>을 저장하여 반환
        HashMap<String, Queue<String>> map = createMap();
        map.put("QueueData", stringQueue);
        return map;
    }
}
