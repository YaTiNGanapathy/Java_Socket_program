import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            Scanner sc = new Scanner(System.in);

            System.out.println("Choose operation:");
            System.out.println("1. factorial");
            System.out.println("2. fibonacci");
            System.out.println("3. prime");
            System.out.println("4. reverse");

            int choice = sc.nextInt();
            String op = "";

            
            switch (choice) {
                case 1:
                    op = "factorial";
                    break;
                case 2:
                    op = "fibonacci";
                    break;
                case 3:
                    op = "prime";
                    break;
                case 4:
                    op = "reverse";
                    break;
                default:
                    System.out.println("Invalid choice");
                    socket.close();
                    return;
            }

            out.writeUTF(op);

            
            switch (choice) {
                case 1:
                case 2:
                case 3:
                    System.out.print("Enter number: ");
                    int num = sc.nextInt();
                    out.writeInt(num);
                    break;

                case 4:
                    System.out.print("Enter string: ");
                    String str = sc.next();
                    out.writeUTF(str);
                    break;
            }

            // Receive result
            String response = in.readUTF();
            System.out.println("Server Response: " + response);

            socket.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}