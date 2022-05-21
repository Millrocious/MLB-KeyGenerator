package Controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BasePairsController {
    private static final int[] bases = new int[] {3, 5, 7, 11, 13, 17, 19};
    private static HashMap<Integer, Boolean> pairs = new HashMap<>();

    public static HashMap<Integer, Boolean> findOne(int base) {
        for (int i = 1; i < base; i++) {
            for (int j = 1; j < base; j++) {
                if (((j*i)%base) == 1) {
                    if (i == j) {
                        View.messages.add(i + " + " + j);
                        pairs.put(i, false);
                    }
                    if (!pairs.containsKey(i) && !pairs.containsKey(j) && i != j) {
                        View.messages.add(i + " + " + j);
                        pairs.put(i, true);
                    }
                }
            }
        }
        View.messages.add("base = " + base + "------------------");
        return pairs;
    }

    public HashMap<int[], Boolean> generatePairsArray(HashMap<Integer, Boolean> basePair) {
        HashMap<int[], Boolean> arrayPairs = new HashMap<>();
        for (Integer entry : basePair.keySet()) {
            if (!basePair.get(entry)) {
                arrayPairs.put(StartKeyGenerator.generateMinKey(entry), false);
            } else {
                arrayPairs.put(StartKeyGenerator.generateMinKey(entry), true);
            }
        }
        return arrayPairs;
    }

    public void showHashMap(HashMap<int[], Boolean> arrayPairs) {
        for (Map.Entry<int[], Boolean> entry : arrayPairs.entrySet()) {
            System.out.println(Arrays.toString(entry.getKey()) + " " + entry.getValue());
        }
    }

    public static void findAll() {
        for (int base : bases) {
            findOne(base);
            pairs.clear();
        }
    }
}