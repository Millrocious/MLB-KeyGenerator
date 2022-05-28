import Controllers.BasePairsController;
import Controllers.FileController;
import Controllers.KeysFinder;
import View.View;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Dispatcher {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        int base = 13;
        int power = 6;

        HashMap<Integer, Boolean> basePair = new HashMap<>(BasePairsController.findOne(base));
        HashMap<int[], Boolean> arrayPairs = new HashMap<>(BasePairsController.generatePairsArray(basePair, power));

        BasePairsController.findAll();
        View.show();



        KeysFinder kF = new KeysFinder(base, power, BasePairsController.generateGenerators(base, power, arrayPairs));
        //kF.isKeysExist();

        new File("Logs/").mkdir();
        new File("Final/").mkdir();
        long startTime = System.currentTimeMillis();
        kF.mTFindF(true);
        long endTime = System.currentTimeMillis();


        FileController.concatFiles();

        System.out.println("Total execution time: " + ((endTime / 1000)-(startTime / 1000)) + "s");


    }
}
