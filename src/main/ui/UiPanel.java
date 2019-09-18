package ui;

import java.util.ArrayList;

import java.util.Scanner;

public class UiPanel {
    private ArrayList<LogEntry> operationLog;
    private Scanner scanner;

    public UiPanel() {
        operationLog = new ArrayList<>();
        scanner = new Scanner(System.in);
        display();
    }

    private void display() {
        String option = "";
        while (true) {
            LogInScreen loginscreen = new LogInScreen();
            operationLog.add(loginscreen.getLogResult());
            option = scanner.nextLine();
            if (option.equals("2")) {
                break;
            }
            DesktopScreen desktopscreen = new DesktopScreen();
            operationLog.add(desktopscreen.getLogResult());
            option = scanner.nextLine();
            if (option.equals("2")) {
                break;
            }
        }
        new QuitScreen(operationLog);
    }
}
