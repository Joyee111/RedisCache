package com.joyee.concurrency.task;

import org.springframework.lang.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 自定义任务
 *
 * @param <T> 请求参数 类型
 * @param <R> 返回值 类型
 */
public abstract class CommonTask<T, R> implements Task<T,R>, Supplier<R> {

    private final Consumer<T> beforeTask;
    private final Consumer<T>  afterTask;
    private T t;
    private R result;
    private Boolean status = false;
    private final Consumer<T> doTask = (t)->{
            result = doTask(t);
    };
    private final Supplier<R> supplierTask = ()->{
        run();
        return getResult();
    };

    public CommonTask(Consumer<T> beforeTask, Consumer<T>  afterTask,  T t , R r) {
        this.beforeTask = beforeTask;
        this.afterTask = afterTask;
        this.t = t;
        this.result = r;
    }

    public CommonTask(Consumer<T> beforeTask,  Consumer<T>  afterTask, R r) {
        this.beforeTask = beforeTask;
        this.afterTask = afterTask;
        this.result = r;
    }

    public R getResult() {
        return result;
    }

    public Boolean getStatus() {
        return status;
    }

    public Supplier<R> getSupplierTask() {
        return supplierTask;
    }

    @Override
    public void run() {
        beforeTask.andThen(doTask).andThen(afterTask).accept(t);
        status = true;
    }

    @Override
    public R get() {
        return getResult();
    }

}
