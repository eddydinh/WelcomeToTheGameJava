package ui;

public class LogInScreen {
    public LogInScreen() {
        display();
    }

    private void display() {
        clearScreen();
        System.out.println("CRACKING OPEN A CODE ONE: A HACKING GAME");
        System.out.println("1.Log in");
        System.out.println("2.Quit");
        System.out.println("Please select an option (1 or 2):");
    }

    public LogEntry getLogResult() {
        LogEntry logEntry = new LogEntry();
        logEntry.setValue("Get to main log in screen");
        return logEntry;
    }

    private void clearScreen() {
        String clear = "";
        for (int i = 0; i < 9; ++i) {
            clear = clear + "\r\n";

        }
        System.out.println(clear);

    }
}
