package Controllers;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class KeysFinder {
    int base, power;
    HashMap<KeysGenerator, Boolean> keysGenerators;
    ArrayList<int[]> listOfKeys;
    List<int[]> results = new ArrayList<>();

    public KeysFinder(int base, int power, HashMap<KeysGenerator, Boolean> keysGenerators) {
        this.base = base;
        this.power = power;
        this.keysGenerators = keysGenerators;
        listOfKeys = new ArrayList<>();
    }

    public List<int[]> mTFindS(boolean isPair) throws InterruptedException, ExecutionException {
        long keysNum = KeysNumFinder.keysNum(base,power);
        ExecutorService exs = Executors.newCachedThreadPool();
        ArrayList<Callable<List<int[]>>> tasks = new ArrayList<>();
        for (Map.Entry<KeysGenerator, Boolean> kG : keysGenerators.entrySet()) {
            if (kG.getValue() == isPair) {
                tasks.add(() -> {
                    return kG.getKey().getKeys();//System.out.println(listOfKeysTS);
                });
            }
        }
        List<Future<List<int[]>>> futures = exs.invokeAll(tasks);
        for (Future<List<int[]>> future : futures) {
            results.addAll(future.get());
        }
        exs.shutdown();
        if (keysNum != results.size()) {
            mTFindS(false);
        }

        return results;
    }

//    public void mTFind(boolean isPair) throws InterruptedException {
//        ExecutorService exs = Executors.newCachedThreadPool();
//
//        for (Map.Entry<KeysGenerator, Boolean> kG : keysGenerators.entrySet()) {
//            if (kG.getValue() == isPair) {
//                exs.execute(() -> {
//                    synchronized(listOfKeysTS) {
//                        listOfKeysTS.add(kG.getKey().getKeys());
//                    }
//                });
//            }
//        }
//        exs.shutdown();
//        exs.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
////        if (KeysNumFinder.keysNum(base, power) != listOfKeys.size()) {
////            mTFind(false);
////        }
//
//        System.out.println(listOfKeys.size());
//    }


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
