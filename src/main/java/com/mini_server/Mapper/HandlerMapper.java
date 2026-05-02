package com.mini_server.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini_server.dto.Handler;
import com.mini_server.dto.HttpRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class HandlerMapper {
    private final Map<String, Handler> mp = new LinkedHashMap<>();

    public void Mapper(HttpRequest httpRequest) {

        String key = httpRequest.method + httpRequest.path;

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
                // No params → simple invoke
                result = handler.method.invoke(handler.instance);

            } else {
                Class<?> paramType = paramTypes[0];

                Object arg = mapper.readValue(httpRequest.body, paramType);

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
