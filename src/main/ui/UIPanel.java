package ui;

import java.util.ArrayList;

import java.util.Scanner;

public class UIPanel {
    private ArrayList<LogEntry> operationLog;
    private Scanner scanner;

    public UIPanel() {
        operationLog = new ArrayList<>();
        scanner = new Scanner(System.in);
        display();
    }

    private void clearScreen() {
        String clear = "";
        for (int i = 0; i < 5; ++i) clear = clear + "\r\n";
        System.out.println(clear);
    }

    private void display() {
        String option = "";
        while (true) {
            clearScreen();
            LogInScreen loginscreen = new LogInScreen();
            operationLog.add(loginscreen.getLogResult());
            option = scanner.nextLine();
            if (option.equals("2")) {

                break;
            } else {
                clearScreen();
                DesktopScreen desktopscreen = new DesktopScreen();
                operationLog.add(desktopscreen.getLogResult());
                option = scanner.nextLine();
                if (option.equals("2")) {
                    break;
                }
            }


        }
        clearScreen();
        new QuitScreen(operationLog);

    }
}
