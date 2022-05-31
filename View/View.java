package View;

import java.util.ArrayList;

public final class View {
    public static ArrayList<String> messages = new ArrayList<>();

    public static void show() {
        for (String message : messages) {
            System.out.println(message);
        }
        messages.clear();
    }

}
