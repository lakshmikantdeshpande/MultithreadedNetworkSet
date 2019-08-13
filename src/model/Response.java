package model;

import server.Store;

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

    public static Response process(Request request, Store store) {
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
            case 6:
                List<Integer> range = getRange(store, arguments);
                return new Response(range.size(), range);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static int getKeyValue(Store store, List<Integer> arguments) {
        int setId = arguments.get(0);
        int key = arguments.get(1);
        return store.getKeyValue(setId, key);
    }

    private static int getSize(Store store, List<Integer> arguments) {
        int setId = arguments.get(0);
        return store.size(setId);
    }

    private static void remove(Store store, List<Integer> arguments) {
        int setId = arguments.get(0);
        int key = arguments.get(1);
        store.remove(setId, key);
    }

    private static void add(Store store, List<Integer> arguments) {
        int setId = arguments.get(0);
        int key = arguments.get(1);
        int score = arguments.get(2);
        store.put(setId, key, score);
    }

    private static List<Integer> getRange(Store store, List<Integer> arguments) {
        final int size = arguments.size();
        int lower = arguments.get(size - 2);
        int upper = arguments.get(size - 1);
        int[] setIds = getSetIdsFromArguments(size, arguments);
        return store.getRange(lower, upper, setIds);
    }

    private static int[] getSetIdsFromArguments(int size, List<Integer> arguments) {
        List<Integer> setIdList = arguments.subList(0, size - 2);
        int[] setIds = new int[setIdList.size()];
        for (int i = 0; i < setIdList.size(); i++) {
            setIds[i] = setIdList.get(i);
        }
        return setIds;
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
