package clients;

import exception.ClientException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 5555);

        // Insertion
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        writeToSocket(outputStream, 4, 1, 1, 1, 15);
        writeToSocket(outputStream, 4, 1, 2, 2, 30);

        // Get Size
        writeToSocket(outputStream, 2, 3, 1);

        // Get Key Value
        writeToSocket(outputStream, 3, 4, 1, 1);
        writeToSocket(outputStream, 3, 4, 2, 2);

        // Range
        writeToSocket(outputStream, 6, 6, 1, 2, 3, 12, 32);

        // Removal
        writeToSocket(outputStream, 3, 2, 1, 1);

        // Disconnect
        writeToSocket(outputStream, 1, 5);

        while (true) {
            System.out.print(inputStream.readInt() + " ");
        }
    }

    private static void writeToSocket(DataOutputStream outputStream, int... values) throws IOException {
        Arrays.stream(values).forEach(value -> {
            try {
                outputStream.writeInt(value);
            } catch (IOException e) {
                throw new ClientException("Failed to write to socket", e);
            }
        });
    }

}
