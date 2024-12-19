package com.example.data_trans;

import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.NestingKind;

@RestController
@RequestMapping("/mq")
public class MQController {

/*
    @GetMapping("/consumer")
    public String consumer (@RequestParam String type, @RequestParam String name){
        Consumer consumer = new Consumer(type,name);
        thread(consumer,false);
        return "new consumer";
    }

    @GetMapping("/procedure")
    public String procedure (@RequestParam String type, @RequestParam String name, @RequestParam String message){
        Procedure procedure = new Procedure(name,message);
        thread(procedure,false);
        return "new procedure";
    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }*/
}
