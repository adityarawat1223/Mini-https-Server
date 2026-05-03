# Mini-Http-Server

[![Java](https://img.shields.io/badge/Java-17-orange)]

## Overview

A miniature Http Server , Following HTTP protocol
with a basic parser , responder and error handling basically
i made this for getting proper learning for some of springboot
internals and some http learning 

## Prerequisites 

1. Java 17
2. Jackson (Parsing Body to Controller Required Parameter)
3. Gradle ( You can use Maven)

## Working 
```Bash
java Main.java
java Client.java
```

## Adding Controllers 

Currently This Project Only Support
Manual Adding of Controllers Add your Controller
To Test Controller And Adder to Add them

In MethodClass Directory there is Test Controller

```java
package com.mini_server.MethodClass;

import com.mini_server.dto.Message;
// Add Your Controller Here
public class TestController {

    public String hello() {
        return "Hello from GET!";
    }

    public Message echo(Message msg) {
        return msg;
    }
}
```
```java
 public void Adder(){
    // Add them to Mapper Here
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
    };
```

## Response

Add Request In HTTP protocol in Client.java or
Checker Will reject it
```java
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
    socket.shutdownOutput();
```

Response Will be Like this
 Server Says is Added for Readability ,Server Returns Response
 in Proper Http Protocol 
```json

"Server says: HTTP/1.1 200 OK"
"Server says: Content-Type: application/json"
"Server says: Content-Length: 17"
"Server says: Connection: close"
"Hello from GET!"
```


## Future Idea

I will add a auto controller Adder in future Maybe
