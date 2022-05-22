package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

public class StartKeyGenerator {

    public static int[] generateMinKey(int num, int power) {
        int[] temp = new int[power];
        temp[0] = num;
        return temp;
    }

    public static int[] generateMaxKey(int base, int power) {
        int[] temp = new int[power];
        temp[0] = 0;
        for (int j = 1; j < power; j++) {
            temp[j] = base-1;
        }
        return temp;
    }
}
