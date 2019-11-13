package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestHackingGame {
    HackingGame game;
    HackScreen hackScreen;
    private Image notePadImg;
    @BeforeEach
    void runBefore() {
        game = new HackingGame();
        hackScreen = game.getHackScreen();
    }

    @Test
    void getInstance() {
        assertEquals(game, game.getInstance());
    }

    @Test
    void getHackScreen() {
        assertEquals(hackScreen,game.getHackScreen());
    }

    @Test
    void update() {
        game.update(game.GAME_IS_PLAYED);

        assertTrue(game.isPlayed());

        game.update(game.GAME_OVER);

        assertTrue(game.isOver());

        game.update(game.GAME_LOG_IN);

        assertTrue(game.isLogIn());

        game.update(game.HACK_SCREEN);

        assertTrue(game.getHackScreen().askedCode.length() > 0);
    }

    @Test
    void setState() {
        assertEquals(game.GAME_LOG_IN, game.getState());
        game.setState(game.GAME_IS_PLAYED);
        assertEquals(game.GAME_IS_PLAYED, game.getState());
        game.setState(game.GAME_OVER);
        assertEquals(game.GAME_OVER, game.getState());
        game.setState(game.HACK_SCREEN);
        assertEquals(game.HACK_SCREEN, game.getState());
    }

    @Test
    void readToLines() {
        List<String> testLines = new ArrayList<String>();
        //TestRead
        try {
            testLines = game.readToLines(game.DEFAULT_NOTE_FILE_PATH);
        } catch (IOException exception) {
            fail("IOException is caught");
            exception.printStackTrace();

        }

        assertEquals(testLines.size(), game.lines.size());

    }


    @Test
    void getImage() {
        notePadImg = game.getImage("unknown");
        assertEquals(null, notePadImg);
        notePadImg = game.getImage(game.DEFAULT_NOTE_IMAGE_PATH);
        assertTrue(notePadImg != null);
    }

    @Test
    void isOver() {
        assertFalse(game.isOver());

        game.setState(game.GAME_OVER);


        assertTrue(game.isOver());
    }

    @Test
    void isLogIn() {
        assertTrue(game.isLogIn());

        game.setState(game.GAME_OVER);

        assertFalse(game.isLogIn());

        game.setState(game.GAME_IS_PLAYED);

        assertFalse(game.isLogIn());

        game.setState(game.GAME_LOG_IN);
        assertTrue(game.isLogIn());


    }

    @Test
    void isPlayed() {
        assertFalse(game.isOver());

        game.setState(game.GAME_OVER);

        assertTrue(game.isOver());

        game.setState(game.GAME_IS_PLAYED);

        assertFalse(game.isLogIn());
        assertFalse(game.isOver());
        assertTrue(game.isPlayed());
    }

    @Test
    void keyPressed() {
    }

    @Test
    void writeToFile() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void setPassword() {
    }

    @Test
    void keyTyped() {
    }
}