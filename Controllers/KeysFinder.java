package Controllers;

import java.util.*;

public class KeysFinder {
    HashMap<KeysGenerator, Boolean> keysGenerators;
    ArrayList<int[]> listOfKeys;

    public KeysFinder(HashMap<KeysGenerator, Boolean> keysGenerators) {
        this.keysGenerators = keysGenerators;
        listOfKeys = new ArrayList<>();
    }

    public void find(boolean isPair) {
        int count = 0;
        for (Map.Entry<KeysGenerator, Boolean> kG : keysGenerators.entrySet()) {
            if (kG.getValue() == isPair) {
                List<int[]> tempList = kG.getKey().getKeys();
                if (!tempList.isEmpty()) {
                    listOfKeys.addAll(tempList);
                }
            } else {
                find(false);
            }
            //System.out.println(kG.getKeys());
        }
        System.out.println(count);
    }
}
