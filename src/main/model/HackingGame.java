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
import java.util.*;

import network.GetRandomStringAPI;


import ui.Constants;


/*
 * Represents a hacking game.
 */
public class HackingGame extends Game {

    private HackScreen hackScreen;
    public int randomInd;
    private static HackingGame HACKING_GAME;

    public static HackingGame getInstance() {
        if (HACKING_GAME == null) {
            HACKING_GAME = new HackingGame();
        }
        return HACKING_GAME;
    }


    public HackScreen getHackScreen() {
        return hackScreen;
    }

    @Override
    public void update(String gameState) {
        Random random = new Random();
        randomInd = random.nextInt(codes.size() - 1);
        hackScreen.askedCode = codes.get(randomInd);
        setState(gameState);
    }

    public void setState(String gameState) {
        state = gameState;
    }


    private String password;
    public List<String> webNames = new ArrayList<>();
    public List<String> codes = new ArrayList<>();

    public HackingGame() {

        HACKING_GAME = this;
        try {
            lines = readToLines(DEFAULT_NOTE_FILE_PATH);
            codes = readToLines(DEFAULT_CODES_FILE_PATH);
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {

            state = GAME_LOG_IN;
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
            hackScreen = new HackScreen();
            hackScreen.addObserver(this);

        }


    }

    private void generateWebLinks(BrowserPage browserPage) {

        List<String> webNamesArray = new ArrayList<>();
        try {
            webNamesArray = readToLines(DEFAULT_WEB_LINKS_PATH);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        GetRandomStringAPI randomStringAPI = new GetRandomStringAPI(RAND_STRING_URL_BASE,
                webNamesArray.size());
        List<String> webLinksArray = randomStringAPI.getRandomStringArray();
        for (int i = 0; i < webNamesArray.size(); i++) {

            makeWebLink(webNamesArray, i, webLinksArray);
        }


    }

    private void makeWebLink(List<String> webNamesArray, int i, List<String> webLinksArray) {
        String webName = webNamesArray.get(i);
        double d = Math.random();
        webNames.add(webName);
        WebLink link = null;
        if (d < 0.5) {
            link = new BrokenWebLink(webName, "http://" + webLinksArray.get(i) + ".ann");
        } else {
            link = new LinkWithLinks(webName, "http://" + webLinksArray.get(i) + ".ann");

        }
        browserPage.addWebLink(link);
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
        return state == GAME_OVER;
    }

    // EFFECTS: returns true if the user is at login screen, false otherwise
    public boolean isLogIn() {
        return state == GAME_LOG_IN;
    }

    // EFFECTS: returns true if the user is at login screen, false otherwise
    public boolean isPlayed() {
        return state == GAME_IS_PLAYED;
    }


    // Responds to key press codes
    // REQUIRES: key pressed code
    // MODIFIES: this
    // EFFECTS: logInScreen: type in password, enter to log in
    //          gameScreen:
    //          gameOverScreen: R to replay


    public void keyPressed(int keyCode) {

        switch (state) {
            case GAME_LOG_IN:
                validateKeyLogIn(keyCode);
                break;
            case GAME_OVER:
                validateKeyGameOver(keyCode);
                break;
            default:
                break;

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
                setState(GAME_IS_PLAYED);
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
        Constants constants = new Constants();
        if (keyCode == KeyEvent.VK_R) {
            setPassword(DEFAULT_PASSWORD);
            notePadPage.setInputContent(constants.DEFAULT_INPUT_NOTEPAD);
            setState(GAME_LOG_IN);
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


    public void keyTyped(int keyCode, char keyChar) {

        switch (state) {
            case GAME_IS_PLAYED:
                validateKeyGameIsPlayed(keyCode, keyChar);
                break;
            case HACK_SCREEN:
                hackScreen.validateKey(keyCode, keyChar);
                break;
            default:
                break;
        }

    }

    private void validateKeyGameIsPlayed(int keyCode, char keyChar) {
        if (notePadPage.inputHasCursor()) {
            if (notePadPage.getInputContent().length() < 50
                    && keyCode != KeyEvent.VK_ENTER && keyCode != KeyEvent.VK_BACK_SPACE) {

                notePadPage.setInputContent(notePadPage.getInputContent() + keyChar);
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
}

