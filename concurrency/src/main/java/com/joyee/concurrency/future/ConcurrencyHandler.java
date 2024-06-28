package com.joyee.concurrency.future;

import lombok.extern.java.Log;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log
public class ConcurrencyHandler {

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(10,20,5,TimeUnit.SECONDS,new LinkedBlockingDeque<>(2000));
    /**
     * 使用{@link CountDownLatch} 并发执行程序并返回
     * 使用{@link CopyOnWriteArrayList} 保证线程安全
     * @param args
     * @return
     * @throws InterruptedException
     */
    public static Boolean countDownLatchTask (List<Boolean> args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(args.size());
        List<Boolean> result = new CopyOnWriteArrayList();
        args.stream().forEach(argsItem->{
            THREAD_POOL_EXECUTOR.execute(() -> {
                try {
                    result.add(argsItem);
                    log.info("CountDownLatch当前线程："+Thread.currentThread().getName()+"开始执行");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    log.info(e.getMessage());
                }finally {
                    countDownLatch.countDown();
                }
            });
        });
        countDownLatch.await();
        log.info("完成了："+result.size()+"个线程");
        return result.stream().allMatch(resultItem->resultItem);
    }

    /**
     * 使用{@link CompletableFuture#supplyAsync(Supplier)}实现多线程执行任务
     * 使用{@link CopyOnWriteArrayList} 保证线程安全
     * @param args
     * @return
     * @throws InterruptedException
     */
    public static Boolean CompletableFutureTask (List<Boolean> args) throws InterruptedException {
        Map<CompletableFuture<Boolean> , Boolean> collect = args.stream().collect(Collectors.toMap(arg -> new CompletableFuture<Boolean>(),arg -> arg));
        CopyOnWriteArrayList<CompletableFuture<Boolean>> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        collect.entrySet().forEach(entry->{
            copyOnWriteArrayList.add(CompletableFuture.supplyAsync(()->{
                try {
                    log.info("CompletableFuture:当前线程："+Thread.currentThread().getName()+"开始执行");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    log.info(e.getMessage());
                }
                return entry.getValue();
            },THREAD_POOL_EXECUTOR));
        });
        CompletableFuture.allOf(copyOnWriteArrayList.toArray(new CompletableFuture[copyOnWriteArrayList.size()])).join();
        log.info("完成了："+copyOnWriteArrayList.size()+"个线程");
        if(copyOnWriteArrayList.stream().anyMatch(CompletableFuture::isCompletedExceptionally)){
            log.info("this CompletableFuture completed exceptionally");
            return false;
        }else {
            return copyOnWriteArrayList.stream().allMatch(item -> item.getNow(Boolean.FALSE));
        }
    }
}
