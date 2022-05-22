package Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class KeysFinder {
    int base, power;
    int[] maxKey;
    HashMap<KeysGenerator, Boolean> keysGenerators;
    ArrayList<int[]> listOfKeys;
    List<int[]> results = new ArrayList<>();

    public KeysFinder(int base, int power, HashMap<KeysGenerator, Boolean> keysGenerators) {
        this.base = base;
        this.power = power;
        this.keysGenerators = keysGenerators;
        listOfKeys = new ArrayList<>();
    }

    private Callable<Void> toCallable(final Runnable runnable) {
        return new Callable<Void>() {
            @Override
            public Void call() {
                runnable.run();
                return null;
            }
        };
    }

    static class NumberedThread implements Runnable {
        private final int number;
        private final KeysGenerator kG;
        public NumberedThread(int number, KeysGenerator kG) {
            this.number = number;
            this.kG = kG;
        }

        @Override
        public void run() {
            kG.writeKeys(FileController.getWritersArray().get(number));
        }
    }

    public void mTFindF(boolean isPair) throws InterruptedException, ExecutionException, IOException {
        int counter = (int) keysGenerators.values().stream().filter(s -> s.equals(isPair)).count();
        List<Thread> threadList = new ArrayList<>();

        FileController.generate(counter+1, isPair);
        System.out.println(FileController.getWritersArray().size());

        long keysNum = KeysNumFinder.keysNum(base,power);
        List<Callable<Void>> callables = new ArrayList<>();
        ExecutorService exs = Executors.newCachedThreadPool();
        int c = 0;
        for (Map.Entry<KeysGenerator, Boolean> kG : keysGenerators.entrySet()) {
            if (kG.getValue() == isPair) {
                threadList.add(new Thread(new NumberedThread(c, kG.getKey())));
                c++;
            }
        }
        for (Thread th : threadList) {
            exs.execute(th);
        }
        exs.shutdown();
        while (!exs.isTerminated()) {
            exs.awaitTermination(100, TimeUnit.SECONDS);
        }
        FileController.closePrintWriters();
        int ccc = (int) Arrays.stream(Objects.requireNonNull(new File("Logs/").listFiles())).mapToInt(s -> {
            try {
                return (int) Files.lines(Paths.get(s.getPath())).parallel().count();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).sum();
        if (keysNum != ccc) {
            mTFindF(false);
        }

    }

    public List<int[]> mTFindS(boolean isPair) throws InterruptedException, ExecutionException {
        int counter = (int) keysGenerators.values().stream().filter(s -> s.equals(isPair)).count();
        System.out.println(counter);
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
