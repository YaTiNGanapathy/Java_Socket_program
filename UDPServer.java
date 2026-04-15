import java.net.*;

public class UDPServer {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(5001)) {
            System.out.println("UDP Server is running on port 5001...");

            while (true) {
                byte[] receiveBuffer = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String request = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                System.out.println("Client requested: " + request);

                // Exit condition (optional)
                if (request.equalsIgnoreCase("exit")) {
                    System.out.println("Shutting down server...");
                    break;
                }

                String result = calculate(request);
                System.out.println("Result: " + result);

                byte[] sendData = result.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String calculate(String input) {
        try {
            String[] parts = input.trim().split("\\s+");

            if (parts.length < 3) {
                parts = input.split("(?<=[-+*/])|(?=[-+*/])");
            }

            double num1 = Double.parseDouble(parts[0].trim());
            String op = parts[1].trim();
            double num2 = Double.parseDouble(parts[2].trim());

            switch (op) {
                case "+": return "Result: " + (num1 + num2);
                case "-": return "Result: " + (num1 - num2);
                case "*": return "Result: " + (num1 * num2);
                case "/":
                    if (num2 == 0) return "Error: Division by zero";
                    return "Result: " + (num1 / num2);
                default: return "Error: Unknown operator";
            }

        } catch (Exception e) {
            return "Invalid input! Example: 10 + 5";
        }
    }
}