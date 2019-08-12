package clients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 5555);

        // Insertion
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        outputStream.writeInt(4);
        outputStream.writeInt(1);
        outputStream.writeInt(1);
        outputStream.writeInt(1);
        outputStream.writeInt(15);

        // Get Size
        outputStream.writeInt(2);
        outputStream.writeInt(3);
        outputStream.writeInt(1);

        // Get Key Value
        outputStream.writeInt(3);
        outputStream.writeInt(4);
        outputStream.writeInt(1);
        outputStream.writeInt(1);

        // Removal
        outputStream.writeInt(3);
        outputStream.writeInt(2);
        outputStream.writeInt(1);
        outputStream.writeInt(1);

        // Disconnect
        outputStream.writeInt(1);
        outputStream.writeInt(5);

        while (true) {
            System.out.print(inputStream.readInt() + " ");
        }
    }
}
