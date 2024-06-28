package com.joyee.concurrency;

import com.joyee.concurrency.future.ConcurrencyHandler;
import com.joyee.concurrency.task.CommonTask;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

@SpringBootTest
@Log
class ConcurrencyApplicationTests {

    @Autowired
    private ConcurrencyHandler concurrencyHandler;
    @Test
    void contextLoads() throws InterruptedException {
        ArrayList<Boolean> booleans = new ArrayList<>();
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(false);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);

        long startCount = System.currentTimeMillis();
        Boolean result = false;

        ArrayList<CommonTask<Map<String, String>, Boolean>> commonTasks = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(booleans.size());
        booleans.stream().forEach(param->{
            Map paramMap = new HashMap<String, String>();paramMap.put("param", param.booleanValue() + "");
            Consumer<Map<String,String>> beforeTask = (arg) -> {
                String paramBeforeTask = arg.get("param");
                arg.put("param", paramBeforeTask + ":任务前包装参数");
            };
            Consumer<Map<String,String>> afterTask = (arg) -> {
                String paramAfterTask = arg.get("param");
                arg.put("param", paramAfterTask + ":任务后包装参数");
                countDownLatch.countDown();
            };
            CommonTask<Map<String,String>, Boolean> commonTask = new CommonTask<Map<String,String>, Boolean>(beforeTask,afterTask,paramMap,result) {
                @Override
                public Boolean doTask(Map<String,String> arg){
                    try{
                        Thread.sleep(500);
                        return true;//
                    }catch (Exception e){
                        return false;
                    }
                }
            };
            commonTasks.add(commonTask);
        });
        log.info("无结果有返回状态的执行："+concurrencyHandler.countDownLatchTaskWithOutResponse(commonTasks,countDownLatch));
        log.info("countDownLatch耗时："+(System.currentTimeMillis()-startCount));
        long startComp = System.currentTimeMillis();
        log.info("有返回结果的执行："+concurrencyHandler.completableFutureTaskWithResponse(commonTasks));
        log.info("completableFuture："+(System.currentTimeMillis()-startComp));
    }

}
