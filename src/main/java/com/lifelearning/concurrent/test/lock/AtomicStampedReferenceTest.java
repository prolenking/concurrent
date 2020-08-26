package com.lifelearning.concurrent.test.lock;

import com.lifelearning.concurrent.test.base.A;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Create with IntelliJ IDEA
 * <p>
 * User: liz
 * Date: 2020/6/16
 * Time: 11:52 下午
 *
 * @author lizhi
 */
public class AtomicStampedReferenceTest {
    public static void main(String[] args) {
        A testa = new A();
        AtomicStampedReference<Integer> atomicAValue = new AtomicStampedReference<>(testa.value,testa.version);
        atomicAValue.set(testa.value+1, testa.version+1);
        System.out.println(atomicAValue.getReference() + " -- "+testa.version);
    }
}
