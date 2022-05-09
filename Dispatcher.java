import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Dispatcher {
    public static void main(String[] args) {
        int base = 3;
        int power = 6;

        KeysGenerator keysGenerator = new KeysGenerator(power, base);
        final List<int[]> keys = keysGenerator.getKeys();
        System.out.println(keys.size());
        for (int[] key : keys) {
            System.out.println(Arrays.toString(key));
        }
    }
}

class MultiLeveLKeyGenerator {
    String phase;
    int base;
    int power;
    int index = 0;
    double length;
    List<String> probablyKeys;

    public MultiLeveLKeyGenerator(String phase, int base, List<String> probablyKeys) {
        this.phase = phase;
        this.base = base;
        this.power = phase.length();
        this.probablyKeys = probablyKeys;
        this.length = Math.pow(base, power) - 1;
    }

    public int[] generateMultiLevelKey() {
        int[] keyArrays = new int[power];
        int[] phaseArrays = new int[power];

        String key = probablyKeys.get(index);

        for (int i = 0; i < power; i++) {
            keyArrays[i] = Integer.parseInt(String.valueOf(key.charAt(i)), 16);
            phaseArrays[i] = Integer.parseInt(String.valueOf(phase.charAt(i)), 16);
        }
        int[] beginningPhase = Arrays.copyOf(phaseArrays, power);

        for (int i = 0; i < length; i++) {
            int res = 0;
            for (int j = 0; j < power; j++) {
                res += keyArrays[j] * phaseArrays[j];
            }
            res %= base;
            for (int j = 0; j < power - 1; ) {
                phaseArrays[j] = phaseArrays[++j];
            }
            phaseArrays[power - 1] = res;

            if (i < (length - power) && i > 1 && Arrays.equals(phaseArrays, beginningPhase)) {
                index++;
                return new int[0];
            }
        }
        if (Arrays.equals(phaseArrays, beginningPhase)) {
            index++;
            return keyArrays;
        }

        index++;
        return new int[0];
    }
}

class KeysGenerator {
    int base;
    int power;
    List<String> probablyKeys = new ArrayList<>();

    public KeysGenerator(int power, int base) {
        this.base = base;
        this.power = power;
    }

    List<int[]> getKeys() {
        int[] interval = new int[power];
        String phase = new String(new char[power]).replace("\0", "1");

        for (int i = 0; i < power; i++) {
            interval[i] = base - 1;
        }
        generatePermutations(interval, 0, "");

        int amountOfKeys = probablyKeys.size();
        List<int[]> keys = new ArrayList<>();
        MultiLeveLKeyGenerator multiLeveLKeyGenerator =
                new MultiLeveLKeyGenerator(phase, base, probablyKeys);

        for (int i = 0; i < amountOfKeys; i++) {
            int[] key = multiLeveLKeyGenerator.generateMultiLevelKey();
            AtomicInteger pair = new AtomicInteger();

            boolean isContainKey = keys.stream().parallel().anyMatch(k -> Arrays.equals(k, key));

            if (!isContainKey && key.length > 0) {
                keys.add(key);

                if (pair.get() > 0) {
                    if (pair.get() == key[0]) {
                        int k = base - pair.get();
                        int[] newKey = new int[power];
                        newKey[0] = pair.get();

                        for (int p = power - 1; p > 0; p--) {
                            newKey[power - p] = (key[p] * k) % base;
                        }
                        keys.add(newKey);
                    }
                } else {
                    for (int j = 1; j < base; j++) {
                        int condition = (key[0] * j) % base;

                        if (condition == 1 && condition != j) {
                            System.out.println(j);
                            pair.set(j);
                        }
                    }

                    multiLeveLKeyGenerator.probablyKeys = multiLeveLKeyGenerator.probablyKeys
                            .stream()
                            .parallel()
                            .filter(s -> !String.valueOf(s.charAt(0)).equals(Integer.toHexString(pair.get())))
                            .collect(Collectors.toList());
                    amountOfKeys = multiLeveLKeyGenerator.probablyKeys.size();

                    int k = base - pair.get();
                    int[] newKey = new int[power];
                    newKey[0] = pair.get();

                    for (int p = power - 1; p > 0; p--) {
                        newKey[power - p] = (key[p] * k) % base;
                    }
                    keys.add(newKey);
                }
            }
        }
        return keys;
    }

    void generatePermutations(int[] intervals, int pos, String lastPerm) {
        if (pos == intervals.length)
            return;

        for (int i = 0; i <= intervals[pos]; i++) {
            if (pos == intervals.length - 1) {
                probablyKeys.add(lastPerm + Integer.toHexString(i));
            }
            generatePermutations(intervals, pos + 1, lastPerm + Integer.toHexString(i));
        }
    }
}