package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.UiPanel;

import static org.junit.jupiter.api.Assertions.*;

class GameTimerTest {
    GameTimer timer;

    @BeforeEach
    void runBefore() {
        timer = new GameTimer();
    }

    @Test
    void timerInit() {
        assertEquals(UiPanel.CODE_BOX_WIDTH, timer.hackScreenTimer);
        assertEquals(1, timer.day);
        assertEquals(0, timer.desktopTimer);
    }

    @Test
    void updateDay() {
        timer.updateDay();
        assertEquals(2, timer.day);
        for (int i = timer.day; i < timer.MAX_DAY - 1; i++) {
            timer.updateDay();
        }
        assertEquals(timer.MAX_DAY - 1, timer.day);
        timer.updateDay();
        assertEquals(timer.MAX_DAY, timer.day);
        assertEquals(
                ConcreteHackingGame.getInstance().state, ConcreteHackingGame.getInstance().GAME_TRULY_OVER
        );
    }

    @Test
    void updateTime() {
        timer.updateTime();
        assertEquals(1, timer.desktopTimer);
        timer.desktopTimer = 23;
        timer.updateTime();
        assertEquals(0, timer.desktopTimer);
        assertEquals(2, timer.day);
    }

    @Test
    void updateHackScreenTimer() {
        ConcreteHackingGame.getInstance().setState(ConcreteHackingGame.getInstance().HACK_SCREEN);
        timer.updateHackScreenTimer();
        assertEquals(UiPanel.CODE_BOX_WIDTH - 2, timer.hackScreenTimer);
        timer.hackScreenTimer = 0;
        timer.updateHackScreenTimer();
        assertEquals(2, timer.day);
        assertEquals(ConcreteHackingGame.getInstance().getState(), ConcreteHackingGame.getInstance().GAME_OVER);
    }
}