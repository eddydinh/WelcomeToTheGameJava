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
        assertFalse(browserPage.getWebLinks().isEmpty());

    }
}
