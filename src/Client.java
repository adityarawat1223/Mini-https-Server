import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9090);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Hello from client!");

            String response = in.readLine();
            System.out.println("Server says: " + response);

            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}