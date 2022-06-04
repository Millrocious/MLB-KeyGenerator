package Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public final class BasePairs {
    private static final ArrayList<Integer> basePair7 = new ArrayList<>(Arrays.asList(3, 2));
    private static final ArrayList<ArrayList<Integer>> basePair11 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(2, 7)),
            new ArrayList<>(Arrays.asList(3, 5))));
    private static final ArrayList<Integer> basePair13 = new ArrayList<>(Arrays.asList(2, 6));
    private static final ArrayList<Integer> basePair17 = new ArrayList<>(Arrays.asList(3, 5, 10, 11));
    private static final ArrayList<ArrayList<Integer>> basePair19 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(2, 3, 14)),
            new ArrayList<>(Arrays.asList(4, 6, 9))));

    private static final HashMap<Integer, ArrayList<Integer>> basePair = new HashMap<>() {{
        put(7, basePair7);
        put(13, basePair13);
        put(17, basePair17);
    }};
    private static final HashMap<Integer, ArrayList<ArrayList<Integer>>> basePairN = new HashMap<>() {{
        put(11, basePair11);
        put(19, basePair19);
    }};

    public static HashMap<Integer, ArrayList<ArrayList<Integer>>> getHashMapN() {
        return basePairN;
    }

    public static HashMap<Integer, ArrayList<Integer>> getHashMap() {
        return basePair;
    }
}
