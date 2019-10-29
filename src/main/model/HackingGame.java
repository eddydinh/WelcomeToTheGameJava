package model;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import model.exceptions.GameIsAlreadyLoggedInException;
import model.exceptions.GameIsAlreadyOverException;
import model.exceptions.GameIsAlreadyPlayedException;


/*
 * Represents a hacking game.
 */
public class HackingGame {
    public static final int WIDTH = 1500;
    public static final int LENGTH = 1000;
    public static final int MAX_PASSWORD_LENGTH = 15;
    public static final String DEFAULT_PASSWORD = "*******";
    public static final String DEFAULT_NOTES_NAME = "Notes";
    public static final String DEFAULT_BROWSER_NAME = "A.N.N";
    public static final String DEFAULT_NOTE_FILE_PATH = "src/notepad.txt";
    public static final String DEFAULT_WEB_LINKS_PATH = "src/weblinks.txt";
    private static final String DEFAULT_NOTE_IMAGE_PATH = "src/img/notePadIcon.png";
    private static final String DEFAULT_BROWSER_IMAGE_PATH = "src/img/browserIcon.png";
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

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
    public List<String> webNames = new ArrayList<>();

    public HackingGame() {

        try {
            lines = readToLines(DEFAULT_NOTE_FILE_PATH);
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            setPassword(DEFAULT_PASSWORD);
            //set up notepad icon
            Image notePadImage = getImage(DEFAULT_NOTE_IMAGE_PATH);
            Image browserImage = getImage(DEFAULT_BROWSER_IMAGE_PATH);
            notePadIcon = new NotePadIcon(50, 50, 80, 90, DEFAULT_NOTES_NAME, notePadImage);
            notePadPage = new NotePadPage(50, 400, 400, 500, DEFAULT_NOTES_NAME);
            notePadIcon.setPage(notePadPage);
            browserIcon = new BrowserIcon(50, 200, 80, 90, DEFAULT_BROWSER_NAME, browserImage);
            browserPage = new BrowserPage(475, 100, 1000, 900, DEFAULT_BROWSER_NAME);
            generateWebLinks(browserPage);
            browserIcon.setPage(browserPage);
        }


    }

    private void generateWebLinks(BrowserPage browserPage) {
        List<String> linksArray = new ArrayList<>();
        try {
            linksArray = readToLines(DEFAULT_WEB_LINKS_PATH);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        for (int i = 0; i < linksArray.size(); i++) {
            String webName = linksArray.get(i);
            webNames.add(webName);
            WebLink link = new WebLink(webName, generateRandomLinkFromName(webName));
            browserPage.addWebLink(link);
        }


    }

//    public static void printMap(HashMap mp) {
//        Iterator it = mp.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry) it.next();
//            System.out.println(pair.getKey() + " = " + pair.getValue().toString());
//            it.remove(); // avoids a ConcurrentModificationException
//        }
//    }

    private String generateRandomLinkFromName(String webName) {
        String generatedString = hashWebName(webName);
        return "http://" + generatedString + ".ann";
    }

    private String hashWebName(String webName) {

        StringBuilder returnString = new StringBuilder();
        int s = 0;
        while (s <= 27) {
            int character = webName.charAt(s % webName.length());
            returnString.append(ALPHA_NUMERIC_STRING.charAt(character % ALPHA_NUMERIC_STRING.length()));
            s++;
        }


        return returnString.toString();
    }


    public List<String> readToLines(String filePath) throws IOException {

        return Files.readAllLines(Paths.get(filePath));

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
    //REQUIRES: isLogIn is false
    //EFFECTS: indicate game is in log in screen
    public void gameIsLogIn() throws GameIsAlreadyLoggedInException {
        if (isLogIn()) {
            throw new GameIsAlreadyLoggedInException();
        } else {
            isGameLogIn = true;
        }


    }


    //MODIFIES: isGameLogIn and isGameOver
    //REQUIRES: isOver is false
    //EFFECTS: indicate game is in game over screen
    public void gameIsOver() throws GameIsAlreadyOverException {
        if (isOver()) {
            throw new GameIsAlreadyOverException();

        } else {
            isGameLogIn = false;
            isGameOver = true;
        }


    }

    //MODIFIES: isGameLogIn and isGameOver
    //REQUIRES: isGameLogin is true and isGameOver is true
    //EFFECTS: indicate game is in main game screen
    public void gameIsPlayed() throws GameIsAlreadyPlayedException {
        if (!isLogIn() && !isOver()) {
            throw new GameIsAlreadyPlayedException();
        } else {
            isGameLogIn = false;
            isGameOver = false;
        }

    }

    // Responds to key press codes
    // REQUIRES: key pressed code
    // MODIFIES: this
    // EFFECTS: logInScreen: type in password, enter to log in
    //          gameScreen:
    //          gameOverScreen: R to replay

    public void keyPressed(int keyCode) {
        if (isGameLogIn) {
            validateKeyLogIn(keyCode);

        } else if (isGameOver) {
            validateKeyGameOver(keyCode);
        } else {
            if (!notePadPage.inputHasCursor()) {
                if (keyCode == KeyEvent.VK_C) {
                    try {
                        gameIsOver();
                    } catch (GameIsAlreadyOverException exception) {
                        System.out.println("Game is already over!");
                    }
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
                try {
                    gameIsPlayed();
                } catch (GameIsAlreadyPlayedException exception) {
                    System.out.println("Game is already at play screen!");
                }
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
            try {
                gameIsLogIn();
            } catch (GameIsAlreadyLoggedInException exception) {
                System.out.println("Game is already at log in screen!");
            }
        }

    }

    //Validate key code on main game screen and produce desired effects
    //REQUIRES: keyCode
    //MODIFIES: this
    //EFFECTS: crash system into game over screen
    private void validateKeyGameIsPlayed(int keyCode) {
        if (notePadPage.inputHasCursor()) {
            if (keyCode == KeyEvent.VK_SPACE && notePadPage.getInputContent().length() < 50) {
                notePadPage.setInputContent(notePadPage.getInputContent() + " ");
            } else if (((keyCode >= 48 && keyCode <= 90)
                    || (keyCode >= 96 && keyCode <= 105)) && notePadPage.getInputContent().length() < 50) {
                notePadPage.setInputContent(notePadPage.getInputContent() + KeyEvent.getKeyText(keyCode).toLowerCase());
            }
            if (notePadPage.getInputContent().length() > 0) {
                if (keyCode == KeyEvent.VK_ENTER) {
                    writeLineAndSaveNoteToFile();
                } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                    notePadPage.setInputContent(notePadPage
                            .getInputContent().substring(0, notePadPage.getInputContent().length() - 1));

                }
            }
        }

    }

    private void writeLineAndSaveNoteToFile() {
        try {
            writeToFile(DEFAULT_NOTE_FILE_PATH);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            lines.add(notePadPage.getInputContent());
            notePadPage.setInputContent("");
        }
    }

    public void writeToFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        PrintWriter writer = new PrintWriter(file);

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

