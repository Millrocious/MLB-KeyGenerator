package Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileController {
    private static final ArrayList<File> tempFiles = new ArrayList<>();
    private static final ArrayList<PrintWriter> printWriters = new ArrayList<>();

    public static void generate() throws FileNotFoundException {
        for (int i = 1; i < 5; i++) {
            tempFiles.add(new File("temp"+i));
            printWriters.add(new PrintWriter(tempFiles.get(i-1)));
        }
    }

    public static void deleteTempFiles() {
        for (File temp: tempFiles) {
            temp.delete();
        }
    }

    public static ArrayList<File> getTempArray() {
        return tempFiles;
    }

    public static ArrayList<PrintWriter> getWritersArray() {
        return printWriters;
    }
}
