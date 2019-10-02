package test;

import interfaces.Clickable;
import interfaces.Draggable;
import model.NotePad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DraggableTest {
    private Draggable notePad;

    private Image getImage(String filePath) {
        try {
            File imageFile = new File(filePath);
            Image imageReturn = ImageIO.read(imageFile);
            return imageReturn;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @BeforeEach
    void runBefore() {
        Image notePadImage = getImage("src/img/notePadIcon.png");
        notePad = new NotePad(50, 50, 80, 90, notePadImage);
    }

    @Test
    void testDragHandler() {

        assertEquals(50, notePad.getIconX());
        assertEquals(50, notePad.getIconY());

        notePad.dragHandler(20, 20);

        assertEquals(20, notePad.getIconX());
        assertEquals(20, notePad.getIconY());


        notePad.dragHandler(30, 30);

        assertEquals(30, notePad.getIconX());
        assertEquals(30, notePad.getIconY());


    }
}
