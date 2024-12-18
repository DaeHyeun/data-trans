package com.example.data_trans;

import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.NestingKind;

@RestController
@RequestMapping("/mq")
public class MQController {


    @GetMapping("/consumer")
    public String consumer (@RequestParam String type, @RequestParam String name){
        Consumer consumer = new Consumer(type,name);
        System.out.println("consumer type : " + consumer.getName());
        System.out.println("consumer Name : " + consumer.getType());
        thread(consumer,false);
        return "new consumer";
    }

    @GetMapping("/procedure")
    public String procedure (@RequestParam String type, @RequestParam String name){
        Procedure procedure = new Procedure(type, name);
        System.out.println("procedure type : " + procedure.getType());
        System.out.println("procedure name : " + procedure.getName());
        thread(procedure,false);
        return "new procedure";
    }



    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
}
