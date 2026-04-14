import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String msg;

            while (true) {
                // Send message
                System.out.print("You: ");
                msg = userInput.readLine();
                output.println(msg);

                if (msg.equalsIgnoreCase("exit")) break;

                // Receive reply
                String reply = input.readLine();
                System.out.println("Server: " + reply);

                if (reply.equalsIgnoreCase("bye")) break;
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}