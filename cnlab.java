
import java.util.Scanner;
import java.io.*;

public class CRC1 {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        // Sender side
        System.out.print("Enter message bits: ");
        String message = sc.nextLine();

        System.out.print("Enter generator: ");
        String generator = sc.nextLine();

        int data[] = new int[message.length() + generator.length() - 1];
        int divisor[] = new int[generator.length()];

        // Convert message into integer array
        for (int i = 0; i < message.length(); i++)
            data[i] = Integer.parseInt(message.charAt(i) + "");

        // Convert generator into integer array
        for (int i = 0; i < generator.length(); i++)
            divisor[i] = Integer.parseInt(generator.charAt(i) + "");

        // CRC calculation using XOR
        for (int i = 0; i < message.length(); i++) {
            if (data[i] == 1) {
                for (int j = 0; j < divisor.length; j++)
                    data[i + j] ^= divisor[j];
            }
        }

        // Display checksum code
        System.out.print("The checksum code is: ");

        for (int i = 0; i < message.length(); i++)
            data[i] = Integer.parseInt(message.charAt(i) + "");

        for (int i = 0; i < data.length; i++)
            System.out.print(data[i]);

        System.out.println();

        // Receiver side
        System.out.print("Enter checksum code: ");
        message = sc.nextLine();

        System.out.print("Enter generator: ");
        generator = sc.nextLine();

        data = new int[message.length() + generator.length() - 1];
        divisor = new int[generator.length()];

        // Convert received message into integer array
        for (int i = 0; i < message.length(); i++)
            data[i] = Integer.parseInt(message.charAt(i) + "");

        // Convert generator into integer array
        for (int i = 0; i < generator.length(); i++)
            divisor[i] = Integer.parseInt(generator.charAt(i) + "");

        // Calculate remainder at receiver side
        for (int i = 0; i < message.length(); i++) {
            if (data[i] == 1) {
                for (int j = 0; j < divisor.length; j++)
                    data[i + j] ^= divisor[j];
            }
        }

        // Check whether received data is valid
        boolean valid = true;

        for (int i = 0; i < data.length; i++) {
            if (data[i] == 1) {
                valid = false;
                break;
            }
        }

        if (valid == true)
            System.out.println("Data stream is valid");
        else
            System.out.println("Data stream is invalid. CRC error occurred.");

        sc.close();
    }
}
