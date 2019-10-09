package test;

import model.HackingGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveLoadTest {
    private HackingGame theGame;

    @BeforeEach
    void runBefore() throws IOException {

        theGame = new HackingGame();
    }

    //Check content
    @Test
    void testReadWrite() throws IOException {
        //TestRead
        List<String> lines = Files.readAllLines(Paths.get("src/notepad.txt"));
        int size = lines.size();
        assertEquals(lines.size(), theGame.lines.size());

        //TestWrite
        theGame.lines.add("Hello World!");
        theGame.writeToFile();
        assertEquals(size + 1, theGame.lines.size());


    }
}
