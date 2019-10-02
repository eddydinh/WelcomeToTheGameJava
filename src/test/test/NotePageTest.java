package test;

import model.HackingGame;
import model.NotePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class NotePageTest {
    private HackingGame game;
    private NotePage notePage;


    @BeforeEach
    void runBefore() throws IOException {

        game = new HackingGame();
        notePage = game.getNotePage();

    }

    @Test
    void testInit() {
        //Test main page
        assertEquals(300, notePage.getMainPageX());
        assertEquals(200, notePage.getMainPageY());
        assertEquals(400, notePage.getMainPageWidth());
        assertEquals(500, notePage.getMainPageHeight());

        //Test nav bar
        assertEquals(300, notePage.getNavBarX());
        assertEquals(170, notePage.getNavBarY());
        assertEquals(400, notePage.getNavBarWidth());
        assertEquals(40, notePage.getNavBarHeight());

        //Test input
        assertEquals(301, notePage.getInputX());
        assertEquals(658, notePage.getInputY());
        assertEquals(398, notePage.getInputWidth());
        assertEquals(40, notePage.getInputHeight());

        //Test close btn
        assertEquals(30, notePage.getCloseBtnWidth());
        assertEquals(30, notePage.getCloseBtnHeight());
        assertEquals(660, notePage.getCloseBtnX());
        assertEquals(173, notePage.getCloseBtnY());

        //Input content
        assertEquals(notePage.DEFAULT_INPUT, notePage.getInputContent());

        //inputHasCursor
        assertFalse(notePage.inputHasCursor());

    }

    @Test
    void testIsMouseOverCloseBtn() {
        double mouseX = 670;
        double mouseY = 202;

        assertTrue(notePage.isMouseOverCloseBtn(mouseX, mouseY));

        mouseX = 690;
        mouseY = 203;

        assertFalse(notePage.isMouseOverCloseBtn(mouseX, mouseY));
    }


    @Test
    void testIsMouseOverInput() {
        double mouseX = 698;
        double mouseY = 697;

        assertTrue(notePage.isMouseOverInput(mouseX, mouseY));

        mouseX = 699;
        mouseY = 698;

        assertFalse(notePage.isMouseOverInput(mouseX, mouseY));
    }

    @Test
    void testHasCursor() {
        assertFalse(notePage.inputHasCursor());
        notePage.setInputHasCursor(true);
        assertTrue(notePage.inputHasCursor());
    }
}
