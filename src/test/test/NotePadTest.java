package test;

import model.HackingGame;
import model.NotePad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class NotePadTest {
    private NotePad notePad;
    private Image notePadImage;
    private HackingGame game;

    @BeforeEach
    void runBefore() throws IOException {
        game = new HackingGame();
        notePad = game.getNotePad();
        notePadImage = notePad.getIconImg();
    }

    @Test
    void testInit() {
        assertEquals(50, notePad.getIconX());
        assertEquals(50, notePad.getIconY());
        assertEquals(80, notePad.getIconWidth());
        assertEquals(90, notePad.getIconHeight());
        assertEquals(notePadImage, notePad.getIconImg());

        assertFalse(notePad.isPressed());
        assertFalse(notePad.isClicked());
        assertFalse(notePad.isDoubleClicked());
    }

    @Test
    void testIsMouseOver() {
        double mouseX = 65;
        double mouseY = 75;

        assertTrue(notePad.isMouseOver(mouseX, mouseY));

        mouseX = 500;
        mouseY = 300;

        assertFalse(notePad.isMouseOver(mouseX, mouseY));
    }

    @Test
    void testPressed() {
        assertFalse(notePad.isPressed());

        notePad.setPressed(true);

        assertTrue(notePad.isPressed());
    }
}
