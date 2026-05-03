package com.mini_server.dto;

import com.mini_server.MethodClass.TestController;

import java.lang.reflect.Method;

public class Handler {

    public Object instance;
    public Method method;

    public Handler(TestController controller, Method getMethod) {
        instance = controller;
        method = getMethod;
    }
}
