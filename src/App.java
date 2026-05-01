import ParserMethods.ParserChecker;
import ParserMethods.ParserFunctions;
import dto.HttpRequest;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    private  final ParserFunctions parserFunctions;
    private  final ParserChecker parserChecker;
    public App(ParserFunctions parserFunctions, ParserChecker parserChecker) {
        this.parserFunctions = parserFunctions;

        this.parserChecker = parserChecker;
    }



    void run(){
        try(ServerSocket serverSocket = new ServerSocket(9090))
        {
            System.out.println("Server is running and waiting for client connection...");

            Socket socket  = serverSocket.accept();
            System.out.println("Client Connected");
            InputStream inputStream = socket.getInputStream();
            HttpRequest httpRequest = new HttpRequest();
            parserFunctions.JsonParser(httpRequest,inputStream);
            parserChecker.checker(httpRequest);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            out.print("HTTP/1.1 200 OK\r\n");
            out.print("Content-Type: text/html\r\n");
            out.print("\r\n");
            out.print("<h1>Hello</h1>");
            out.flush();

            socket.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
