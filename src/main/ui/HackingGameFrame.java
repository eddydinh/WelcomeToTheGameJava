package ui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.HackingGame;


public class HackingGameFrame extends JFrame {
    private static final int INTERVAL = 20;
    private static final int INTERVAL_FLASH = 500;
    private static final int INTERVAL_INITIALIZE = 10000;
    private UiPanel uiPanel;
    private HackingGame theGame;
    private FlashCursorDisplay flashCursorDisplay;

    HackingGameFrame() {
        super("WELCOME TO THE GAME");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        theGame = HackingGame.getInstance();
        uiPanel = new UiPanel(theGame);
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

                theGame.getHackScreen().notifyObservers(theGame.HACK_SCREEN);
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
            int keyCode = (int) e.getKeyChar();
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
