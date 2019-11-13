package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class NotePadIconTest {
    private NotePadIcon notePadIcon;
    private Image notePadImage;
    private HackingGame game;
    private BrowserIcon browserIcon;

    @BeforeEach
    void runBefore() throws IOException {
        game = new HackingGame();
        notePadIcon = game.getNotePadIcon();
        notePadImage = notePadIcon.getIconImg();
        browserIcon = game.getBrowserIcon();
    }

    @Test
    void testInit() {
        assertEquals(50, notePadIcon.getIconX());
        assertEquals(50, notePadIcon.getIconY());
        assertEquals(80, notePadIcon.getIconWidth());
        assertEquals(90, notePadIcon.getIconHeight());
        assertEquals(notePadImage, notePadIcon.getIconImg());


        assertFalse(notePadIcon.isClicked());
        assertFalse(notePadIcon.isDoubleClicked());

        assertEquals(game.DEFAULT_NOTES_NAME, notePadIcon.getIconName());
    }

    @Test
    void testIsMouseOver() {
        double mouseX = 65;
        double mouseY = 75;

        assertTrue(notePadIcon.isMouseOver(mouseX, mouseY));

        mouseX = 500;
        mouseY = 300;

        assertFalse(notePadIcon.isMouseOver(mouseX, mouseY));

    }

    @Test
    void testGetPage() {
        assertEquals(game.getNotePadPage(), notePadIcon.getPage());

    }

}
