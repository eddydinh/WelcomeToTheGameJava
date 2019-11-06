package ui;

import java.awt.*;

public class RectDisplay {

    //Draw rect component of main game screen
    //MODIFIES: gameGraphics
    //EFFECTS: draws all the rectangular component of main game screen
    public void drawRect(Graphics gameGraphics, Color rectColor, int rectX, int rectY, int rectWidth, int rectLength) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(rectColor);
        gameGraphics.fillRect(
                rectX, rectY,
                rectWidth,
                rectLength);
        gameGraphics.setColor(savedColor);


    }
}
