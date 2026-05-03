package com.mini_server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9090);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            String body = """
            {
              "msg": "hello"
            }
            """;

            int contentLength = body.getBytes(StandardCharsets.UTF_8).length;

            out.print("GET /hello HTTP/1.1\r\n");
            out.print("Host: localhost:9090\r\n");
            out.print("Connection: close\r\n");
            out.print("Content-Type: application/json\r\n");
            out.print("Content-Length: " + contentLength + "\r\n");
            out.print("\r\n");
            out.print(body);
            out.flush();
            out.flush();
            socket.shutdownOutput();
            String response;
            while ((response = in.readLine()) != null && !response.isEmpty()) {
                System.out.println("Server says: " + response);
            }
            response = in.readLine();
            System.out.println(response);
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}