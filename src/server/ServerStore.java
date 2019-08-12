package server;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ServerStore {

    private final Map<Integer, Integer> setIdToScoreMap;
    private final Map<Integer, Set<Integer>> setIdToSetMap;

    ServerStore() {
        setIdToSetMap = Collections.synchronizedMap(new TreeMap<>());
        setIdToScoreMap = new TreeMap<>();
    }

    public void put(int setId, int key, int score) {
        synchronized (setIdToScoreMap) {
            boolean setExists = doesSetExist(setId);
            if (setExists) {
                addKey(setId, key, score);
            } else {
                setIdToScoreMap.put(setId, score);
                Set<Integer> set = new TreeSet<>(Collections.singletonList(key));
                setIdToSetMap.put(key, set);
            }
        }
    }

    private void addKey(int setId, int key, int score) {
        Set<Integer> set = setIdToSetMap.get(setId);
        if (set.contains(key)) {
            setIdToScoreMap.put(setId, score);
        } else {
            set.add(key);
        }
    }

    public void remove(int setId, int key) {
        synchronized (setIdToScoreMap) {
            boolean setExists = doesSetExist(setId);
            if (setExists) {
                Set<Integer> set = setIdToSetMap.get(setId);
                set.remove(key);
            }
        }
    }

    public int size(int setId) {
        synchronized (setIdToScoreMap) {
            boolean setExists = doesSetExist(setId);
            if (setExists) {
                return setIdToSetMap.get(setId).size();
            }
            return 0;
        }
    }

    public int getKeyValue(int setId, int key) {
        synchronized (setIdToScoreMap) {
            if (!doesSetExist(setId)) {
                return 0;
            }
            if (setIdToSetMap.get(setId).contains(key)) {
                return setIdToScoreMap.get(key);
            }
            return 0;
        }
    }

    private boolean doesSetExist(int setId) {
        return setIdToScoreMap.containsKey(setId);
    }

}
