package com.lifelearning.concurrent.test.lock;



import com.lifelearning.concurrent.test.base.A;
import com.lifelearning.concurrent.test.util.PrintUtil;

import java.util.concurrent.CountDownLatch;

/**
 * Create with IntelliJ IDEA
 * <p>
 * 一站式抓到所有锁
 * <p>
 * User: liz
 * Date: 2020/6/9
 * Time: 8:54 下午
 *
 * @author lizhi
 */
public class LockTest {
    public static void main(String[] args) {
        A a1 = new A();
        PrintUtil.printInfo("刚启动new的实例", a1);
        try {
            // 睡6s等JVM开默认偏向锁
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        A a2 = new A();
        PrintUtil.printInfo("等待之后JVM开的偏向锁", a2);
        String hashCodeString = Integer.toBinaryString(a2.hashCode());
        System.out.println("hashCode:" + hashCodeString);
        int end = hashCodeString.length();
        System.out.println("按字节（8位）倒排hashCode");
        for (; end > 0; end -= 8) {
            System.out.print(hashCodeString.substring(Math.max(end - 8, 0), end) + " ");
        }
        PrintUtil.printInfo("调用了方法后的实例", a2);

        synchronized (a2) {
            PrintUtil.printInfo("在主线程锁了一下a2", a2);
        }
        PrintUtil.printInfo("主线程解锁了a2", a2);

        System.out.println("捕捉偏向锁");
        System.out.println("new 一个 a3备用");
        A a3 = new A();
        synchronized (a3) {
            System.out.println("主线程给a3上了锁");
            PrintUtil.printInfo("上偏向锁的a3", a3);
            a3.a++;
        }

        System.out.println("开始尝试并发情况下的锁");
        A a4 = new A();
        int threadNum = 4;
        CountDownLatch count = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            int finalI = i;
            new Thread(
                    () -> {
                        try {
                            count.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (finalI != 0) {
                            try {
                                System.out.println("thread_" + finalI + "睡了一会");
                                Thread.sleep(200 * finalI);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (finalI == 1) {
                            synchronized (a4) {
                                PrintUtil.printInfo("thread_" + finalI + "使用偏向锁锁了a4", a4);
                            }
                        }
                        synchronized (a2) {
                            System.out.println("thread_" + finalI + "给a2上了锁");
                            PrintUtil.printInfo("上自旋锁的a2", a2);
                            a2.a++;
                        }
                    }
            ).start();
            count.countDown();
        }
        try {
            //睡个一会防止重量级锁影响自旋锁
            System.out.println("睡了10s，防止重量级锁影响自旋锁");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CountDownLatch count1 = new CountDownLatch(threadNum);
        for (int i = threadNum; i < threadNum + threadNum; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    count.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a2) {
                    System.out.println("thread_" + finalI + "给a2上了锁");
                    PrintUtil.printInfo("上重量级锁的a2", a2);
                    a2.a++;
                }
            }).start();
            count1.countDown();
        }
    }

}

