package com.joyee.rediscache.entity;

import lombok.Data;
import org.springframework.boot.configurationprocessor.json.JSONObject;

@Data
public class RedisItem {
    /**
     * 缓存实体
     */
    private JSONObject item;
    /**
     * 命中数
     */
    private Integer count;
}
