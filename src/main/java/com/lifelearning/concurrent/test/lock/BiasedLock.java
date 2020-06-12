package com.lifelearning.concurrent.test.lock;

import static com.lifelearning.concurrent.test.lock.PrintUtil.printInfo;

/**
 * Create with IntelliJ IDEA
 * <p>
 *     匿名偏向锁
 *     +
 *     匿名偏向锁 -> 偏向锁
 *     +
 *     偏向锁的解锁
 *     +
 *     偏向锁的升级
 *     +
 *     顺带上的自旋锁解锁
 *
 * User: liz
 * Date: 2020/6/12
 * Time: 10:48 上午
 *
 * @author lizhi
 */
public class BiasedLock {
    public static void main(String[] args) {
        prepareForBiasedLock();
        A a3 = new A();
        printInfo("匿名偏向锁的a3", a3);
        synchronized (a3) {
            System.out.println("主线程给a3上了锁");
            printInfo("上偏向锁的a3", a3);
            a3.a++;
        }
        printInfo("主线程解锁后的a3", a3);
        // 争夺a3升级锁
        scramble(a3);
    }

    private static void scramble(A a3) {
        System.out.println("new 一个线程B来争夺 a3");
        new Thread(() -> {
            synchronized (a3) {
                System.out.println("线程B争夺a3");
                printInfo("升级为自旋锁的a3", a3);
                a3.a++;
            }
            printInfo("线程B解锁了a3, a3回归无锁", a3);
        }).start();
    }

    static void prepareForBiasedLock() {
        try {
            System.out.println("等待5s给jvm上偏向锁");
            for (int i = 0; i<5;i++) {
                System.out.println(i+1);
                Thread.sleep(1000);
            }
            System.out.println("开始锁的演示");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
