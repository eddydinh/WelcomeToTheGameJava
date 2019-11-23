package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Constants;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BrowserPageTest {
    private ConcreteHackingGame game;
    private BrowserPage browserPage;
    private Constants constants;


    @BeforeEach
    void runBefore() throws IOException {

        game = new ConcreteHackingGame();
        browserPage = game.getBrowserPage();
        constants = new Constants();

    }

    @Test
    void testWebLinksInit() {
        assertTrue(browserPage.getWebLinks().isEmpty());
        browserPage.addWebLink(new WebLink("test", "test"));
        browserPage.addWebLink(new WebLink("test", "test"));
        assertEquals(1, browserPage.getWebLinks().size());

        assertEquals(browserPage.getMainPageX() + 10, browserPage.getHomeBtnX());
        assertEquals(browserPage.getMainPageY() + browserPage.getNavBarHeight() - 25,
                browserPage.getHomeBtnY());
        assertEquals(30, browserPage.getHomeBtnWidth());
        assertEquals(30, browserPage.getHomeBtnHeight());
    }

    @Test
    void testIsMouseOverHomeButton() {
        double mouseX = browserPage.getHomeBtnX() +
                browserPage.getHomeBtnWidth() / 2;
        double mouseY = browserPage.getHomeBtnY() +
                browserPage.getHomeBtnHeight() / 2;
        assertTrue(browserPage.isMouseOverHomeButton(mouseX, mouseY));
        mouseX = browserPage.getHomeBtnX() - 1;
        mouseY = browserPage.getHomeBtnY() - 1;
        assertFalse(browserPage.isMouseOverHomeButton(mouseX, mouseY));
        mouseX = browserPage.getHomeBtnX() +
                browserPage.getHomeBtnWidth() / 2;
        mouseY = browserPage.getHomeBtnY() - 1;
        assertFalse(browserPage.isMouseOverHomeButton(mouseX, mouseY));
        mouseX = browserPage.getHomeBtnX() - 1;
        mouseY = browserPage.getHomeBtnY() +
                browserPage.getHomeBtnHeight() / 2;
        assertFalse(browserPage.isMouseOverHomeButton(mouseX, mouseY));
    }

    @Test
    void testSetMouseOverHomeButton(){
        assertFalse(browserPage.getIsMouseOverHomeButton());
        browserPage.setMouseOverCloseBtn(true);
        assertTrue(browserPage.getIsMouseOverHomeButton());
    }

}
