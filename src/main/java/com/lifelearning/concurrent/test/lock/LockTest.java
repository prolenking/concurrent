package com.lifelearning.concurrent.test.lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.CountDownLatch;

/**
 * Create with IntelliJ IDEA
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
        printInfo("刚启动new的实例", a1);
        try {
            // 睡6s等JVM开默认偏向锁
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        A a2 = new A();
        printInfo("等待之后JVM开的偏向锁", a2);
        String hashCodeString = Integer.toBinaryString(a2.hashCode());
        System.out.println("hashCode:" + hashCodeString);
        int end = hashCodeString.length();
        System.out.println("按字节（8位）倒排hashCode");
        for (; end > 0; end -= 8) {
            System.out.print(hashCodeString.substring(Math.max(end - 8, 0), end) + " ");
        }
        printInfo("调用了方法后的实例", a2);

        synchronized (a2) {
            printInfo("在主线程锁了一下a2", a2);
        }
        printInfo("主线程解锁了a2", a2);

        System.out.println("开始尝试并发情况下的锁");
        int threadNum = 2;
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
                        if (finalI < 1) {
                            try {
                                System.out.println("thread_" + finalI + "睡了一会");
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        synchronized (a2) {
                            System.out.println("thread_" + finalI + "给a2上了锁");
                            printInfo("上自旋锁的a2", a2);
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
        for (int i = 2; i < threadNum + 2; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    count.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a2) {
                    System.out.println("thread_" + finalI + "给a2上了锁");
                    printInfo("上重量级锁的a2", a2);
                    a2.a++;
                }
            }).start();
            count1.countDown();
        }
    }

    private static void printInfo(String remark, A a) {
        System.out.println(remark + "\n" + ClassLayout.parseInstance(a).toPrintable());
    }
}

