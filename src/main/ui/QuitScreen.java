package ui;

import java.util.ArrayList;

public class QuitScreen {
    private ArrayList<LogEntry> operationLog;

    public QuitScreen(ArrayList<LogEntry> opLog) {
        operationLog = opLog;
        display();
    }

    private void display() {
        clearScreen();
        System.out.println("FATAL EXCEPTION");
        System.out.println("ACTIVITIES LOG:");
        for (LogEntry entry : operationLog) {
            System.out.println("     " + entry);
        }
        System.out.println("     System crashed, fatal exception!");
    }

    private void clearScreen() {
        String clear = "";
        for (int i = 0; i < 5; ++i) {
            clear = clear + "\r\n";

        }
        System.out.println(clear);

    }
}
