package Controllers;

public class KeysNumFinder {
    private final int[] bases = {3, 5, 7, 11, 13, 17, 19};

    public void countKeys() {
        for (int basis : bases) {
            for (int j = 2; j < 10; j++) {
                System.out.println(basis + "^" + j + " -> " + keysNum(basis, j));
            }
        }
    }

    public static long keysNum(double base, double pow) {
        return (long) (eulerf((long) (Math.pow(base, pow) - 1)) / pow);
    }

    private static long eulerf(long n) {
        long result = n;

        for (int i = 2; i * i <= n; ++i) {
            if (n % i == 0) {
                while (n % i == 0) n /= i;
                result -= result / i;
            }
        }

        if (n > 1) result -= result / n;
        return result;
    }
}
