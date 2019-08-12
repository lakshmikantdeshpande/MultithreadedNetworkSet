package model;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Request {

    private int noOfArguments;
    private int operationId;
    private List<Integer> arguments;

    public Request(DataInputStream inputStream) throws IOException {
        noOfArguments = inputStream.readInt();
        operationId = inputStream.readInt();
        arguments = readArguments(inputStream, noOfArguments - 1);
    }

    private List<Integer> readArguments(DataInputStream inputStream, int noOfArguments) throws IOException {
        List<Integer> arguments = new ArrayList<>(noOfArguments);
        for (int i = 0; i < noOfArguments; i++) {
            int argument = inputStream.readInt();
            arguments.add(argument);
        }
        return arguments;
    }

    public int getNoOfArguments() {
        return noOfArguments;
    }

    int getOperationId() {
        return operationId;
    }

    List<Integer> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "Request{" +
                "noOfArguments=" + noOfArguments +
                ", operationId=" + operationId +
                ", arguments=" + arguments +
                '}';
    }
}
