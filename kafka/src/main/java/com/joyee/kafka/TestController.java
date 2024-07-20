package com.joyee.kafka;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @RequestMapping("kafka")
    public String kafka() {
        return "hello kafka";
    }


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/kafka/{message}")
    public void sendNormalMessage(@PathVariable("message") String message) {
        CrmContactDO crmContactDO = new CrmContactDO().setName("王敬一");
        kafkaTemplate.send("sb_topic", JSONUtil.toJsonStr(crmContactDO));
    }

}
