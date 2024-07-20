package com.joyee.feign.service;

import com.joyee.feign.service.fallback.KafkaServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        value = "kafka",fallbackFactory = KafkaServiceFallbackFactory.class,url = "/kafka"
)
public interface KafkaService {
    @GetMapping("/kafka/{message}")
    public String sendNormalMessage (@PathVariable("message") String message);
}
