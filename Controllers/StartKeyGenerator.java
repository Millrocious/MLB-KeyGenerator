package Controllers;

public class StartKeyGenerator {

    public static int[] generateMinKeyWNPos(int num, int power) {
        int[] temp = new int[power];
        temp[0] = num;
        return temp;
    }

    public static int[] generateMinKeyWPos(int power, int prev, int cur) {
        int[] temp = new int[power];
        temp[0] = prev;
        temp[1] = cur;
        return temp;
    }

    public static int[] generateMaxKey(int base, int power, int pos) {
        int[] temp = new int[power];
        for (int i = 0; i < pos+1; i++) {
            temp[0] = 0;
        }
        for (int j = 1; j < power; j++) {
            temp[j] = base-1;
        }
        return temp;
    }
}
