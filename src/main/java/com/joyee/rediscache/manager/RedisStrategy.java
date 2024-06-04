package com.joyee.rediscache.manager;

import com.joyee.rediscache.entity.RedisItem;

/**
 * Redis 缓存策略
 */
public interface RedisStrategy {
    public void complate(RedisItem redisItem);
}
