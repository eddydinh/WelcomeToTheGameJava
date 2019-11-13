package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GameScreen {
    private List<Game> observers = new ArrayList<>();
    protected double chance;
    public String inputCode;
    public String askedCode;


    public GameScreen() {
        chance = 0.5;
        inputCode = "";
        askedCode = "";
    }


    public void addObserver(Game observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void notifyObservers(String gameState) {
        for (Game observer : observers) {
            double d = Math.random();
            if (gameState == observer.HACK_SCREEN
                    && observer.state == observer.GAME_IS_PLAYED
                    && d < chance) {
                observer.update(gameState);
            } else if (gameState == observer.GAME_IS_PLAYED && observer.state == observer.HACK_SCREEN) {
                observer.update(gameState);
            } else if (gameState == observer.GAME_OVER && observer.state == observer.HACK_SCREEN) {
                observer.update(gameState);
            }

        }


    }
}
