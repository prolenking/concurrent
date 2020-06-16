package com.lifelearning.concurrent.test.util;

import java.util.concurrent.CountDownLatch;

/**
 * Create with IntelliJ IDEA
 * <p>
 * User: liz
 * Date: 2020/6/16
 * Time: 10:56 下午
 *
 * @author lizhi
 */
public class ConcurrentTestUtil {
    public static void concurrentTest(Runnable runnable, int threadNum){
        CountDownLatch count = new CountDownLatch(threadNum);
        for (int i =0; i< threadNum; i++){
            new Thread(() ->{
                try {
                    count.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runnable.run();
            }).start();
            count.countDown();
        }
    }
}
