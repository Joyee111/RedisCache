package com.joyee.kafka;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class KafkaMessageListener{

    @KafkaListener(topics = "sb_topic", groupId = "myGroup")
    @KafkaHandler()
    public void listen(@Payload List<String> record, Acknowledgment acknowledgment) {
        try {
            // 消息处理逻辑
            CrmContactDO bean = JSONUtil.toBean(record.get(0), CrmContactDO.class);
            System.out.println("Received message in listener: " + bean.getName());
            // 手动确认消息
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // 在异常情况下，不确认消息，以便它们可以被重新传递或处理
            acknowledgment.nack(Duration.ofMillis(500));
        }
    }
}
