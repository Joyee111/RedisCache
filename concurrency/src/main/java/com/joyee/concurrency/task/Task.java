package com.joyee.concurrency.task;

public interface Task<T,R> extends Runnable{

    public  R doTask(T t);
}
