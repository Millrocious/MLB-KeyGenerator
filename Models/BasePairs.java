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

    public static final HashMap<ArrayList<?>, Boolean> basePair = new HashMap<ArrayList<?>, Boolean>() {{
        put(basePair7, false);
        put(basePair11, true);
        put(basePair13, false);
        put(basePair19, true);
    }};
}
