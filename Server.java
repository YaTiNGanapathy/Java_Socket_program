import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server started...");

            Socket socket = server.accept();
            System.out.println("Client connected!");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String msg;

            while (true) {
                // Receive from client
                msg = input.readLine();
                System.out.println("Client: " + msg);

                if (msg.equalsIgnoreCase("exit")) break;

                // Send reply
                System.out.print("You: ");
                String reply = userInput.readLine();
                output.println(reply);

                if (reply.equalsIgnoreCase("bye")) break;
            }

            socket.close();
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}