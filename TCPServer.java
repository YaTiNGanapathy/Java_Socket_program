import java.io.*;
import java.net.*;


class ClientHandler extends Thread {
    private Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String operation = in.readUTF();
            String result = "";

            switch (operation) {
                case "factorial":
                    int n = in.readInt();
                    result = "Factorial: " + factorial(n);
                    break;

                case "fibonacci":
                    int f = in.readInt();
                    result = "Fibonacci: " + fibonacci(f);
                    break;

                case "prime":
                    int p = in.readInt();
                    result = isPrime(p) ? p + " is Prime" : p + " is Not Prime";
                    break;

                case "reverse":
                    String str = in.readUTF();
                    result = new StringBuilder(str).reverse().toString();
                    break;

                default:
                    result = "Invalid Operation";
            }

            out.writeUTF(result);
            socket.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    // Functions
    static int factorial(int n) {
        if (n == 0) return 1;
        return n * factorial(n - 1);
    }

    static int fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0) return false;
        return true;
    }
}

// Main server
public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server running on port 5000...");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Client connected");

                // Create new thread
                ClientHandler client = new ClientHandler(socket);
                client.start();
            }

        } catch (Exception e) {
            System.out.println("Server Error: " + e);
        }
    }
}