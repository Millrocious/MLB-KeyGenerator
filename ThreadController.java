import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadController {
    ArrayBlockingQueue<String> keys = new ArrayBlockingQueue<>(10);
    PrintWriter pw;
    int base, power, pos;
    int[] maxKey, minKey;
    boolean workDone = false;

    public ThreadController(PrintWriter pw, int base, int power, int pos, int[] maxKey, int[] minKey) {
        this.pw = pw;
        this.base = base;
        this.power = power;
        this.pos = pos;
        this.maxKey = maxKey;
        this.minKey = minKey;
    }

    public void start() throws InterruptedException {
        generateKeys(base, power, maxKey, pos, minKey, pw);

        new Thread(() -> {
            while (!workDone) {
                try {
                    pw.println(keys.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void generateKeys(int base, int power, int[] maxKey, int pos, int[] lastGeneratedKey, PrintWriter pw) throws InterruptedException {
        if (pos == maxKey.length)
            return;

        for (int i = 0; i <= maxKey[pos]; i++) {
            int[] updatedKey = Arrays.copyOf(lastGeneratedKey, power);

            if (pos == maxKey.length - 1) {
                updatedKey[pos] = i;
                keys.put(Arrays.toString(updatedKey));
            }
            generateKeys(base, power, maxKey, pos + 1, updatedKey, pw);

            if (++lastGeneratedKey[pos] > base - 1)
                break;
        }
        workDone = true;
    }
}
