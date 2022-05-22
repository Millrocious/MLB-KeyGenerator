package Controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FileController {
    private static final ArrayList<File> tempFiles = new ArrayList<>();
    private static final ArrayList<PrintWriter> printWriters = new ArrayList<>();

    public static void generate(int count, boolean isPair) throws FileNotFoundException {
        for (int i = 1; i < count; i++) {
            tempFiles.add(new File("Logs/temp"+i+isPair));
            printWriters.add(new PrintWriter(tempFiles.get(i-1)));
        }
    }

    public static void deleteTempFiles() {
        for (File temp: tempFiles) {
            temp.delete();
        }
    }

    public static void closePrintWriters() {
        for (PrintWriter temp: printWriters) {
            temp.close();
        }
    }

    public static void concatFiles() throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("Final/result.txt"));
        File file = new File("Logs/");
        File[] files = file.listFiles();
        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {

            System.out.println("Processing " + files[i].getPath() + "... ");
            BufferedReader br = new BufferedReader(new FileReader(files[i]
                    .getPath()));
            String line = br.readLine();
            while (line != null) {
                pw.println(line);
                line = br.readLine();
            }
            br.close();
        }
        pw.close();
        FileController.deleteTempFiles();
        System.out.println("All files have been concatenated into result.txt");
    }

    public static ArrayList<File> getTempArray() {
        return tempFiles;
    }

    public static ArrayList<PrintWriter> getWritersArray() {
        return printWriters;
    }
}
