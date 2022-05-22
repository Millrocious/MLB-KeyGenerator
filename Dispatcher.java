import Controllers.BasePairsController;
import Controllers.KeysFinder;
import View.View;

import java.io.FileNotFoundException;
import java.util.*;

public class Dispatcher {
    public static void main(String[] args) throws FileNotFoundException {
        int base = 11;
        int power = 3;

        HashMap<Integer, Boolean> basePair = new HashMap<>(BasePairsController.findOne(base));
        HashMap<int[], Boolean> arrayPairs = new HashMap<>(BasePairsController.generatePairsArray(basePair, power));

        //BasePairsController.findAll();
        //View.show();

        KeysFinder kF = new KeysFinder(base, power, BasePairsController.generateGenerators(base, power, arrayPairs));

        kF.find(true);

    }
}
