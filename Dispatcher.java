import Controllers.BasePairsController;
import Controllers.KeysFinder;
import Controllers.ThreadController;
import Models.BasePairs;
import View.View;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Dispatcher {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        int base = 13;
        int power = 6;

        Map<Integer, Boolean> basePair1 = new HashMap<>();


        Map<Integer, Boolean> basePair = new HashMap<>(BasePairsController.findOne(base));
        Map<int[], Boolean> arrayPairs = new HashMap<>(BasePairsController.generatePairsArray(basePair, power));

        //BasePairsController.findAll();
        //View.show();

        //KeysFinder kF = new KeysFinder(base, power, BasePairsController.generateGenerators(base, power, arrayPairs));
        //kF.startFinder();

        ThreadController.calculate();
    }
}
