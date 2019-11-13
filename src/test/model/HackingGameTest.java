package model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

class HackingGameTest {
    private HackingGame game;
    private Image notePadImg;

    @BeforeEach
    void runBefore() {
        game = new HackingGame();

    }

    @Test
    void testInit() {
        assertEquals(1500, game.WIDTH);
        assertEquals(1000, game.LENGTH);
        assertEquals(15, game.MAX_PASSWORD_LENGTH);

        assertFalse(game.isOver());
        assertTrue(game.isLogIn());
        assertEquals(game.DEFAULT_PASSWORD, game.getPassword());

    }


    @Test
    void testGetImg() {
        notePadImg = game.getImage("unknown");
        assertEquals(null, notePadImg);
    }

    @Test
    void testSetPassword() {
        assertEquals(game.DEFAULT_PASSWORD, game.getPassword());

        game.setPassword("test");

        assertEquals("test", game.getPassword());

        game.setPassword("");

        assertEquals("", game.getPassword());
    }

    @Test
    void testGameIsOver() {
        assertFalse(game.isOver());

        game.setState(game.GAME_OVER);


        assertTrue(game.isOver());

    }

    @Test
    void testGameIsLogIn() {

        assertTrue(game.isLogIn());

        game.setState(game.GAME_OVER);

        assertFalse(game.isLogIn());

        game.setState(game.GAME_IS_PLAYED);

        assertFalse(game.isLogIn());

        game.setState(game.GAME_LOG_IN);
        assertTrue(game.isLogIn());


    }

    @Test
    void testGameIsPlayed() {
        assertFalse(game.isOver());

        game.setState(game.GAME_OVER);

        assertTrue(game.isOver());

        game.setState(game.GAME_IS_PLAYED);

        assertFalse(game.isLogIn());
        assertFalse(game.isOver());


    }

    @Test
    void testKeyPressedLogIn() {
        //game is at log in state and password is an empty string
        assertTrue(game.isLogIn());
        assertEquals(game.DEFAULT_PASSWORD.length(), game.getPassword().length());


        //Test for when user types anything other than backspace and enter -> add a character to password
        game.keyPressed(KeyEvent.VK_A);
        assertEquals(game.DEFAULT_PASSWORD.length() + 1, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_ESCAPE);
        assertEquals(game.DEFAULT_PASSWORD.length() + 2, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_KP_DOWN);
        assertEquals(game.DEFAULT_PASSWORD.length() + 3, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_1);
        assertEquals(game.DEFAULT_PASSWORD.length() + 4, game.getPassword().length());


        //Test when user hits backspace when password is not empty -> delete last character
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(game.DEFAULT_PASSWORD.length() + 3, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(game.DEFAULT_PASSWORD.length() + 2, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_2);
        assertEquals(game.DEFAULT_PASSWORD.length() + 3, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(game.DEFAULT_PASSWORD.length() + 2, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(game.DEFAULT_PASSWORD.length() + 1, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_BACK_SPACE);

        assertEquals(game.DEFAULT_PASSWORD.length(), game.getPassword().length());
        for (int i = 0; i < game.DEFAULT_PASSWORD.length(); i++) {
            game.keyPressed(KeyEvent.VK_BACK_SPACE);
        }
        assertEquals(0, game.getPassword().length());


        //Test when user hits enter and backspace when password is empty
        game.keyPressed(KeyEvent.VK_ENTER);
        assertEquals(0, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals(0, game.getPassword().length());
        for (int i = 0; i < 15; i++) {
            game.keyPressed(KeyEvent.VK_A);
        }
        assertEquals(15, game.getPassword().length());

        //Adding a characters to password at maximum length
        game.keyPressed(KeyEvent.VK_J);
        assertEquals(15, game.getPassword().length());
        game.keyPressed(KeyEvent.VK_B);
        assertEquals(15, game.getPassword().length());


        //set isGameOver and isGameLogIn to true
        game.setState(game.GAME_OVER);

        assertTrue(game.isOver());

        game.setState(game.GAME_LOG_IN);

        assertTrue(game.isLogIn());

        //When user hit enter with a non-empty password
        // -> move to main game state and isGameOver and isGameLogIn are both false
        game.keyPressed(KeyEvent.VK_ENTER);
        assertFalse(game.isLogIn());
        assertEquals(game.getState(), game.GAME_IS_PLAYED);
    }

    @Test
    void testKeyPressedMainGame() {
        game.setState(game.GAME_IS_PLAYED);
        NotePadPage notePadPage = game.getNotePadPage();

        //When input dialog box is active
        notePadPage.setInputHasCursor(true);
        notePadPage.setInputContent("");


        game.keyPressed(KeyEvent.VK_ENTER);
        game.keyPressed(KeyEvent.VK_BACK_SPACE);
        assertEquals("", notePadPage.getInputContent());

        game.keyTyped(KeyEvent.VK_SPACE, ' ');
        assertEquals(" ", notePadPage.getInputContent());

        game.keyTyped(KeyEvent.VK_A, 'a');
        game.keyTyped(KeyEvent.VK_1, '1');
        assertEquals(" a1", notePadPage.getInputContent());

        String str = " a1";
        for (int i = 0; i < 47; i++) {
            game.keyTyped(KeyEvent.VK_C, 'c');
            str += "c";
        }
        assertEquals(str, notePadPage.getInputContent());

        game.keyTyped(KeyEvent.VK_SPACE, ' ');
        game.keyTyped(KeyEvent.VK_A, 'a');
        assertEquals(str, notePadPage.getInputContent());

        game.keyTyped(KeyEvent.VK_BACK_SPACE, (char) 8);
        game.keyTyped(KeyEvent.VK_BACK_SPACE, (char) 8);
        str = str.substring(0, str.length() - 2);
        assertEquals(str, notePadPage.getInputContent());

        game.keyTyped(KeyEvent.VK_ENTER, (char) KeyEvent.VK_ENTER);
        assertEquals("", notePadPage.getInputContent());


        //When input dialog box is not active
        notePadPage.setInputHasCursor(false);

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



    }

    @Test
    void testKeyPressedGameOver() {

        game.setState(game.GAME_OVER);
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
        assertEquals(game.DEFAULT_PASSWORD, game.getPassword());


    }


}