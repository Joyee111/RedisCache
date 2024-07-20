package com.joyee.feign.service.fallback;

import com.joyee.feign.service.KafkaService;
import org.springframework.cloud.openfeign.FallbackFactory;

public class KafkaServiceFallbackFactory implements FallbackFactory<KafkaService> {
    @Override
    public KafkaService create(Throwable cause) {
        return new KafkaService() {

            @Override
            public String sendNormalMessage(String message) {
                return "";
            }
        };
    }
}
