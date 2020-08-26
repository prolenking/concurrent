package com.lifelearning.concurrent.test.utils.test;

import com.lifelearning.concurrent.common.GlobalExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.*;

/**
 * 计数栅栏不仅能够用于控制线程一起开始，也能控制线程一起结束
 * 拓展思维：
 * CountDownLatch可以用于线程之间的通信工具，相当于线程的红绿灯
 *
 * @author : lizhi
 * @date : 2020/8/26 10:59 下午
 */
public class CountDownLatchTest2 {
    public static void main(String[] args) {
        CountDownLatch doneSignal = new CountDownLatch(10);
        ExecutorService e = GlobalExecutor.getInstance();
// create and start threads
        for (int i = 0; i < 10; ++i) {
            int finalI = i;
            e.execute(() ->{
                System.out.println("Thread"+ finalI +"正在运行");
                System.out.println("Thread"+ finalI +"开始睡眠");
                try {
                    //线程在启动后的执行时间各不相同
                    Thread.sleep(11000-1000 * finalI);
                    System.out.println("Thread"+ finalI +"睡眠结束");

                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                doneSignal.countDown();
            });
        }
        try {
            //用栅栏把主线程阻塞住，知道所有线程执行完成再放开
            System.out.println("主线程阻塞");
            doneSignal.await();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        System.out.println("主线程继续运行");
        e.shutdown();
    }
}