package server;

import exception.ServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static constants.AppConstants.SERVER_PORT;

public class Server implements Runnable {

    private static final int POOL_SIZE = getProcessorCount();
    private Socket socket;
    private ServerSocket serverSocket;
    private ServerStore serverStore;
    private boolean shutdown;

    private Server() {
        serverStore = new ServerStore();
    }

    private static int getProcessorCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static void main(String[] args) {
        Server server = new Server();
        Thread mainThread = new Thread(server);
        mainThread.start();
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);
        startListening(executorService);
    }

    private void startListening(ExecutorService executorService) {
        try {
            listen(executorService);
        } catch (IOException e) {
            // TODO: Add a logging framework
            executorService.shutdown();
            throw new ServerException("Failed to create a server socket.", e);
        }
    }

    private void listen(ExecutorService executorService) throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("SERVER: Started listening on: " + SERVER_PORT);
        while (!shutdown) {
            serveRequest(executorService);
        }
    }

    private void serveRequest(ExecutorService executorService) throws IOException {
        Socket clientSocket = serverSocket.accept();
        RequestHandler handler = new RequestHandler(serverStore, clientSocket);
        executorService.execute(handler);
    }
}
