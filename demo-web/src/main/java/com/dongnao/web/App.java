package com.dongnao.web;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        new ClassPathXmlApplicationContext("classpath*:applicatonContext.xml");
        CountDownLatch latch=new CountDownLatch(1);
        latch.await();
    }
}
