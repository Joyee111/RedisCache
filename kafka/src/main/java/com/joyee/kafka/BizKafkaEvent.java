package com.joyee.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.event.ConsumerStartedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BizKafkaEvent {

    @EventListener
    public void onConsumerStarted(ConsumerStartedEvent event) {
        // 在这里处理消费者启动事件
        // 例如，你可以打印一些日志或者执行一些初始化操作
        System.out.println("Kafka Consumer Started");
    }


}
