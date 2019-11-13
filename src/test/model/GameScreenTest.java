package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameScreenTest {
    private HackingGame game;
    private HackScreen hackScreen;

    @BeforeEach
    void runBefore() {
        game = new HackingGame();
        hackScreen = game.getHackScreen();

    }


    @Test
    void testInit(){
        assertEquals(0.5, hackScreen.chance);
        assertEquals("",hackScreen.inputCode);
        assertEquals("",hackScreen.askedCode);
    }

}
