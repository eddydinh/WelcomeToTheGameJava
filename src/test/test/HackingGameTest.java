package test;

import model.HackingGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

class HackingGameTest {
    private HackingGame game;

    @BeforeEach
    void runBefore() {
        game = new HackingGame();
    }

    @Test
    void testInit() {
        assertEquals(1000, game.WIDTH);
        assertEquals(800, game.LENGTH);
        assertEquals(15, game.MAX_PASSWORD_LENGTH);

        assertFalse(game.isOver());
        assertTrue(game.isLogIn());
        assertEquals("", game.getPassword());

    }

    @Test
    void testSetPassword() {
        assertEquals("", game.getPassword());

        game.setPassword("test");

        assertEquals("test", game.getPassword());

        game.setPassword("");

        assertEquals("", game.getPassword());
    }

    @Test
    void testGameIsOver() {
        assertFalse(game.isOver());

        game.gameIsOver();

        assertTrue(game.isOver());
        assertFalse(game.isLogIn());
    }

    @Test
    void testGameIsLogIn() {

        assertTrue(game.isLogIn());

        game.setGameLogIn(false);

        assertFalse(game.isLogIn());

        game.gameIsLogIn();

        assertTrue(game.isLogIn());
    }

    @Test
    void testGameIsPlayed() {
        assertTrue(game.isLogIn());
        assertFalse(game.isOver());

        game.setGameOver(true);

        assertTrue(game.isOver());

        game.gameIsPlayed();

        assertFalse(game.isLogIn());
        assertFalse(game.isOver());
    }

    @Test
    void testKeyPressedLogIn() {
        //game is at log in state and password is an empty string
        assertTrue(game.isLogIn());
        assertEquals(0, game.getPassword().length());

        //Test for when user hits enter when password is empty
        game.keyPressed(KeyEvent.VK_ENTER);
        game.keyPressed(KeyEvent.VK_ENTER);
        game.keyPressed(KeyEvent.VK_ENTER);
        assertEquals(0, game.getPassword().length());

        //Test for when user hits backspace when password is empty
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(0, game.getPassword().length());

        //Test for when user types anything other than backspace and enter -> add a character to password
        game.keyPressed(KeyEvent.VK_A);
        assertEquals(1, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_ESCAPE);
        assertEquals(2, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_KP_DOWN);
        assertEquals(3, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_1);
        assertEquals(4, game.getPassword().length());


        //Test when user hits backspace when password is not empty -> delete last character
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(3, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(2, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_2);
        assertEquals(3, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(2, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(1, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(0, game.getPassword().length());

        //Test when user hits enter and backspace when password is empty
        game.keyPressed(KeyEvent.VK_ENTER);
        assertEquals(0, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(0, game.getPassword().length());
        for (int i = 0; i < 15; i++){
            game.keyPressed(KeyEvent.VK_A);
        }
        assertEquals(15, game.getPassword().length());

        //Adding a characters to password at maximum length
        game.keyPressed(KeyEvent.VK_J);
        assertEquals(15, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_B);
        assertEquals(15, game.getPassword().length());


        //set isGameOver and isGameLogIn to true
        game.setGameOver(true);
        assertTrue(game.isLogIn());
        assertTrue(game.isOver());

        //When user hit enter with a non-empty password
        // -> move to main game state and isGameOver and isGameLogIn are both false
        game.keyPressed(KeyEvent.VK_ENTER);
        assertFalse(game.isOver());
        assertFalse(game.isLogIn());
    }

    @Test
    void testKeyPressedMainGame() {
        game.gameIsPlayed();

        //make sure the game is in main screen stage (both isOver() and isLogIn() are false)
        assertFalse(game.isOver());
        assertFalse(game.isLogIn());

        //if user hits other keys
        game.keyPressed(KeyEvent.VK_A);
        //Nothing changes
        assertFalse(game.isOver());
        assertFalse(game.isLogIn());
        //if user hits other keys
        game.keyPressed(KeyEvent.VK_ESCAPE);
        //Nothing changes
        assertFalse(game.isOver());
        assertFalse(game.isLogIn());
        //if user hits other keys
        game.keyPressed(KeyEvent.VK_KP_DOWN);
        //Nothing changes
        assertFalse(game.isOver());
        assertFalse(game.isLogIn());
        //if user hits other keys
        game.keyPressed(KeyEvent.VK_1);
        //Nothing changes
        assertFalse(game.isOver());
        assertFalse(game.isLogIn());

        //if user hits C
        game.keyPressed(KeyEvent.VK_C);

        //-> Game over state
        assertTrue(game.isOver());
        assertFalse(game.isLogIn());


    }

    @Test
    void testKeyPressedGameOver() {
        game.gameIsOver();

        //make sure game is in game over state
        assertTrue(game.isOver());
        assertFalse(game.isLogIn());

        //if user hits other keys
        game.keyPressed(KeyEvent.VK_A);
        //Nothing changes
        assertTrue(game.isOver());
        assertFalse(game.isLogIn());
        //if user hits other keys
        game.keyPressed(KeyEvent.VK_ESCAPE);
        //Nothing changes
        assertTrue(game.isOver());
        assertFalse(game.isLogIn());
        //if user hits other keys
        game.keyPressed(KeyEvent.VK_KP_DOWN);
        //Nothing changes
        assertTrue(game.isOver());
        assertFalse(game.isLogIn());
        //if user hits other keys
        game.keyPressed(KeyEvent.VK_1);
        //Nothing changes
        assertTrue(game.isOver());
        assertFalse(game.isLogIn());

        //if user hits R
        game.keyPressed(KeyEvent.VK_R);

        //-> Log In state
        assertTrue(game.isLogIn());
        assertEquals(0, game.getPassword().length());


    }
}