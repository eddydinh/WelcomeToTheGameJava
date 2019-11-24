package model;

import ui.Constants;
import ui.UiPanel;


public class GameTimer {
    public static int hackScreenTimer;
    public static int desktopTimer;
    public static int day;
    public static final int MAX_DAY = 5;


    public GameTimer() {
        hackScreenTimer = UiPanel.CODE_BOX_WIDTH;
        desktopTimer = 0;
        day = 1;

    }

    //MODIFIES: this
    //EFFECT: increase day by one check if day is more than max day, if yes, update game state to LOST

    public static void updateDay() {
        ConcreteHackingGame hackingGame = ConcreteHackingGame.getInstance();
        day++;
        if (day >= MAX_DAY) {
            hackingGame.getHackScreen().notifyObservers(hackingGame.GAME_TRULY_OVER);
        }
    }

    //MODIFIES: this
    //EFFECT: increase desktop time by 1, if more than 23, increase day by 1, return desktop time to 0
    public static void updateTime() {

        desktopTimer += 1;
        if (desktopTimer > 23) {
            desktopTimer = 0;
            updateDay();
        }


    }


    //MODIFIES: this
    //EFFECT: decrease hack screen time by 2, if under 0, switch game state to get hacked, increase day by one

    public static void updateHackScreenTimer() {
        ConcreteHackingGame hackingGame = ConcreteHackingGame.getInstance();

        hackScreenTimer -= 2;
        if (hackScreenTimer <= 0) {

            updateDay();


            hackingGame.getHackScreen().notifyObservers(hackingGame.GAME_OVER);
        }

    }

}
