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
        System.out.println("结果："+countDownLatchTask(booleans));
        System.out.println((System.currentTimeMillis()-startCount));
        long startComp = System.currentTimeMillis();
        System.out.println("结果："+CompletableFutureTask(booleans));
        System.out.println((System.currentTimeMillis()-startComp));
    }

}
