package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameScreenTest {
    private ConcreteHackingGame game;
    private HackScreen hackScreen;

    @BeforeEach
    void runBefore() {
        game = new ConcreteHackingGame();
        hackScreen = game.getHackScreen();

    }


    @Test
    void testInit() {
        assertEquals(0.5, hackScreen.chance);
        assertEquals("", hackScreen.inputCode);
        assertEquals("", hackScreen.askedCode);
    }

    @Test
    void testNotifyObservers() {
        game.setState(game.GAME_IS_PLAYED);
        hackScreen.chance = 1;
        hackScreen.notifyObservers(game.HACK_SCREEN);


        assertEquals(game.HACK_SCREEN, game.getState());

        game.setState(game.GAME_IS_PLAYED);
        hackScreen.chance = 0;
        hackScreen.notifyObservers(game.HACK_SCREEN);


        assertEquals(game.GAME_IS_PLAYED, game.getState());

        game.setState(game.HACK_SCREEN);
        hackScreen.chance = 1;
        hackScreen.notifyObservers(game.GAME_IS_PLAYED);


        assertEquals(game.GAME_IS_PLAYED, game.getState());

        game.setState(game.HACK_SCREEN);
        hackScreen.chance = 0;
        hackScreen.notifyObservers(game.GAME_IS_PLAYED);


        assertEquals(game.GAME_IS_PLAYED, game.getState());

        game.setState(game.HACK_SCREEN);
        hackScreen.notifyObservers(game.GAME_OVER);

        assertEquals(game.GAME_OVER, game.getState());

        game.setState(game.GAME_IS_PLAYED);
        hackScreen.notifyObservers(game.GAME_OVER);

        assertEquals(game.GAME_IS_PLAYED, game.getState());






    }

}
