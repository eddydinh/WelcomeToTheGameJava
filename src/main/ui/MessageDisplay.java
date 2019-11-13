package ui;

import model.HackingGame;

import java.awt.*;

public class MessageDisplay {
    private HackingGame theGame;

    public MessageDisplay(HackingGame theGame) {
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
        FontMetrics fontMetrics = gameGraphics.getFontMetrics();
        int stringWidth = displayString(message, gameGraphics, fontMetrics, messageX, messageY);
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
        gameGraphics.setFont(new Font("Arial", fontSize, fontSize));
        FontMetrics fontMetrics = gameGraphics.getFontMetrics();
        int stringWidth = displayString(message, gameGraphics, fontMetrics, messageX, messageY);
        if (underlied) {
            gameGraphics.drawLine(messageX, messageY + 2, messageX + stringWidth, messageY + 2);
        }
        gameGraphics.setColor(savedColor);

        return stringWidth;
    }

    // Centres a string on the screen
    // MODIFIES: gameGraphics
    // EFFECTS:  display the string str horizontally onto g at vertical position y
    private int displayString(String string, Graphics gameGraphics, FontMetrics fontMetrics, int stringX, int stringY) {
        int stringWidth = fontMetrics.stringWidth(string);
        if (stringX == 0) {
            gameGraphics.drawString(string, (theGame.WIDTH - stringWidth) / 2, stringY);
        } else {
            gameGraphics.drawString(string, stringX, stringY);
        }
        return stringWidth;
    }
}
