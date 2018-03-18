package com.dongnao.web;

import com.dongnao.api.Ihello;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class AppClient {
    public static void main(String[] args){
        ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("classpath*:applicatonContext-client.xml");
        Ihello hello=(Ihello) context.getBean("hello");
        System.out.println(hello.say("dongnao"));

    }
}
