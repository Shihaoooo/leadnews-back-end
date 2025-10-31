package com.nobody.utils;

public class ThreadLocalUtils <T> {

    ThreadLocal<T> threadLocal = new ThreadLocal<>();

    public void set( T value){
        threadLocal.set(value);
    }

    public T get(){
        return threadLocal.get();
    }

    public void remove(){
        threadLocal.remove();
    }
}
