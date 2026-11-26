 java.net.*;
import java.util.Scanner;

public class UDPServer {
    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(5000);
        Scanner sc = new Scanner(System.in);

        InetAddress clientIP =
            InetAddress.getByName("192.168.1.10");

        System.out.println("Server Started...");
        System.out.println("Type messages to chat:");

        // Receive messages from client
        Thread receiver = new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];

                while (true) {
                    DatagramPacket packet =
                        new DatagramPacket(buffer, buffer.length);

                    socket.receive(packet);

                    String msg = new String(
                        packet.getData(), 0, packet.getLength());

                    System.out.println("\nClient: " + msg);
                }
            } catch (Exception e) {
                System.out.println("Connection closed.");
            }
        });

        receiver.setDaemon(true);
        receiver.start();

        while (true) {
            String msg = sc.nextLine();

            if (msg.equalsIgnoreCase("exit")) {
                break;
            }

            byte[] data = msg.getBytes();

            DatagramPacket packet = new DatagramPacket(
                data, data.length, clientIP, 5001);

            socket.send(packet);
        }

        socket.close();
        sc.close();
    }



 java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(5001);
        Scanner sc = new Scanner(System.in);

        InetAddress serverIP =
            InetAddress.getByName("192.168.1.20");

        System.out.println("Client Started...");
        System.out.println("Waiting for server messages...");

        // Receive messages from server
        Thread receiver = new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];

                while (true) {
                    DatagramPacket packet =
                        new DatagramPacket(buffer, buffer.length);

                    socket.receive(packet);

                    String msg = new String(
                        packet.getData(), 0, packet.getLength());

                    System.out.println("\nServer: " + msg);
                }
            } catch (Exception e) {
                System.out.println("Connection closed.");
            }
        });

        receiver.setDaemon(true);
        receiver.start();

        while (true) {
            String msg = sc.nextLine();

            if (msg.equalsIgnoreCase("exit")) {
                break;
            }

            byte[] data = msg.getBytes();

            DatagramPacket packet = new DatagramPacket(
                data, data.length, serverIP, 5000);

            socket.send(packet);
        }

        socket.close();
        sc.close();
    }
}