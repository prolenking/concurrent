package com.lifelearning.concurrent.test.lock;

import com.lifelearning.concurrent.test.base.A;

import static com.lifelearning.concurrent.test.util.PrintUtil.printInfo;

/**
 * Create with IntelliJ IDEA
 * <p>
 * 刚启动时上的锁
 * 原因：jvm刚启动时有大量线程争夺，所以刚启动时默认不开偏向锁
 * <p>
 * User: liz
 * Date: 2020/6/12
 * Time: 9:57 上午
 *
 * @author lizhi
 */
public class StartLock {
    public static void main(String[] args) {
        A a1 = new A();
        printInfo("刚启动new的实例", a1);
    }
}
