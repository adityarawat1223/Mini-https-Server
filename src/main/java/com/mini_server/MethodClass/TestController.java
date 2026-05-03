package com.mini_server.MethodClass;

import com.mini_server.dto.Message;

public class TestController {

    public String hello() {
        return "Hello from GET!";
    }

    public Message echo(Message msg) {
        return msg;
    }
}