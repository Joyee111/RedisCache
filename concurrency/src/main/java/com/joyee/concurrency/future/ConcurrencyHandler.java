package com.joyee.concurrency.future;

import com.joyee.concurrency.task.CommonTask;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log
@Component
public class ConcurrencyHandler {

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(100, 200, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(2000), Thread.ofVirtual().factory());


    /**
     * 使用{@link CountDownLatch} 并发执行程序并返回
     * 使用{@link CopyOnWriteArrayList} 保证线程安全
     *
     * @param commonTaskList
     * @return
     * @throws InterruptedException
     */
    public Boolean countDownLatchTaskWithOutResponse(List<CommonTask<Map<String, String>, Boolean>> commonTaskList, CountDownLatch countDownLatch) throws InterruptedException {
        commonTaskList.stream().forEach(commonTask -> {
            THREAD_POOL_EXECUTOR.execute(commonTask);
        });
        countDownLatch.await();
        log.info("执行状态" + commonTaskList.stream().map(CommonTask::getStatus).collect(Collectors.toUnmodifiableList()));
        return commonTaskList.stream().allMatch(CommonTask::getStatus);
    }

    /**
     * 使用{@link CompletableFuture#supplyAsync(Supplier)}实现多线程执行任务
     * 使用{@link CopyOnWriteArrayList} 保证线程安全
     *
     * @param commonTaskList
     * @return
     * @throws InterruptedException
     */
    public List<Boolean> completableFutureTaskWithResponse(List<CommonTask<Map<String, String>, Boolean>> commonTaskList) throws InterruptedException {
        CopyOnWriteArrayList<CompletableFuture<Boolean>> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        commonTaskList.forEach(commonTask -> {
            copyOnWriteArrayList.add(CompletableFuture.supplyAsync(commonTask.getSupplierTask(), THREAD_POOL_EXECUTOR));
        });
        CompletableFuture.allOf(copyOnWriteArrayList.toArray(new CompletableFuture[copyOnWriteArrayList.size()])).join();
        if (copyOnWriteArrayList.stream().anyMatch(CompletableFuture::isCompletedExceptionally)) {
            throw new InterruptedException("this CompletableFuture completed exceptionally");
        } else {
            return copyOnWriteArrayList.stream().map(item -> item.getNow(Boolean.FALSE)).collect(Collectors.toUnmodifiableList());
        }
    }
}
