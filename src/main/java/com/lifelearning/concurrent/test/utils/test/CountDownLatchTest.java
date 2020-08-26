package com.lifelearning.concurrent.test.utils.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@link java.util.concurrent.CountDownLatch} is a concurrent utils
 *
 * @author: lizhi
 * @date: 2020/8/26
 * @time: 10:37 下午
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        int threadNum = 10;
        CountDownLatch lock = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            int finalI = i;
            new Thread(
                    () -> {
                        System.out.println("Thread"+ finalI +"已被创建");
                        try {
                            System.out.println("Thread"+ finalI +"正在等待栅栏放行");
                            lock.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                        System.out.println("Thread"+ finalI +"正在运行");
                    }
            ).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.countDown();
        }

    }
}
