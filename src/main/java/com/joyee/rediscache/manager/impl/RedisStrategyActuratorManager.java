package com.joyee.rediscache.manager.impl;

import com.joyee.rediscache.manager.RedisStrategy;
import com.joyee.rediscache.manager.RedisStrategyActuator;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@Log
public class RedisStrategyActuratorManager implements RedisStrategyActuator {
    private volatile boolean lock = true;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void youngOldActuator(RedisStrategy redisStrategy) {

    }

    @Override
    public void lock(long time) throws InterruptedException {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

                var future1 = executor.submit(() -> {
                    while(lock){ // 循环执行 模拟续租

                        System.out.println("子任务 "  +" 运行在 " + Thread.currentThread());
                        try {
                            redisTemplate.opsForValue().set("key","value",time,TimeUnit.SECONDS);
                            Boolean key = redisTemplate.expire("key", time, TimeUnit.SECONDS);
                            Thread.sleep(600*time);
                        } catch (InterruptedException e) {
                            break;
                        }
                        // 执行其他操作

                    }

                });
                log.info("处理主线程业务");
            //处理业务 模拟阻塞
            Thread.sleep(50000);
            future1.cancel(true);
                log.info("处理主线程业务完成");
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

    }
}
