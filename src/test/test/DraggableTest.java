package test;

import interfaces.Draggable;
import model.NotePadPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class DraggableTest {
    private Draggable notePage;


    @BeforeEach
    void runBefore() {
        notePage = new NotePadPage(300, 200, 400, 500, "Notes");
    }

    @Test
    void testDragHandler() {

        assertEquals(300, notePage.getMainPageX());
        assertEquals(200, notePage.getMainPageY());

        notePage.dragHandler(20, 20);

        assertEquals(20, notePage.getMainPageX());
        assertEquals(20, notePage.getMainPageY());


        notePage.dragHandler(30, 30);

        assertEquals(30, notePage.getMainPageX());
        assertEquals(30, notePage.getMainPageY());


    }
}
