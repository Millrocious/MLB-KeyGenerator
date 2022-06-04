package Controllers;

import Models.KeysGenerator;
import View.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
                    else if (!pairs.containsKey(i) && !pairs.containsKey(j)) {
                        View.messages.add(i + " + " + j);
                        pairs.put(i, true);
                    }
                }
            }
        }
        View.messages.add("base = " + base + "------------------");
        return pairs;
    }

    public static Map<int[], Boolean> generatePairsArray(Map<Integer, Boolean> basePair, int power) {
        return basePair.entrySet().stream()
                .collect(Collectors.toMap(
                        k -> StartKeyGenerator.generateMinKeyWNPos(k.getKey(), power),
                        Map.Entry::getValue));
    }

    public static Map<Integer, int[]> generatePairsArray(HashMap<Integer, ArrayList<Integer>> basePair, int power) {
        int counter = 0;
        Map<Integer, int[]> tempMap = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : basePair.entrySet()) {
            tempMap.put(entry.getKey(), StartKeyGenerator.generateMinKeyWNPos(entry.getValue().get(counter), power));
            counter++;
        }
        return tempMap;
    }

    public static Map<Integer, int[]> generatePairsArray(HashMap<Integer, ArrayList<ArrayList<Integer>>> basePair, int power, int pair) {
        int counter = 0;
        Map<Integer, int[]> tempMap = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> entry : basePair.entrySet()) {
            tempMap.put(entry.getKey(), StartKeyGenerator.generateMinKeyWNPos(entry.getValue().get(pair).get(counter), power));
            counter++;
        }
        return tempMap;
    }

    public static void showHashMap(HashMap<int[], Boolean> arrayPairs) {
        arrayPairs.forEach((key, value) -> System.out.println(Arrays.toString(key) + " " + value));
    }

    public static void showHashMap(Map<int[], Boolean> arrayPairs) {
        arrayPairs.forEach((key, value) -> System.out.println(Arrays.toString(key) + " " + value));
    }

    public static Map<KeysGenerator, Boolean> generateGenerators(int base, int power, Map<int[], Boolean> arrayPairs) {
        return arrayPairs.entrySet().stream()
                .collect(Collectors.toMap(
                        k -> new KeysGenerator(power, base, StartKeyGenerator.generateMaxKey(base, power, 0), k.getKey()),
                        Map.Entry::getValue));
    }

    public static Map<KeysGenerator, Boolean> generateGeneratorsWD(int base, int power, Map<int[], Boolean> arrayPairs) {
        return arrayPairs.entrySet().stream()
                .collect(Collectors.toMap(
                        k -> new KeysGenerator(power, base, StartKeyGenerator.generateMaxKey(base, power, 1), k.getKey()),
                        Map.Entry::getValue));
    }

    public static void findAll() {
        for (int base : bases) {
            findOne(base);
            pairs.clear();
        }
    }
}
