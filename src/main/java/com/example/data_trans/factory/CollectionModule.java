package com.example.data_trans.factory;

import java.util.HashMap;


class CollectionModule implements Collection<Object>  {
    private Module1 module1;
    private Module2 module2;
    private Module3 module3;
    private Module4 module4;  // Module4 추가
    private Module5 module5;
    private Module6 module6;

    public CollectionModule(Module1 module1,Module2 module2,Module3 module3,Module4 module4,Module5 module5,Module6 module6){
        this.module1 = module1;
        this.module2 = module2;
        this.module3 = module3;
        this.module4 = module4;
        this.module5 = module5;
        this.module6 = module6;
    }


    @Override
    public HashMap<String, Object> collect() {
        HashMap<String, Object> combinedMap = new HashMap<>();
        combinedMap.putAll(module1.collect());
        combinedMap.putAll(module2.collect());
        combinedMap.putAll(module3.collect());
        combinedMap.putAll(module4.collect());  // module4 데이터 추가
        combinedMap.putAll(module5.collect());
        combinedMap.putAll(module6.collect());
        return combinedMap;
    }
}