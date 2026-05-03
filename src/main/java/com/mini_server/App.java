package com.mini_server;

import com.mini_server.Mapper.HandlerMapper;
import com.mini_server.ParserMethods.ParserChecker;
import com.mini_server.ParserMethods.ParserFunctions;
import com.mini_server.ReponseMethods.Responder;
import com.mini_server.dto.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class App {
    private  final ParserFunctions parserFunctions;
    private  final ParserChecker parserChecker;
    private  final Responder responder;
    private  final HandlerMapper handlerMapper;
    public App(ParserFunctions parserFunctions, ParserChecker parserChecker, Responder responder, HandlerMapper handlerMapper) {
        this.parserFunctions = parserFunctions;
        this.parserChecker = parserChecker;
        this.responder = responder;
        this.handlerMapper = handlerMapper;
    }

    void run(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                4,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy()

        );
        try(ServerSocket serverSocket = new ServerSocket(9090))
        {
            System.out.println("Server is running and waiting for client connection...");
            while(true){
                Socket socket  = serverSocket.accept();

                executor.execute(() -> {
                   try(socket){
                       System.out.println("Client Connected");

                       InputStream inputStream = socket.getInputStream();

                       HttpRequest httpRequest = new HttpRequest();
                       parserFunctions.JsonParser(httpRequest, inputStream);
                       parserChecker.checker(httpRequest);
                       handlerMapper.Mapper(httpRequest);

                       PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                       responder.respond(httpRequest, out);
                       out.flush();
                   }catch (Exception e){
                       HttpRequest err = new HttpRequest();
                       err.version = "HTTP/1.1";
                       err.status = 500;
                       err.response = "{\"error\":\"Internal Server Error\"}";

                       PrintWriter out = null;
                       try {
                           out = new PrintWriter(socket.getOutputStream(), true);
                       } catch (IOException ex) {
                           throw new RuntimeException(ex);
                       }
                       responder.respond(err, out);
                        System.out.println(e.getMessage());
                   }
                });

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
