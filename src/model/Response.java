package model;

import server.ServerStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Response {

    private final List<Integer> arguments;
    private final int argumentCount;

    private Response(int argumentCount, List<Integer> arguments) {
        this.argumentCount = argumentCount;
        this.arguments = arguments;
    }

    public static Response process(Request request, ServerStore store) {
        List<Integer> arguments = request.getArguments();
        int operationId = request.getOperationId();
        switch (operationId) {
            case 1:
                add(store, arguments);
                return new Response(0, new ArrayList<>());
            case 2:
                remove(store, arguments);
                return new Response(0, new ArrayList<>());
            case 3:
                int size = getSize(store, arguments);
                return new Response(1, Collections.singletonList(size));
            case 4:
                int score = getKeyValue(store, arguments);
                return new Response(1, Collections.singletonList(score));
            case 5:
                return new Response(-1, new ArrayList<>());
            default:
                throw new IllegalArgumentException();
        }
    }

    private static int getKeyValue(ServerStore store, List<Integer> arguments) {
        int setId = arguments.get(0);
        int key = arguments.get(1);
        return store.getKeyValue(setId, key);
    }

    private static int getSize(ServerStore store, List<Integer> arguments) {
        int setId = arguments.get(0);
        return store.size(setId);
    }

    private static void remove(ServerStore store, List<Integer> arguments) {
        int setId = arguments.get(0);
        int key = arguments.get(1);
        store.remove(setId, key);
    }

    private static void add(ServerStore store, List<Integer> arguments) {
        int setId = arguments.get(0);
        int key = arguments.get(1);
        int score = arguments.get(2);
        store.put(setId, key, score);
    }

    @Override
    public String toString() {
        return "Response{" +
                "argumentCount=" + argumentCount +
                ", arguments=" + arguments +
                '}';
    }

    public int getArgumentCount() {
        return argumentCount;
    }

    public List<Integer> getArguments() {
        return arguments;
    }
}
