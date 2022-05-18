import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Dispatcher {
    public static void main(String[] args) throws FileNotFoundException {
        int base = 3;
        int power = 3;

        FileController.generate();
        FileController.deleteTempFiles();

        // for debug
        KeyChecker debug = new KeyChecker(new int[]{10, 10, 10}, 11);
        // enter any key here
        System.out.println("\n" + debug.isKeyValid(new int[]{2, 1, 8}, true));
        System.out.println();

        // result
        KeysGenerator keysGenerator = new KeysGenerator(power, base);
        List<int[]> keys = keysGenerator.getKeys();
        System.out.println("Number of keys for " + base + "^" + power + ": " + keys.size());

        for (int[] key : keys) {
            System.out.println(Arrays.toString(key));
        }
    }
}

class KeyChecker {
    int[] phaseArray;
    int base;
    int power;
    double length;

    public KeyChecker(int[] phase, int base) {
        this.phaseArray = phase;
        this.base = base;
        this.power = phase.length;
        this.length = Math.pow(base, power) - 1;
    }

    public boolean isKeyValid(int[] keyArray, boolean debugMode) {
        int[] beginningPhase = Arrays.copyOf(phaseArray, power);
        for (int i = 0; i < length; i++) {
            int res = 0;
            for (int j = 0; j < power; j++) {
                res += keyArray[j] * phaseArray[j];
            }
            res %= base;

            // print sequence if you want

            if (debugMode) {
                System.out.print(res);
            }

            for (int j = 0; j < power - 1; ) {
                phaseArray[j] = phaseArray[++j];
            }
            phaseArray[power - 1] = res;

            if (Arrays.equals(phaseArray, beginningPhase) && i < length - power - 1) {
                return false;
            }
        }

        return Arrays.equals(phaseArray, beginningPhase);
    }
}

class KeysGenerator {
    int base;
    int power;
    int[] maxKey;
    List<int[]> probablyKeys = new ArrayList<>();
    AtomicInteger pair = new AtomicInteger();
    int keyPair = 0;

    public KeysGenerator(int power, int base) {
        this.base = base;
        this.power = power;

        int[] minKey = new int[power];
        maxKey = new int[power];
        minKey[0] = 1;

        for (int i = 0; i < power; i++) {
            maxKey[i] = base - 1;
        }

        generatePermutations(maxKey, 0, minKey);
    }

    public List<int[]> getKeys() {
        List<int[]> keys = new ArrayList<>();
        KeyChecker checker;
        checker = new KeyChecker(maxKey, base);

        for (int i = 0; i < probablyKeys.size(); i++) {
            if (checker.isKeyValid(probablyKeys.get(i), false)) {
                keys.add(probablyKeys.get(i));
                int[] pairKey = checkAndFindPair(probablyKeys.get(i));
                if (pairKey != null) keys.add(pairKey);
            }
        }
        return keys;
    }

    public void generatePermutations(int[] maxKey, int pos, int[] lastGeneratedKey) {
        if (pos == maxKey.length)
            return;

        for (int i = 0; i <= maxKey[pos]; i++) {
            int[] updatedKey = Arrays.copyOf(lastGeneratedKey, power);

            if (pos == maxKey.length - 1) {
                updatedKey[pos] = i;
                probablyKeys.add(updatedKey);
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
}