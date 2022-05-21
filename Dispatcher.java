import Controllers.BasePairsController;
import View.View;
import models.KeysGenerator;
import models.StartKeyGenerator;

import java.io.FileNotFoundException;
import java.util.*;

public class Dispatcher {
    public static void main(String[] args) throws FileNotFoundException {
        int base = 11;
        int power = 3;

        StartKeyGenerator.base = base;
        StartKeyGenerator.power = power;
        HashMap<Integer, Boolean> basePair = new HashMap<>(BasePairsController.findOne(base));
        HashMap<int[], Boolean> arrayPairs = new HashMap<>();

        ArrayList<KeysGenerator> keysGenerators = new ArrayList<>();

        //Controllers.FileController.generate();
        //Controllers.FileController.deleteTempFiles();

        BasePairsController.findAll();
        View.show();

        //ArrayList<Runnable> threads = new ArrayList<>();
//        for (int i = 0; i < Controllers.FileController.getWritersArray().size(); i++) {
//            threads.add(() -> {
//                models.KeysGenerator.generatePermutationsCustom();
//            });
//        }
//
//        ExecutorService exs = Executors.newCachedThreadPool();
//        exs.submit(() -> {
//
//        });

        // for debug
//        models.KeyChecker debug = new models.KeyChecker(new int[]{10, 10, 10}, 11);
//        // enter any key here
//        System.out.println("\n" + debug.isKeyValid(new int[]{2, 1, 8}, true));
//        System.out.println();

        for (Map.Entry<int[], Boolean> entry : arrayPairs.entrySet()) {
            if (entry.getValue()) {
                keysGenerators.add(new KeysGenerator(power, base, StartKeyGenerator.generateMaxKey(), entry.getKey()));
            }
        }
        System.out.println(keysGenerators);

        int count = 0;
        for (KeysGenerator kG : keysGenerators) {
            List<int[]> tempList = kG.getKeys();
            if (!tempList.isEmpty()) {
                kG.getKeys().forEach(keys -> System.out.println(Arrays.toString(keys)));
                count += tempList.size();
            }
            //System.out.println(kG.getKeys());
        }
        System.out.println(count);




        // result
        //models.KeysGenerator kG1 = new models.KeysGenerator(power, base, new int[] {0, 4, 4}, new int[] {1, 0, 0});
        //System.out.println("------");
        //models.KeysGenerator kG2 = new models.KeysGenerator(power, base, new int[] {0, 4, 4}, new int[] {2, 0, 0});
        //System.out.println("------");
        //models.KeysGenerator kG3 = new models.KeysGenerator(power, base, new int[] {0, 4, 4}, new int[] {3, 0, 0});
        //System.out.println("------");
        //models.KeysGenerator kG4 = new models.KeysGenerator(power, base, new int[] {0, 4, 4}, new int[] {4, 0, 0});
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

        //models.KeysGenerator.generatePermutationsCustom(base, power, new int[] {0, 2, 2}, 0, new int[]{1, 0, 0});

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
