package com.joyee.feign.service;

import com.joyee.feign.service.fallback.KafkaServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "kafka",fallbackFactory = KafkaServiceFallbackFactory.class,url = "/kafka",dismiss404 = true
)
public interface KafkaService {

    public String sendNormalMessage ();
}
