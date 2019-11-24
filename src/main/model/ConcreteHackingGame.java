package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;


import ui.Constants;
import ui.HackingGameFrame;
import ui.UiPanel;


/*
 * Represents a hacking game.
 */
public class ConcreteHackingGame extends HackingGame {

    private HackScreen hackScreen;
    public int randomInd;
    private static ConcreteHackingGame HACKING_GAME;

    //EFFECT: return singleton instance of ConcreteHackingGame
    public static ConcreteHackingGame getInstance() {
        if (HACKING_GAME == null) {
            HACKING_GAME = new ConcreteHackingGame();
        }
        return HACKING_GAME;
    }


    public HackScreen getHackScreen() {
        return hackScreen;
    }

    //MODIFIES: this
    //EFFECT: update state of game, if hack screen state, generate random prompt and reset timer.
    @Override
    public void update(String gameState) {

        if (gameState == HACK_SCREEN) {
            Random random = new Random();
            randomInd = random.nextInt(codes.size() - 1);
            hackScreen.askedCode = codes.get(randomInd);
            GameTimer.hackScreenTimer = UiPanel.CODE_BOX_WIDTH;
        }
        setState(gameState);
    }

    public void setState(String gameState) {
        state = gameState;
    }



    private String password;
    public List<String> webNames = new ArrayList<>();
    public List<String> codes = new ArrayList<>();
    private int initialKeysLength;
    public int count;

    public ConcreteHackingGame() {

        HACKING_GAME = this;

        try {

            keys = readToLines(DEFAULT_KEYS_FILE_PATH);
            initialKeysLength = keys.size();
            resetNote();
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
            browserIcon.setPage(browserPage);
            hackScreen = new HackScreen();
            hackScreen.addObserver(this);


        }


    }

    //EFFECTS: read lines in txt files to a List of String

    public List<String> readToLines(String filePath) throws IOException {

        return Files.readAllLines(Paths.get(filePath));

    }

    //EFFECTS: return Java Image from file path
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
                validateKeyGameWinLost(keyCode);
                break;

        }


    }


    //Validate key code on win/ lost screen produce desired effects
    //REQUIRES: keyCode
    //MODIFIES: this
    //EFFECTS: let user quite the game
    private void validateKeyGameWinLost(int keyCode) {
        if (keyCode == KeyEvent.VK_Q) {
            if (state == WIN || state == GAME_TRULY_OVER) {
                HackingGameFrame.getInstance().dispose();
            }
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
            browserPage.mainPageState = browserPage.HOME;
            notePadPage.setInputContent(constants.DEFAULT_INPUT_NOTEPAD);
            notePadPage.setInputTextColor(new Color(173, 173, 173));
            notePadPage.setInputHasCursor(false);
            hackScreen.inputCode = "";
            setState(GAME_LOG_IN);
            GameTimer.hackScreenTimer = UiPanel.CODE_BOX_WIDTH;
        }

    }

    //MODIFIES: this
    //EFFECT: save users' notes and verify if user has found a secret key, if yes set state to WIN

    private void writeLineAndSaveNoteToFile() {

        try {
            if (keys.contains(notePadPage.getInputContent())) {
                count++;
                keys.remove(notePadPage.getInputContent());
            }
            if (count == initialKeysLength) {
                this.update(WIN);

            }
            lines.add(notePadPage.getInputContent());
            notePadPage.setInputContent("");
            writeToFile(DEFAULT_NOTE_FILE_PATH);


        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    //MODIFIES: file from filePath
    //EFFECT: write array lines to file
    public void writeToFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        PrintWriter writer = new PrintWriter(file);

        for (String line : lines) {
            writer.println(line);
        }
        writer.close();

    }

    //MODIFIES: notepad.txt
    //EFFECTS: reset note pad to original texts
    public void resetNote() {
        File file = new File(DEFAULT_NOTE_FILE_PATH);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("WELCOME TO THE GAME!");
            writer.println("Use Anonymous Node Network (A.N.N) to find all " + initialKeysLength + " secret keys");
            writer.println("Don't forget to save your key in Notes!");
            writer.println("You have " + GameTimer.MAX_DAY + " days.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    //Validate key typed on notepad and hackscreen
    //REQUIRES: keyCode, keyChar
    //MODIFIES: this
    //EFFECTS: let user type and save notes, and type and submit code in hack screen
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


    //Validate key code on notepad
    //REQUIRES: keyCode
    //MODIFIES: notePadPage, notepad.txt
    //EFFECTS: let user write and save in notes.

    private void validateKeyGameIsPlayed(int keyCode, char keyChar) {
        if (notePadPage.inputHasCursor() && isPlayed()) {
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

