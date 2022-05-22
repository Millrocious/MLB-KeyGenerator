import Controllers.BasePairsController;
import Controllers.KeysFinder;
import Controllers.KeysNumFinder;
import View.View;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Dispatcher {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException, ExecutionException {
        int base = 11;
        int power = 5;

        HashMap<Integer, Boolean> basePair = new HashMap<>(BasePairsController.findOne(base));
        HashMap<int[], Boolean> arrayPairs = new HashMap<>(BasePairsController.generatePairsArray(basePair, power));

        //BasePairsController.findAll();
        //View.show();

        KeysFinder kF = new KeysFinder(base, power, BasePairsController.generateGenerators(base, power, arrayPairs));

        long startTime = System.currentTimeMillis();
        List<int[]> list = kF.mTFindS(true);
        long endTime = System.currentTimeMillis();
        if (KeysNumFinder.keysNum(base, power) == list.size()) {
            System.out.println("yes " + list.size());
        }
        System.out.println("Total execution time: " + ((endTime / 1000)-(startTime / 1000)) + "s");


    }
}
