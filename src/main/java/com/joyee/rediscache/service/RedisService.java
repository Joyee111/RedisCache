package com.joyee.rediscache.service;

import java.util.concurrent.ExecutionException;

/**
 * Redis基础服务
 */
public interface RedisService {

    public void lock(long time) throws InterruptedException, ExecutionException;
}
