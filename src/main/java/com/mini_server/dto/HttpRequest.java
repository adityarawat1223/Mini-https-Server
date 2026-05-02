package com.mini_server.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest {
    public String method;
    public String path;
    public String version;
    public Map<String, String> headers = new LinkedHashMap<>();
    public String body;
    public boolean valid = true;
    public String response;
    public int status = 200;
}
