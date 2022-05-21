import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dispatcher {
    public static void main(String[] args) throws FileNotFoundException {
        int base = 7;
        int power = 3;

        StartKeyGenerator.base = base;
        StartKeyGenerator.power = power;
        HashMap<Integer, Boolean> basePair = new HashMap<>(BasePairsController.findOne(base));
        HashMap<int[], Boolean> arrayPairs = new HashMap<>();

        ArrayList<KeysGenerator> keysGenerators = new ArrayList<>();

        //FileController.generate();
        //FileController.deleteTempFiles();

        BasePairsController.findAll();
        View.show();

        //ArrayList<Runnable> threads = new ArrayList<>();
//        for (int i = 0; i < FileController.getWritersArray().size(); i++) {
//            threads.add(() -> {
//                KeysGenerator.generatePermutationsCustom();
//            });
//        }
//
//        ExecutorService exs = Executors.newCachedThreadPool();
//        exs.submit(() -> {
//
//        });

        // for debug
//        KeyChecker debug = new KeyChecker(new int[]{10, 10, 10}, 11);
//        // enter any key here
//        System.out.println("\n" + debug.isKeyValid(new int[]{2, 1, 8}, true));
//        System.out.println();


        for (Integer entry : basePair.keySet()) {
            if (!basePair.get(entry)) {
                arrayPairs.put(StartKeyGenerator.generateMinKey(entry), false);
            } else {
                arrayPairs.put(StartKeyGenerator.generateMinKey(entry), true);
            }
        }

        for (Map.Entry<int[], Boolean> entry : arrayPairs.entrySet()) {
            System.out.println(Arrays.toString(entry.getKey()) + " " + entry.getValue());
        }

        for (Map.Entry<int[], Boolean> entry : arrayPairs.entrySet()) {
            if (entry.getValue()) {
                keysGenerators.add(new KeysGenerator(power, base, StartKeyGenerator.generateMaxKey(), entry.getKey()));
            }
        }
        System.out.println(keysGenerators);

        int count = 0;
        for (KeysGenerator kG : keysGenerators) {
            List<int[]> tempList = kG.getKeys();
            kG.getKeys().forEach(keys -> System.out.println(Arrays.toString(keys)));
            count = tempList.size();
            //System.out.println(kG.getKeys());
        }
        System.out.println(count);




        // result
        //KeysGenerator kG1 = new KeysGenerator(power, base, new int[] {0, 4, 4}, new int[] {1, 0, 0});
        //System.out.println("------");
        //KeysGenerator kG2 = new KeysGenerator(power, base, new int[] {0, 4, 4}, new int[] {2, 0, 0});
        //System.out.println("------");
        //KeysGenerator kG3 = new KeysGenerator(power, base, new int[] {0, 4, 4}, new int[] {3, 0, 0});
        //System.out.println("------");
        //KeysGenerator kG4 = new KeysGenerator(power, base, new int[] {0, 4, 4}, new int[] {4, 0, 0});
        //List<int[]> keys1 = kG1.getKeys();
        //List<int[]> keys2 = kG2.getKeys();
        //List<int[]> keys3 = kG3.getKeys();
        //List<int[]> keys4 = kG4.getKeys();
        //List<int[]> keys2 = kG2.getKeys();

//        for (int[] key : keys2) {
//            System.out.println(Arrays.toString(key));
//        }
//
//        System.out.println(keys2.size());



//        for (int[] key : keys2) {
//            System.out.println(Arrays.toString(key));
//        }



        //System.out.println((555 / 4 + 1) * 4);

        //System.out.println(Arrays.toString(divideNumber(base, power, 4)));

        //System.out.println(Arrays.toString(nums));

        //KeysGenerator.generatePermutationsCustom(base, power, new int[] {0, 2, 2}, 0, new int[]{1, 0, 0});

//        for (int[] key : Keys.keysArray) {
//            System.out.println(Arrays.toString(key));
//        }

//        List<List<int[]>> newList = partition(keysGenerator.probablyKeys,
//                keysGenerator.probablyKeys.size() / 4 + 1);

//        for (List<int[]> key : newList) {
//            for (int[] ints : key) {
//                System.out.print(Arrays.toString(ints) + " ");
//            }
//            System.out.println();
//            //System.out.println(key.size());
//        }
        //System.out.println("Number of keys for " + base + "^" + power + ": " + keys.size());



//        for (int[] key : keys) {
//            System.out.println(Arrays.toString(key));
//        }
    }
}

class Keys {
    public static ArrayList<int[]> keysArray = new ArrayList<>();
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
        return "KeysGenerator{" +
                "base=" + base +
                ", power=" + power +
                ", maxKey=" + Arrays.toString(maxKey) +
                ", probablyKeys=" + probablyKeys +
                ", pair=" + pair +
                ", keyPair=" + keyPair +
                '}';
    }
}