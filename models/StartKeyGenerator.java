package models;

import java.util.ArrayList;
import java.util.Arrays;

public class StartKeyGenerator {
    static int base;
    static int power;

    public static int[] generateMinKey(int num) {
        int[] temp = new int[power];
        temp[0] = num;
        return temp;
    }

    public static int[] generateMaxKey() {
        int[] temp = new int[power];
        temp[0] = 0;
        for (int j = 1; j < power; j++) {
            temp[j] = base-1;
        }
        return temp;
    }
}
