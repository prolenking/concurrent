package com.lifelearning.concurrent.test.thread;

import com.lifelearning.concurrent.common.GlobalExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : ori
 * @date : 2020/8/27 12:29 上午
 */
public class CallableTest {
    public static void main(String[] args) {
        Callable<Integer> callable =  () -> {
            for (int i = 0; i<5;i++){
                System.out.println(i);
                Thread.sleep(1000);
            }
            return 1;
        };
        ThreadPoolExecutor executor = GlobalExecutor.getInstance();
        try {
            Future<Integer> future = executor.submit(callable);
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }
    }
}
