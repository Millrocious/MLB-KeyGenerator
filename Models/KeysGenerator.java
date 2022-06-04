package Models;

import Controllers.KeyChecker;
import View.KeysNumTable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class KeysGenerator {
    int base;
    int power;
    int[] maxKey;
    public List<int[]> probablyKeys = new ArrayList<>();
    AtomicInteger pair = new AtomicInteger();
    int keyPair = 0;

    public KeysGenerator(int power, int base, int[] maxKey, int[] minKey) {
        this.base = base;
        this.power = power;
        this.maxKey = maxKey;

        generatePermutations(maxKey, 0, minKey);
    }

    public List<int[]> getKeys() {
        List<int[]> keys = new ArrayList<>();
        KeyChecker checker;
        checker = new KeyChecker(maxKey, base);
        long maxKeyNum = KeysNumTable.keysNum(base, power);

        for (int i = 0; i < probablyKeys.size(); i++) {
            if (checker.isKeyValid(probablyKeys.get(i), false)) {
                if (maxKeyNum != keys.size()) {
                    keys.add(probablyKeys.get(i));
                    int[] pairKey = checkAndFindPair(probablyKeys.get(i));
                    if (pairKey != null) keys.add(pairKey);
                } else {
                    return keys;
                }
            }
        }
        return keys;
    }

    public boolean checkIsKeysExist() {
        KeyChecker checker;
        checker = new KeyChecker(maxKey, base);

        if (base == 3) {
            for (int i = 0; i < 9; i++) {
                if (checker.isKeyValid(probablyKeys.get(i), false)) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < 9; i++) {
                if (checker.isKeyValid(probablyKeys.get(i), false)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void writeKeys(PrintWriter pw) {
        List<int[]> keys = new ArrayList<>();
        KeyChecker checker;
        checker = new KeyChecker(maxKey, base);
        long maxKeyNum = KeysNumTable.keysNum(base, power);

        for (int i = 0; i < probablyKeys.size(); i++) {
            if (checker.isKeyValid(probablyKeys.get(i), false)) {
                pw.println(Arrays.toString(probablyKeys.get(i)));
                int[] pairKey = checkAndFindPair(probablyKeys.get(i));
                if (pairKey != null) pw.println(Arrays.toString(pairKey));;
            }
        }
    }

    public void generatePermutations(int[] maxKey, int pos, int[] lastGeneratedKey) {
        if (pos == maxKey.length) {
            return;
        }

        for (int i = 0; i <= maxKey[pos]; i++) {
            int[] updatedKey = Arrays.copyOf(lastGeneratedKey, power);

            if (pos == maxKey.length - 1) {
                updatedKey[pos] = i;
                probablyKeys.add(updatedKey);
                //System.out.println(Arrays.toString(updatedKey));
            }
            generatePermutations(maxKey, pos + 1, updatedKey);

            if (++lastGeneratedKey[pos] > base - 1)
                break;
        }
    }

    public int[] checkAndFindPair(int[] key) {
        if (keyPair != key[0]) {
            int temp = 0;
            keyPair = key[0];

            for (int j = 1; j < base; j++) {
                int condition = (key[0] * j) % base;

                if (condition == 1 && key[0] != j) {
                    pair.set(j);
                    probablyKeys.removeIf(s -> s[0] == pair.get());
                    temp = j;
                }
            }

            if(temp == 0){
                pair.set(0);
            }
        }

        if (pair.get() > 0) {
            int k = base - pair.get();
            int[] pairKey = new int[power];
            pairKey[0] = pair.get();

            for (int p = power - 1; p > 0; p--) {
                pairKey[power - p] = (key[p] * k) % base;
            }
            return pairKey;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Models.KeysGenerator{" +
                "base=" + base +
                ", power=" + power +
                ", maxKey=" + Arrays.toString(maxKey) +
                ", probablyKeys=" + probablyKeys +
                ", pair=" + pair +
                ", keyPair=" + keyPair +
                '}';
    }
}