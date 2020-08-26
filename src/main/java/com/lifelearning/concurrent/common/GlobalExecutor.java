package com.lifelearning.concurrent.common;

import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : ori
 * @date : 2020/8/27 12:33 上午
 */
public class   GlobalExecutor {
    private static class InstanceHolder{
        private static final ThreadPoolExecutor INSTANCE = new ThreadPoolExecutor(
                1,
                15,
                10,
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(), new ThreadPoolExecutorFactoryBean());
        public static ThreadPoolExecutor getInstance() {
            return INSTANCE;
        }
    }

    public static ThreadPoolExecutor getInstance(){
        return InstanceHolder.getInstance();
    }

}
