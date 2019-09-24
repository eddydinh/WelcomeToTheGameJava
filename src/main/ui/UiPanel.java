package ui;

import model.HackingGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.*;


public class UiPanel extends JPanel {
    private static final int LOGIN_BOX_WIDTH = 200;
    private static final int LOGIN_BOX_LENGTH = 30;
    private static final String WELCOME = "WELCOME TO THE GAME";
    private static final String LOGIN_LABEL = "LOGIN: ";
    private static final String PASSWORD_LABEL = "PASSWORD: ";
    private static final String LOGIN_CONTENT = "root";

    private static final String GREETING_0 = "ADMINISTRATION TERMINAL...";
    private static final String GREETING_1 = "SESSION ENCRYPTED...";
    private static final String GREETING_2 = "TYPE IN YOUR PASSWORD AND PRESS ENTER TO LOG IN >";

    private static final String GAME_OVER_MESSAGE_0 = "SYSTEM FAILURE";
    private static final String GAME_OVER_MESSAGE_1 = "KERNEL PANIC!";
    private static final String GAME_OVER_MESSAGE_2 = "Files corrupted: error 23423413...pending...";
    private static final String GAME_OVER_MESSAGE_3 = "fatal ER # 45337QW32Z_WS @%$$";
    private static final String GAME_OVER_MESSAGE_4 = "PRESS R TO REBOOT";


    private HackingGame theGame;
    private boolean flash = false;
    private int centerY;

    public UiPanel(HackingGame theGame) {
        setPreferredSize(new Dimension(HackingGame.WIDTH, HackingGame.LENGTH));
        setBackground(Color.BLACK);
        this.theGame = theGame;
        this.centerY = theGame.LENGTH / 2 - LOGIN_BOX_LENGTH / 2;


    }

    @Override
    protected void paintComponent(Graphics gameGraphics) {
        super.paintComponent(gameGraphics);

        if (theGame.isLogIn()) {
            displayLogInScreen(gameGraphics);
        } else if (theGame.isOver()) {
            displayGameOverScreen(gameGraphics);
        } else {
            displayGameScreen(gameGraphics);
        }


    }


    // MODIFIES: gameGraphics
    // EFFECTS:  draws GAME_OVER_MESSAGE strings, and replay instructions onto gameGraphics
    private void displayGameOverScreen(Graphics gameGraphics) {
        Color savedColor = gameGraphics.getColor();
        Color messageTextColor = new Color(255, 0, 0);
        drawMessage(GAME_OVER_MESSAGE_0, gameGraphics, 50, centerY - 150, messageTextColor);
        drawMessage(GAME_OVER_MESSAGE_1, gameGraphics, 25, centerY - 100, messageTextColor);
        drawMessage(GAME_OVER_MESSAGE_2, gameGraphics, 10, centerY - 50, messageTextColor);
        drawMessage(GAME_OVER_MESSAGE_3, gameGraphics, 10, centerY, messageTextColor);
        int stringWidth = drawMessage(GAME_OVER_MESSAGE_4, gameGraphics, 25, centerY + 50, messageTextColor);
        gameGraphics.setColor(new Color(255, 255, 255));
        drawFlashingCursor(gameGraphics, (theGame.WIDTH - stringWidth) / 2 + 10, centerY + 55, stringWidth);
        gameGraphics.setColor(savedColor);


    }

    // Draws the game
    // MODIFIES: gameGraphics
    // EFFECTS:  draws the game onto gameGraphics
    private void displayGameScreen(Graphics gameGraphics) {
        drawRect(gameGraphics, new Color(78, 78, 78), 0, 0, theGame.WIDTH, 30);
        drawRect(gameGraphics, new Color(56, 91, 110), 0, 30, theGame.WIDTH, theGame.LENGTH);
        drawMessage("PRESS C TO CRASH THE SYSTEM", gameGraphics, 50, centerY, new Color(255, 255, 255));

    }

