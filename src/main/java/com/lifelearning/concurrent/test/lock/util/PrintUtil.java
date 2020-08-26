package com.lifelearning.concurrent.test.lock.util;

import com.lifelearning.concurrent.test.base.A;
import org.openjdk.jol.info.ClassLayout;

/**
 * Create with IntelliJ IDEA
 * <p>
 * 用于打印实例的内存信息
 * <p>
 * User: liz
 * Date: 2020/6/12
 * Time: 11:14 上午
 *
 * @author lizhi
 */
public class PrintUtil {
    public static void printInfo(String remark, A a) {
        System.out.println(remark + "\n" + ClassLayout.parseInstance(a).toPrintable());
    }
}
