package com.dongnao.web;

import com.dongnao.api.Ihello;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class Hello implements Ihello {
    public String say(String name) {
        return name+" 您好！";
    }


}
