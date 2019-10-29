package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BrowserPageTest {
    private HackingGame game;
    private BrowserPage browserPage;


    @BeforeEach
    void runBefore() throws IOException {

        game = new HackingGame();
        browserPage = game.getBrowserPage();

    }

    @Test
    void testWebLinksInit() {
        assertFalse(browserPage.getWebLinks().isEmpty());
    }
}
