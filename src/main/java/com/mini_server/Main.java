package  com.mini_server;
import com.mini_server.Mapper.HandlerMapper;
import com.mini_server.ParserMethods.*;
import com.mini_server.ReponseMethods.Responder;


public class Main {
    public static void main(String[] args) {

        ParserFunctions parserFunctions = new ParserFunctions();
        ParserChecker parserChecker = new ParserChecker();
        Responder responder = new Responder();
        HandlerMapper handlerMapper = new HandlerMapper();
        App app = new App(parserFunctions,parserChecker,responder,handlerMapper);
        app.run();
    }
}