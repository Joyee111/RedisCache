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
public class RedisStrategyActuatorManager implements RedisStrategyActuator {

    @Value("${com.joyee.lock.rate}")
    private double rate;

    @Value("${com.joyee.lock.expire}")
    private Integer expire;

    private RedisTemplate redisTemplate;

    @Autowired
    public  RedisStrategyActuatorManager(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void youngOldActuator(RedisStrategy redisStrategy) {}

    @Override
    public void lock(long businessTime) throws InterruptedException {
        do{log.info("拿不到锁");}while(Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent("key", "value", expire, TimeUnit.MILLISECONDS)));
        log.info("拿到锁了");
        Thread virtualThread = Thread.ofVirtual().factory().newThread((() -> {
            for (;;) { // 循环执行 模拟续租
                log.info("子任务  运行在 " + Thread.currentThread());
                try {
                    redisTemplate.expire("key", expire, TimeUnit.MILLISECONDS);
                    //续租等待时长
                    Thread.sleep((long) (rate * businessTime));
                } catch (InterruptedException e) {
                    log.info("续租结束");
                    break;
                }
            }
        }));
        virtualThread.start();
        log.info("处理主线程业务");
        //处理业务 模拟阻塞
        Thread.sleep(5000*expire);
        //触发InterruptedException 跳出续租逻辑
        virtualThread.interrupt();
        log.info("处理主线程业务完成");
    }
}
