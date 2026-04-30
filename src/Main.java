import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(9090))
        {
            System.out.println("Server is running and waiting for client connection...");

            Socket socket  = serverSocket.accept();
            System.out.println("Client Connected");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            String message ;
            while((message = in.readLine()) != null && !message.isEmpty()){
            System.out.println(message);
            }

            out.println("Server got the message");
            socket.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}