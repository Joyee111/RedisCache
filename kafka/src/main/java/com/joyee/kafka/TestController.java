package com.joyee.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("kafka")
    public String kafka() {
        return "hello kafka";
    }


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/kafka/{message}")
    public void sendNormalMessage(@PathVariable("message") String message) {
        kafkaTemplate.send("sb_topic", message);
    }
}
