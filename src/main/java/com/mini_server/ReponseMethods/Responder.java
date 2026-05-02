package com.mini_server.ReponseMethods;

import com.mini_server.dto.HttpRequest;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
public class Responder {

    public void respond(HttpRequest httpRequest, PrintWriter out) {

        String body = httpRequest.response == null ? "" : httpRequest.response;

        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);

        out.print(httpRequest.version + " " + httpRequest.status + " " + reason(httpRequest.status) + "\r\n");

        out.print("Content-Type: application/json\r\n");
        out.print("Content-Length: " + bodyBytes.length + "\r\n");
        out.print("Connection: close\r\n");

        out.print("\r\n");

        out.print(body);

        out.flush();
    }

    private String reason(int status) {
        return switch (status) {
            case 200 -> "OK";
            case 400 -> "Bad Request";
            case 404 -> "Not Found";
            case 405 -> "Method Not Allowed";
            default -> "Internal Server Error";
        };
    }
}