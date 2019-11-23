package ui;

import model.ConcreteHackingGame;

import java.awt.*;

public class MessageDisplay {
    private ConcreteHackingGame theGame;

    public MessageDisplay(ConcreteHackingGame theGame) {
        this.theGame = theGame;
    }

    public int drawMessage(String message,
                           Graphics gameGraphics,
                           int fontSize,
                           int messageX,
                           int messageY,
                           Color textColor) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(textColor);
        gameGraphics.setFont(new Font("Arial", fontSize, fontSize));
        int stringWidth = getStringWidth(message,gameGraphics,fontSize);
        displayString(message, gameGraphics, stringWidth, messageX, messageY);
        gameGraphics.setColor(savedColor);
        return stringWidth;
    }

    public int drawMessage(String message,
                           Graphics gameGraphics,
                           int fontSize,
                           int messageX,
                           int messageY,
                           Color textColor, boolean underlied) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(textColor);
        int stringWidth = getStringWidth(message, gameGraphics, fontSize);
        displayString(message, gameGraphics, stringWidth, messageX, messageY);
        if (underlied) {
            gameGraphics.drawLine(messageX, messageY + 2, messageX + stringWidth, messageY + 2);
        }
        gameGraphics.setColor(savedColor);

        return stringWidth;
    }

    public int getStringWidth(String string, Graphics gameGraphics, int fontSize) {
        gameGraphics.setFont(new Font("Arial", fontSize, fontSize));
        FontMetrics fontMetrics = gameGraphics.getFontMetrics();
        return fontMetrics.stringWidth(string);
    }


    // Centres a string on the screen
    // MODIFIES: gameGraphics
    // EFFECTS:  display the string str horizontally onto g at vertical position y
    private void displayString(String string, Graphics gameGraphics, int stringWidth, int stringX, int stringY) {

        if (stringX == 0) {
            gameGraphics.drawString(string, (theGame.WIDTH - stringWidth) / 2, stringY);
        } else {
            gameGraphics.drawString(string, stringX, stringY);
        }
    }
}
