package com.example.data_trans.factory;

import java.util.HashMap;

public abstract class AbstractModule<T> implements Collection<T> {
    // 공통된 HashMap 생성 로직
    protected HashMap<String, T> createMap() {
        return new HashMap<>();
    }

    // 각 모듈은 collect() 메서드를 구현해야 함
    @Override
    public abstract HashMap<String, T> collect();
}