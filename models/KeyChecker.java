package models;

import java.util.Arrays;

class KeyChecker {
    int[] phaseArray;
    int base;
    int power;
    double length;

    public KeyChecker(int[] phase, int base) {
        this.phaseArray = phase;
        this.base = base;
        this.power = phase.length;
        this.length = Math.pow(base, power) - 1;
    }

    public boolean isKeyValid(int[] keyArray, boolean debugMode) {
        int[] beginningPhase = Arrays.copyOf(phaseArray, power);
        for (int i = 0; i < length; i++) {
            int res = 0;
            for (int j = 0; j < power; j++) {
                res += keyArray[j] * phaseArray[j];
            }
            res %= base;

            // print sequence if you want

            if (debugMode) {
                System.out.print(res);
            }

            for (int j = 0; j < power - 1; ) {
                phaseArray[j] = phaseArray[++j];
            }
            phaseArray[power - 1] = res;

            if (Arrays.equals(phaseArray, beginningPhase) && i < length - power - 1) {
                return false;
            }
        }

        return Arrays.equals(phaseArray, beginningPhase);
    }


}