import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();
             Scanner sc = new Scanner(System.in)) {

            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 5001;

            System.out.println("UDP Calculator Client");
            System.out.println("Format: 10 + 5 OR 10+5");
            System.out.println("Type 'exit' to stop");

            while (true) {
                System.out.print("\nEnter expression: ");
                String message = sc.nextLine().trim();

               
                if (message.equalsIgnoreCase("exit")) {
                    byte[] exitData = "exit".getBytes();
                    DatagramPacket exitPacket =
                            new DatagramPacket(exitData, exitData.length, serverAddress, serverPort);
                    socket.send(exitPacket);
                    break;
                }

                
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                socket.send(sendPacket);

                
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveBuffer, receiveBuffer.length);

                socket.receive(receivePacket);

                String response = new String(
                        receivePacket.getData(),
                        0,
                        receivePacket.getLength()
                );

                System.out.println("Server Response: " + response);
            }

            System.out.println("Client closed.");

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}