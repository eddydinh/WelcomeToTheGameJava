package model;

import java.awt.event.KeyEvent;


/*
 * Represents a hacking game.
 */
public class HackingGame {
    public static final int WIDTH = 1000;
    public static final int LENGTH = 800;
    public static final int MAX_PASSWORD_LENGTH = 15;

    private boolean isGameOver;


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
    private String password = "";


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

    public void keyPressed(int keyCode) {
        if (isGameLogIn) {
            validateKeyLogIn(keyCode);

        } else if (isGameOver) {
            validateKeyGameOver(keyCode);
        } else {
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
            setPassword("");
            gameIsLogIn();
        }

    }

    //Validate key code on main game screen and produce desired effects
    //REQUIRES: keyCode
    //MODIFIES: this
    //EFFECTS: crash system into game over screen
    private void validateKeyGameIsPlayed(int keyCode) {
        if (keyCode == KeyEvent.VK_C) {
            gameIsOver();
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


}
