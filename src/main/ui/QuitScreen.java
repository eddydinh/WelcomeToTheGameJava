package ui;

import java.util.ArrayList;

public class QuitScreen {
    private ArrayList<LogEntry> operationLog;

    public QuitScreen(ArrayList<LogEntry> opLog) {
        operationLog = opLog;
        display();
    }

    private void display() {
        System.out.println("FATAL EXCEPTION");
        System.out.println("ACTIVITIES LOG:");
        for (LogEntry entry : operationLog) {
            System.out.println("     " + entry);
        }
        System.out.println("     System crashed, fatal exception!");
    }
}
