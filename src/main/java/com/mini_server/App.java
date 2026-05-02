package com.mini_server;

import com.mini_server.Mapper.HandlerMapper;
import com.mini_server.ParserMethods.ParserChecker;
import com.mini_server.ParserMethods.ParserFunctions;
import com.mini_server.ReponseMethods.Responder;
import com.mini_server.dto.HttpRequest;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
        try(ServerSocket serverSocket = new ServerSocket(9090))
        {
            System.out.println("Server is running and waiting for client connection...");

            Socket socket  = serverSocket.accept();
            System.out.println("com.mini_server.Client Connected");
            InputStream inputStream = socket.getInputStream();
            HttpRequest httpRequest = new HttpRequest();
            parserFunctions.JsonParser(httpRequest,inputStream);
            parserChecker.checker(httpRequest);
            handlerMapper.Mapper(httpRequest);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            responder.respond(httpRequest,out);

            socket.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
