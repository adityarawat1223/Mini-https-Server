package com.mini_server.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini_server.MethodClass.TestController;
import com.mini_server.dto.Handler;
import com.mini_server.dto.HttpRequest;
import com.mini_server.dto.Message;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class HandlerMapper {
    private final Map<String, Handler> mp = new LinkedHashMap<>();

    public void Adder(){
        try{
        TestController controller = new TestController();
        Method getMethod = TestController.class.getMethod("hello");
        mp.put("GET/hello", new Handler(controller, getMethod));

        Method postMethod = TestController.class.getMethod("echo", Message.class);
        mp.put("POST/echo", new Handler(controller, postMethod));
        }
        catch (Exception e) {
        throw new RuntimeException(e);
}
    }
    public void Mapper(HttpRequest httpRequest) {

        String key = httpRequest.method + httpRequest.path;
        System.out.println(key);

        Handler handler = mp.get(key);

        if (handler == null) {
            httpRequest.valid = false;
            httpRequest.status = 404;
            httpRequest.response = "Path Not Found";
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        Object result;

        Class<?>[] paramTypes = handler.method.getParameterTypes();

        try {
            if (paramTypes.length == 0) {
                result = handler.method.invoke(handler.instance);

            } else {
                Class<?> paramType = paramTypes[0];

                Object arg = mapper.readValue(httpRequest.body, paramType);
                System.out.print(arg);
                result = handler.method.invoke(handler.instance, arg);
            }

            httpRequest.status = 200;
            httpRequest.response = mapper.writeValueAsString(result);

        } catch (Exception e) {
            httpRequest.valid = false;
            httpRequest.status = 400;
            httpRequest.response = "Bad Request: " + e.getMessage();
        }
    }
}
