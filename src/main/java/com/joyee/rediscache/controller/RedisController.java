package com.joyee.rediscache.controller;

import com.joyee.rediscache.manager.RedisStrategyActuator;
import com.joyee.rediscache.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisStrategyActuator redisStrategyActuator;

    @Autowired
    private RedisService redisService;

    @PostMapping("lock")
    public String getLock(@RequestParam(value = "time") long time) throws InterruptedException, ExecutionException {
        redisService.lock(time);
        return "1";
    }
}
