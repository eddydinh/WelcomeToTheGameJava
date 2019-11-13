package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SaveLoadTest {
    private ConcreteHackingGame theGame;

    @BeforeEach
    void runBefore() {

        theGame = new ConcreteHackingGame();
    }

    //Check content
    @Test
    void testWrite() {
        //TestWrite
        int previousSize = theGame.lines.size();
        theGame.lines.add("Hello World!");
        try {
            theGame.writeToFile(theGame.DEFAULT_NOTE_FILE_PATH);
        } catch (FileNotFoundException exception) {
            fail("FileNotFoundException is caught");
            exception.printStackTrace();
        } finally {
            assertEquals(previousSize + 1, theGame.lines.size());

        }


    }

    @Test
    void testRead() {
        List<String> testLines = new ArrayList<String>();
        //TestRead
        try {
            testLines = theGame.readToLines(theGame.DEFAULT_NOTE_FILE_PATH);
        } catch (IOException exception) {
            fail("IOException is caught");
            exception.printStackTrace();

        }

        assertEquals(testLines.size(), theGame.lines.size());

    }


}
