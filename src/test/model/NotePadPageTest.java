package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class NotePadPageTest {
    private HackingGame game;
    private NotePadPage notePadPage;


    @BeforeEach
    void runBefore() throws IOException {

        game = new HackingGame();
        notePadPage = game.getNotePadPage();

    }

    @Test
    void testInit() {
        assertFalse(notePadPage.isPressed());
        //Test main page
        assertEquals(50, notePadPage.getMainPageX());
        assertEquals(400, notePadPage.getMainPageY());
        assertEquals(400, notePadPage.getMainPageWidth());
        assertEquals(500, notePadPage.getMainPageHeight());

        //Test nav bar
        assertEquals(50, notePadPage.getNavBarX());
        assertEquals(370, notePadPage.getNavBarY());
        assertEquals(400, notePadPage.getNavBarWidth());
        assertEquals(40, notePadPage.getNavBarHeight());

        //Test input
        assertEquals(51, notePadPage.getInputX());
        assertEquals(858, notePadPage.getInputY());
        assertEquals(398, notePadPage.getInputWidth());
        assertEquals(40, notePadPage.getInputHeight());

        //Test close btn
        assertEquals(30, notePadPage.getCloseBtnWidth());
        assertEquals(30, notePadPage.getCloseBtnHeight());
        assertEquals(410, notePadPage.getCloseBtnX());
        assertEquals(373, notePadPage.getCloseBtnY());

        //Input content
        assertEquals(notePadPage.DEFAULT_INPUT, notePadPage.getInputContent());

        //inputHasCursor
        assertFalse(notePadPage.inputHasCursor());

        //
        assertEquals(game.DEFAULT_NOTES_NAME, notePadPage.getPageName());

        //
        assertEquals(notePadPage.DEFAULT_INPUT_COLOR, notePadPage.getInputTextColor());

    }

    @Test
    void testIsMouseOverCloseBtn() {
        double mouseX = notePadPage.getCloseBtnX()
                + notePadPage.getCloseBtnWidth() - 10;
        double mouseY = notePadPage.getCloseBtnY()
                + notePadPage.getCloseBtnHeight() - 10;

        assertTrue(notePadPage.isMouseOverCloseBtn(mouseX, mouseY));

        mouseX = 690;
        mouseY = 203;

        assertFalse(notePadPage.isMouseOverCloseBtn(mouseX, mouseY));
    }


    @Test
    void testIsMouseOverInput() {
        double mouseX = notePadPage.getInputX() + notePadPage.getInputWidth() - 10;
        double mouseY = notePadPage.getInputY() + notePadPage.getInputHeight() - 10;

        assertTrue(notePadPage.isMouseOverInput(mouseX, mouseY));

        mouseX = 699;
        mouseY = 698;

        assertFalse(notePadPage.isMouseOverInput(mouseX, mouseY));
    }

    @Test
    void testIsMouseOverNavBar() {
        double mouseX = notePadPage.getNavBarX() + notePadPage.getNavBarWidth() - notePadPage.getCloseBtnWidth() - 10;
        double mouseY = notePadPage.getNavBarY() + notePadPage.getNavBarHeight() - 10;

        assertTrue(notePadPage.isMouseOverNavBar(mouseX, mouseY));

        mouseX = 699;
        mouseY = 698;

        assertFalse(notePadPage.isMouseOverNavBar(mouseX, mouseY));
    }

    @Test
    void testHasCursor() {
        assertFalse(notePadPage.inputHasCursor());
        notePadPage.setInputHasCursor(true);
        assertTrue(notePadPage.inputHasCursor());
    }

    @Test
    void testPressed() {
        assertFalse(notePadPage.isPressed());

        notePadPage.setPressed(true);

        assertTrue(notePadPage.isPressed());
    }
}
