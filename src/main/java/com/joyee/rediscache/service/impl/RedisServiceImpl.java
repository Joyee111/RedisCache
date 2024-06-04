package com.joyee.rediscache.service.impl;

import com.joyee.rediscache.manager.RedisStrategyActuator;
import com.joyee.rediscache.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisStrategyActuator redisStrategyActuator;

    public void setRedisStrategyActuator(){
        redisStrategyActuator.youngOldActuator((redisItem -> {
            //查询命中数
            //根据命中数判断是否进入old层 进入 增加超时时间 并写入持久层
        }));
    }

    @Override
    public void lock(long time) throws InterruptedException, ExecutionException {
        redisStrategyActuator.lock(time);
    }
}
