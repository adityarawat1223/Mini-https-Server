package com.mini_server.ParserMethods;
import com.mini_server.dto.HttpRequest;
import java.io.IOException;
import java.io.InputStream;


public class ParserFunctions {
    void reqParser(String message, HttpRequest httpRequest) {
        String[] parts = message.split(" ");
        httpRequest.method = parts[0];
        httpRequest.path = parts[1].toLowerCase();
        httpRequest.version = parts[2];
    }
    void headerParser(String message, HttpRequest httpRequest) {
        int idx = message.indexOf(":");
        String key = message.substring(0, idx).trim().toLowerCase();
        String value = message.substring(idx + 1).trim().toLowerCase();
        httpRequest.headers.put(key, value);
    }

    public void JsonParser(HttpRequest httpRequest, InputStream in) throws IOException {
        boolean reqline = true;
        StringBuilder s = new StringBuilder();
        int reader;

        while ((reader = in.read()) != -1) {
            char c = (char) reader;

            if (c == '\r') continue;

            if (c == '\n') {
                if (reqline) {
                    reqParser(s.toString(), httpRequest);
                    reqline = false;
                } else {
                    if (s.isEmpty()) break;
                    headerParser(s.toString(), httpRequest);
                }
                s.setLength(0);
            } else {
                s.append(c);
            }
        }

        String cl = httpRequest.headers.get("content-length");
        if (cl != null) {
            int contentLength = Integer.parseInt(cl);
            byte[] body = new byte[contentLength];

            for (int i = 0; i < contentLength; i++) {
                int b = in.read();

                if (b == -1) {
                    httpRequest.status = 404;
                    httpRequest.response = "Length Mismatch";
                    return;
                }

                body[i] = (byte) b;
            }

            httpRequest.body = new String(body, java.nio.charset.StandardCharsets.UTF_8);
        }
    }


}
