package test;

import interfaces.Clickable;
import model.NotePad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClickableTest {
    private Clickable notePad;

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
    void testClickHandler() {
        assertFalse(notePad.isClicked());

        notePad.clickHandler(true);

        assertTrue(notePad.isClicked());

        notePad.clickHandler(false);

        assertFalse(notePad.isClicked());

    }

    @Test
    void testDoubleClickHandler() {
        assertFalse(notePad.isDoubleClicked());

        notePad.doubleClickHandler(true);

        assertTrue(notePad.isDoubleClicked());

        notePad.doubleClickHandler(false);

        assertFalse(notePad.isDoubleClicked());

    }


}