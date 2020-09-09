package collections;

import java.util.HashMap;
import java.util.Map;

public class MapReverse {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        int size = 100;

        initialize(map, size);
        System.out.println(map);

        reverseMapRecursive(map);
        System.out.println(map);
    }

    private static <T> void reverseMapRecursive(Map<T, T> map) {
        if(map == null || map.size() == 0){
            return;
        }

        reverseEntry(map, map.keySet().iterator().next());
    }

    private static <T> void reverseEntry(Map<T, T> map, T key) {
        T value = map.get(key);
        if(map.containsKey(value)){
            reverseEntry(map, value);
        }
        map.remove(key);
        map.put(value, key);
    }

    private static void initialize(Map<Integer, Integer> map, int size) {
        for (int i = 0; i < size; ++i) {
            map.put(i, size + i);
        }
    }
}
