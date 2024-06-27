package com.joyee.concurrency;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static com.joyee.concurrency.future.ConcurrencyHandler.CompletableFutureTask;
import static com.joyee.concurrency.future.ConcurrencyHandler.countDownLatchTask;

@SpringBootTest
class ConcurrencyApplicationTests {

    @Test
    void contextLoads() throws InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList<Boolean> booleans = new ArrayList<>();
        booleans.add(false);
        booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        booleans.add(true);
        booleans.add(true);booleans.add(true);booleans.add(true);booleans.add(true);
        System.out.println("结果："+countDownLatchTask(booleans));
        System.out.println("结果："+CompletableFutureTask(booleans));
        System.out.println((System.currentTimeMillis()-start)/1000+"s");
    }

}