    //Draw rect component of main game screen
    //MODIFIES: gameGraphics
    //EFFECTS: draws all the rectangular component of main game screen
    private void drawRect(Graphics gameGraphics, Color rectColor, int rectX, int rectY, int rectWidth, int rectLength) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(rectColor);
        gameGraphics.fillRect(
                rectX, rectY,
                rectWidth,
                rectLength);
        gameGraphics.setColor(savedColor);


    }


    // Draws log in screen
    // MODIFIES: gameGraphics
    // EFFECTS:  draws log in screen onto gameGraphics
    private void displayLogInScreen(Graphics gameGraphics) {
        Color messageTextColor = new Color(0, 215, 0);
        drawMessage(WELCOME, gameGraphics, 50, centerY - 150, messageTextColor);
        drawLogInBox(gameGraphics,
                centerY,
                LOGIN_LABEL,
                LOGIN_CONTENT);
        drawLogInBox(gameGraphics,
                centerY + 50,
                PASSWORD_LABEL, theGame.getPassword());
        int messageY = centerY + 100;
        drawMessage(GREETING_0, gameGraphics, 10, messageY + 100, messageTextColor);
        drawMessage(GREETING_1, gameGraphics, 10, messageY + 120, messageTextColor);
        drawMessage(GREETING_2, gameGraphics, 15, messageY + 200, messageTextColor);


    }

    //Draws message
    //MODIFIES: gameGraphics
    //EFFECTS: draws messages onto game Graphics
    private int drawMessage(String message, Graphics gameGraphics, int fontSize, int messageY, Color textColor) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(textColor);
        gameGraphics.setFont(new Font("Arial", fontSize, fontSize));
        FontMetrics fontMetrics = gameGraphics.getFontMetrics();
        int stringWidth = centreString(message, gameGraphics, fontMetrics, messageY);
        gameGraphics.setColor(savedColor);
        return stringWidth;


    }


    // MODIFIES: gameGraphics
    // EFFECTS: Draw log in boxes with label and content on login screen
    private void drawLogInBox(Graphics gameGraphics, int logInBoxY, String label, String content) {
        Color savedColor = gameGraphics.getColor();
        int logInBoxX = theGame.WIDTH / 2 - LOGIN_BOX_WIDTH / 2 + 50;
        drawBoxes(gameGraphics, logInBoxX, logInBoxY);
        drawLogInLabel(gameGraphics, logInBoxX, logInBoxY, label);
        if (content == LOGIN_CONTENT) {
            drawLogInContent(gameGraphics, logInBoxX, logInBoxY, content);
        } else {
            drawPasswordContent(gameGraphics, logInBoxX, logInBoxY, content);
        }
        gameGraphics.setColor(savedColor);

    }

    private void drawBoxes(Graphics gameGraphics, int logInBoxX, int logInBoxY) {
        gameGraphics.setColor(new Color(0, 215, 0, 100));
        gameGraphics.fillRect(
                logInBoxX + 1, logInBoxY + 1,
                LOGIN_BOX_WIDTH,
                LOGIN_BOX_LENGTH);
        gameGraphics.setColor(new Color(0, 215, 0));
        gameGraphics.drawRect(
                logInBoxX, logInBoxY,
                LOGIN_BOX_WIDTH + 2,
                LOGIN_BOX_LENGTH + 2);
    }

    // MODIFIES: gameGraphics
    // EFFECTS: Draw labels for log in boxes on login screen
    private void drawLogInLabel(Graphics gameGraphics, int logInBoxX, int logInBoxY, String label) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(new Color(0, 215, 0, 150));
        gameGraphics.setFont(new Font("Arial", 28, 28));
        FontMetrics fontMetrics = gameGraphics.getFontMetrics();
        int labelWidth = fontMetrics.stringWidth(label);
        gameGraphics.drawString(label,
                logInBoxX - labelWidth,
                logInBoxY + 28);
        gameGraphics.setColor(savedColor);
    }

    // MODIFIES: gameGraphics
    // EFFECTS: Draw content for log in boxes on login screen
    private void drawLogInContent(Graphics gameGraphics, int logInBoxX, int logInBoxY, String content) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(new Color(255, 255, 255));
        gameGraphics.setFont(new Font("Arial", 25, 25));
        gameGraphics.drawString(content,
                logInBoxX + 10,
                logInBoxY + 23);
        gameGraphics.setColor(savedColor);
    }

    // MODIFIES: gameGraphics
    // EFFECTS: Draw content for password boxes on login screen
    private void drawPasswordContent(Graphics gameGraphics, int logInBoxX, int logInBoxY, String content) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(new Color(255, 255, 255));
        gameGraphics.setFont(new Font("Arial", 25, 25));
        int contentWidth = gameGraphics.getFontMetrics().stringWidth(content);
        int offsetLogInBoxX = logInBoxX + 10;
        int offsetLogInBoxY = logInBoxY + 30;
        gameGraphics.drawString(content,
                offsetLogInBoxX,
                offsetLogInBoxY);
        drawFlashingCursor(gameGraphics, offsetLogInBoxX, offsetLogInBoxY, contentWidth);
        gameGraphics.setColor(savedColor);

    }


    // MODIFIES: gameGraphics
    // EFFECTS: Draw content for password boxes on login screen
    private void drawFlashingCursor(Graphics gameGraphics, int logInBoxX, int logInBoxY, int contentWidth) {
        gameGraphics.setFont(new Font("Arial", 25, 25));
        String str = "|";
        if (flash) {
            str = "";
        }
        gameGraphics.drawString(str,
                logInBoxX + contentWidth,
                logInBoxY - 8);

    }

    // Centres a string on the screen
    // MODIFIES: gameGraphics
    // EFFECTS:  centres the string str horizontally onto g at vertical position y
    private int centreString(String string, Graphics gameGraphics, FontMetrics fontMetrics, int stringY) {
        int stringWidth = fontMetrics.stringWidth(string);
        gameGraphics.drawString(string, (theGame.WIDTH - stringWidth) / 2, stringY);
        return stringWidth;
    }

    //MODIFIES: flash
    //EFFECTS: invert flash boolean variable
    public void updateFlash() {
        flash = !flash;
    }


}
