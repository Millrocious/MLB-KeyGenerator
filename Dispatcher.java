import Controllers.BasePairsController;
import View.View;

import java.io.FileNotFoundException;
import java.util.*;

public class Dispatcher {
    public static void main(String[] args) throws FileNotFoundException {
        int base = 11;
        int power = 3;

        HashMap<Integer, Boolean> basePair = new HashMap<>(BasePairsController.findOne(base));
        HashMap<int[], Boolean> arrayPairs = new HashMap<>();

        BasePairsController.findAll();
        View.show();

    }
}
