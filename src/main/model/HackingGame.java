package model;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


/*
 * Represents a hacking game.
 */
public class HackingGame {
    public static final int WIDTH = 1500;
    public static final int LENGTH = 1000;
    public static final int MAX_PASSWORD_LENGTH = 15;
    public static final String DEFAULT_PASSWORD = "*******";

    private boolean isGameOver;
    public List<String> lines;


    public NotePadIcon getNotePadIcon() {
        return notePadIcon;
    }

    private NotePadIcon notePadIcon;

    public NotePadPage getNotePadPage() {
        return notePadPage;
    }

    private NotePadPage notePadPage;

    public BrowserIcon getBrowserIcon() {
        return browserIcon;
    }

    private BrowserIcon browserIcon;

    public BrowserPage getBrowserPage() {
        return browserPage;
    }

    private BrowserPage browserPage;


    //Set isGameOver value
    //MODIFIES: isGameOver
    //EFFECTS: set isGameOver boolean value to given value
    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    //Set isGameLogIn value
    //MODIFIES: isGameLogIn
    //EFFECTS: set isGameLogIn boolean value to given value
    public void setGameLogIn(boolean gameLogIn) {
        isGameLogIn = gameLogIn;
    }

    private boolean isGameLogIn = true;
    private String password;

    public HackingGame() throws IOException {
        setPassword(DEFAULT_PASSWORD);
        lines = Files.readAllLines(Paths.get("src/notepad.txt"));
        //set up notepad icon
        Image notePadImage = getImage("src/img/notePadIcon.png");
        Image browserImage = getImage("src/img/browserIcon.png");
        notePadIcon = new NotePadIcon(50, 50, 80, 90, "Notes", notePadImage);
        browserIcon = new BrowserIcon(50, 200, 80, 90, "A.N.N", browserImage);
        notePadPage = new NotePadPage(50, 400, 400, 500, "Notes");
        browserPage = new BrowserPage(475, 100, 1000, 900, "A.N.N");

    }


    public Image getImage(String filePath) {
        try {
            File imageFile = new File(filePath);
            Image imageReturn = ImageIO.read(imageFile);
            return imageReturn;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    // EFFECTS: returns true if the game is over, false otherwise
    public boolean isOver() {
        return isGameOver;
    }

    // EFFECTS: returns true if the user is at login screen, false otherwise
    public boolean isLogIn() {
        return isGameLogIn;
    }

    //MODIFIES: isGameLogIn and isGameOver
    //REQUIRES: isGameLogIn is false
    //EFFECTS: indicate game is in log in screen
    public void gameIsLogIn() {
        isGameLogIn = true;

    }

    //MODIFIES: isGameLogIn and isGameOver
    //REQUIRES: IsGameOver is false
    //EFFECTS: indicate game is in game over screen
    public void gameIsOver() {
        isGameLogIn = false;
        isGameOver = true;

    }

    //MODIFIES: isGameLogIn and isGameOver
    //REQUIRES: isGameLogin is true or isGameOver is true
    //EFFECTS: indicate game is in main game screen
    public void gameIsPlayed() {
        isGameLogIn = false;
        isGameOver = false;
    }

    // Responds to key press codes
    // REQUIRES: key pressed code
    // MODIFIES: this
    // EFFECTS: logInScreen: type in password, enter to log in
    //          gameScreen:
    //          gameOverScreen: R to replay

    public void keyPressed(int keyCode) throws FileNotFoundException {
        if (isGameLogIn) {
            validateKeyLogIn(keyCode);

        } else if (isGameOver) {
            validateKeyGameOver(keyCode);
        } else {
            if (!notePadPage.inputHasCursor()) {
                if (keyCode == KeyEvent.VK_C) {
                    gameIsOver();
                }
            }
            validateKeyGameIsPlayed(keyCode);
        }


    }

    //Validate key code on log in screen and produce desired effects
    //REQUIRES: keyCode
    //MODIFIES: this
    //EFFECTS: let user type in password, edit and enter to login
    private void validateKeyLogIn(int keyCode) {
        if (getPassword().length() == 0) {
            if (keyCode != KeyEvent.VK_ENTER && keyCode != KeyEvent.VK_BACK_SPACE) {
                setPassword(getPassword() + "*");
            }
        } else {
            if (keyCode == KeyEvent.VK_ENTER) {
                gameIsPlayed();
            } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                setPassword(getPassword().substring(0, getPassword().length() - 1));
            } else if (getPassword().length() < MAX_PASSWORD_LENGTH) {
                setPassword(getPassword() + "*");
            }
        }

    }

    //Validate key code on game over screen and produce desired effects
    //REQUIRES: keyCode
    //MODIFIES: this
    //EFFECTS: let user reboot into main login screen
    private void validateKeyGameOver(int keyCode) {
        if (keyCode == KeyEvent.VK_R) {
            setPassword(DEFAULT_PASSWORD);
            notePadPage.setInputContent(notePadPage.DEFAULT_INPUT);
            gameIsLogIn();
        }

    }

    //Validate key code on main game screen and produce desired effects
    //REQUIRES: keyCode
    //MODIFIES: this
    //EFFECTS: crash system into game over screen
    private void validateKeyGameIsPlayed(int keyCode) throws FileNotFoundException {
        if (notePadPage.inputHasCursor()) {
            if (keyCode == KeyEvent.VK_SPACE && notePadPage.getInputContent().length() < 50) {
                notePadPage.setInputContent(notePadPage.getInputContent() + " ");
            } else if (((keyCode >= 48 && keyCode <= 90)
                    || (keyCode >= 96 && keyCode <= 105)) && notePadPage.getInputContent().length() < 50) {
                notePadPage.setInputContent(notePadPage.getInputContent() + KeyEvent.getKeyText(keyCode).toLowerCase());
            }
            if (notePadPage.getInputContent().length() > 0) {
                if (keyCode == KeyEvent.VK_ENTER) {
                    lines.add(notePadPage.getInputContent());
                    writeToFile();
                    notePadPage.setInputContent("");
                } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                    notePadPage.setInputContent(notePadPage
                            .getInputContent().substring(0, notePadPage.getInputContent().length() - 1));

                }
            }
        }

    }

    public void writeToFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("src/notepad.txt");
        for (String line : lines) {
            writer.println(line);
        }
        writer.close();
    }

    //Get password value
    //EFFECTS: return password value
    public String getPassword() {
        return password;
    }

    //Set password value
    //MODIFIES: password
    //EFFECTS: set password value to given value
    public void setPassword(String password) {
        this.password = password;
    }


}
