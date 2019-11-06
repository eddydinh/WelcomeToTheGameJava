package ui;

import java.awt.*;

public class FlashCursorDisplay {
    private static boolean flash = false;

    // MODIFIES: gameGraphics
    // EFFECTS: Draw content for password boxes on login screen
    public void drawFlashingCursor(Graphics gameGraphics,
                                    int logInBoxX,
                                    int logInBoxY,
                                    int contentWidth,
                                    Color color) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(color);
        gameGraphics.setFont(new Font("Arial", 25, 25));
        String str = "|";
        if (flash) {
            str = "";
        }
        gameGraphics.drawString(str,
                logInBoxX + contentWidth,
                logInBoxY - 8);
        gameGraphics.setColor(savedColor);

    }


    //MODIFIES: flash
    //EFFECTS: invert flash boolean variable
    public boolean updateFlash() {
        flash = !flash;
        return flash;
    }
}
