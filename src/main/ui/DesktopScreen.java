package ui;

public class DesktopScreen {
    public DesktopScreen() {
        display();
    }

    private void display() {

        System.out.println("LOGGED INTO DESKTOP AS ADMIN");
        System.out.println("1.Back");
        System.out.println("2.Quit");
        System.out.println("Please select an option (1 or 2):");
    }

    public LogEntry getLogResult() {
        LogEntry logEntry = new LogEntry();
        logEntry.setValue("Logged into desktop as admin");
        return logEntry;
    }
}
