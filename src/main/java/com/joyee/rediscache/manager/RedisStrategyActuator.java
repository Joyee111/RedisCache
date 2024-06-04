package com.joyee.rediscache.manager;

import java.util.concurrent.ExecutionException;

/**
 * Redis young old 策略执行器
 * 用于执行缓存的分代策略
 * 最终实现 热点数据 持久化
 */
public interface RedisStrategyActuator {
    public void youngOldActuator(RedisStrategy redisStrategy);

    public void lock (long time) throws InterruptedException, ExecutionException;
}
