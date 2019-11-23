package ui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.ConcreteHackingGame;
import model.GameTimer;
import network.GetRandomStringAPI;


public class HackingGameFrame extends JFrame {
    private static final int INTERVAL = 20;
    private static final int INTERVAL_FLASH = 500;
    private static final int INTERVAL_INITIALIZE = 20000;
    private static final int INTERVAL_DURING = 100;
    private static final int INTERVAL_DESKTOP = 5000;
    private UiPanel uiPanel;
    private ConcreteHackingGame theGame;
    private FlashCursorDisplay flashCursorDisplay;
    private static HackingGameFrame HACKING_GAME_FRAME;

    public static HackingGameFrame getInstance() {
        if (HACKING_GAME_FRAME == null) {
            HACKING_GAME_FRAME = new HackingGameFrame();
        }
        return HACKING_GAME_FRAME;
    }

    HackingGameFrame() {

        super("WELCOME TO THE GAME");
        HACKING_GAME_FRAME = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        theGame = new ConcreteHackingGame();
        uiPanel = new UiPanel(theGame);
        new GetRandomStringAPI();
        flashCursorDisplay = new FlashCursorDisplay();
        uiPanel.setLayout(null);
        add(uiPanel);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
        addTimerFlash();
        addTimerInitializeHackScreen();
        addTimerDuringHackScreen();
        addTimerDesktop();


    }

    // Set up timer
    // MODIFIES: none
    // EFFECTS: initializes a timer that updates game each
    // INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                uiPanel.repaint();
            }
        });
        t.start();
    }

    // Set up timer
    // MODIFIES: none
    // EFFECTS: initializes a timer that updates game each
    // INTERVAL milliseconds
    private void addTimerDesktop() {
        Timer t = new Timer(INTERVAL_DESKTOP, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (theGame.getState() == theGame.GAME_IS_PLAYED) {
                    GameTimer.updateTime();
                }

            }
        });
        t.start();
    }

    //Set up timer for flash
    //MODIFIES: UiPanel.flash
    //EFFECTS: invert UiPanel's flash boolean variable, make cursor flash
    //INTERVAL milliseconds
    private void addTimerFlash() {
        Timer t = new Timer(INTERVAL_FLASH, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                flashCursorDisplay.updateFlash();
            }
        });
        t.start();
    }


    //Set up timer for flash
    //MODIFIES: UiPanel.flash
    //EFFECTS: invert UiPanel's flash boolean variable, make cursor flash
    //INTERVAL milliseconds
    private void addTimerInitializeHackScreen() {
        Timer t = new Timer(INTERVAL_INITIALIZE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (theGame.isPlayed()) {
                    theGame.getHackScreen().notifyObservers(theGame.HACK_SCREEN);
                }
            }
        });
        t.start();
    }


    //Set up timer for flash
    //MODIFIES: UiPanel.flash
    //EFFECTS: invert UiPanel's flash boolean variable, make cursor flash
    //INTERVAL milliseconds
    private void addTimerDuringHackScreen() {
        Timer t = new Timer(INTERVAL_DURING, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (theGame.getState() == theGame.HACK_SCREEN) {
                    GameTimer.updateHackScreenTimer();
                }
            }
        });
        t.start();
    }

    // Centres frame on desktop
    // MODIFIES: this
    // EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            theGame.keyPressed(e.getKeyCode());

        }

        @Override
        public void keyTyped(KeyEvent e) {
            int keyCode = e.getKeyChar();
            char keyChar = e.getKeyChar();
            theGame.keyTyped(keyCode, keyChar);
        }


    }

    /*
     * Play the game
     */
    public static void main(String[] args) {
        new HackingGameFrame();
    }
}
