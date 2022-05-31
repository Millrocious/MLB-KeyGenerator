package Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

public class KeysFinder {
    int base, power;
    int[] maxKey;
    Map<KeysGenerator, Boolean> keysGenerators;
    ArrayList<int[]> listOfKeys;
    List<int[]> results = new ArrayList<>();

    public KeysFinder(int base, int power, Map<KeysGenerator, Boolean> keysGenerators) {
        this.base = base;
        this.power = power;
        this.keysGenerators = keysGenerators;
        listOfKeys = new ArrayList<>();
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
        //System.out.println(FileController.getWritersArray().size());

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

    public void startFinder () throws IOException, ExecutionException, InterruptedException {
        new File("../Logs/").mkdir();
        new File("../Final/").mkdir();
        long startTime = System.currentTimeMillis();
        mTFindF(true);
        long endTime = System.currentTimeMillis();


        FileController.concatFiles();

        System.out.println("Total execution time: " + ((endTime / 1000)-(startTime / 1000)) + "s");
    }

    public void isKeysExist() {
        HashMap<Integer, Boolean> isExists = new HashMap<>();
        for (Map.Entry<KeysGenerator, Boolean> kG : keysGenerators.entrySet()) {
            isExists.put(kG.getKey().probablyKeys.get(0)[0], kG.getKey().checkIsKeysExist());
        }
        System.out.println(isExists);
    }
}
