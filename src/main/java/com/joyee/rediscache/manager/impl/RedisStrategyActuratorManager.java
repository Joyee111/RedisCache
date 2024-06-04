package com.joyee.rediscache.manager.impl;

import com.joyee.rediscache.manager.RedisStrategy;
import com.joyee.rediscache.manager.RedisStrategyActuator;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@Log
public class RedisStrategyActuratorManager implements RedisStrategyActuator {

    @Value("${com.joyee.lock.rate}")
    private double rate;
    @Value("${com.joyee.lock.expire}")
    private Integer expire;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void youngOldActuator(RedisStrategy redisStrategy) {

    }

    @Override
    public void lock(long time) throws InterruptedException {
        do{log.info("拿不到锁");}while(Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent("key", "value", expire, TimeUnit.MILLISECONDS)));
        try (var executor = Executors.newSingleThreadExecutor()) {
            var future1 = executor.submit(() -> {

                for(;;){ // 循环执行 模拟续租
                    log.info("子任务  运行在 " + Thread.currentThread());
                    try {
                        Boolean key = redisTemplate.expire("key", expire, TimeUnit.MILLISECONDS);
                        Thread.sleep((long) (rate*time));
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });
            log.info("处理主线程业务");
            //处理业务 模拟阻塞
            Thread.sleep(5*expire);
            future1.cancel(true);
            log.info("处理主线程业务完成");
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

    }
}
