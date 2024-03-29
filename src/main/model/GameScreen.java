package model;

import java.util.ArrayList;
import java.util.List;

public abstract class GameScreen {
    private List<HackingGame> observers = new ArrayList<>();
    protected double chance;
    protected String inputCode;
    protected String askedCode;
    public static final double CHANCE = 0.45;


    public GameScreen() {
        chance = CHANCE;
        inputCode = "";
        askedCode = "";
    }


    //MODIFIES: this
    //EFFECT: add observer to list of observers
    public void addObserver(HackingGame observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    //MODIFIES: observer
    //EFFECT: call update method in observer
    public void notifyObservers(String gameState) {
        for (HackingGame observer : observers) {
            double d = Math.random();
            if (gameState == observer.HACK_SCREEN
                    && observer.state == observer.GAME_IS_PLAYED
                    && d < chance) {
                observer.update(gameState);
            } else if (gameState == observer.GAME_IS_PLAYED && observer.state == observer.HACK_SCREEN) {
                observer.update(gameState);
            } else if (gameState == observer.GAME_OVER && observer.state == observer.HACK_SCREEN) {
                observer.update(gameState);
            } else if (gameState == observer.GAME_TRULY_OVER) {
                observer.update(gameState);
            }

        }


    }


    public String getInputCode() {
        return inputCode;
    }


    public String getAskedCode() {
        return askedCode;
    }

}
