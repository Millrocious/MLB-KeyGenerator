package Controllers;

import java.util.*;

public class KeysFinder {
    int base, power;
    HashMap<KeysGenerator, Boolean> keysGenerators;
    ArrayList<int[]> listOfKeys;

    public KeysFinder(int base, int power, HashMap<KeysGenerator, Boolean> keysGenerators) {
        this.base = base;
        this.power = power;
        this.keysGenerators = keysGenerators;
        listOfKeys = new ArrayList<>();
    }

    public void find(boolean isPair) {
        for (Map.Entry<KeysGenerator, Boolean> kG : keysGenerators.entrySet()) {
            if (kG.getValue() == isPair) {
                List<int[]> tempList = kG.getKey().getKeys();
                if (!tempList.isEmpty()) {
                    listOfKeys.addAll(tempList);
                }
            }
        }
        if (KeysNumFinder.keysNum(base, power) != listOfKeys.size()) {
            find(false);
        }
        System.out.println(listOfKeys.size());
    }
}
