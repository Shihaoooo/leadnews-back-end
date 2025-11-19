package com.nobody.utils;

public class ThreadLocalUtils {

    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void set(Object value){
        threadLocal.set((Integer) value);
    }

    public static Integer get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}
