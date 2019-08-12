package server;

import model.Request;
import model.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static constants.AppConstants.EXIT_CODE;

public class RequestHandler implements Runnable {

    private final ServerStore store;
    private final Socket socket;

    RequestHandler(ServerStore store, Socket socket) {
        this.store = store;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            handleRequests(input, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequests(DataInputStream input, DataOutputStream output) throws IOException {
        boolean shutdown = false;

        while (!shutdown) {
            Request request = new Request(input);
            Response response = Response.process(request, store);
            System.out.println(response);
            if (response.getArgumentCount() == EXIT_CODE) {
                shutdown = true;
            } else {
                writeResponse(response, output);
            }
        }
    }

    private void writeResponse(Response response, DataOutputStream output) throws IOException {
        output.writeInt(response.getArgumentCount());
        List<Integer> arguments = response.getArguments();
        for (Integer argument : arguments) {
            output.writeInt(argument);
        }
        output.flush();
    }
}
