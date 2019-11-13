package ui;

import model.*;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;


public class UiPanel extends JPanel {


    private ConcreteHackingGame theGame;
    private int centerY;
    private MouseMotionListener mlDrag;
    private MouseListener mlPressed;
    private IconDisplay iconDisplay;
    private PageDisplay pageDisplay;
    private MessageDisplay messageDisplay;
    private Constants constants;
    private RectDisplay rectDisplay;
    private FlashCursorDisplay flashCursorDisplay;


    public UiPanel(ConcreteHackingGame theGame) {

        constants = new Constants();
        flashCursorDisplay = new FlashCursorDisplay();

        List<Icon> icons = new ArrayList<Icon>();
        //Orders matter
        icons.add(theGame.getNotePadIcon());
        icons.add(theGame.getBrowserIcon());
        iconDisplay = new IconDisplay(icons, theGame);


        //orders matter
        List<Page> pages = new ArrayList<Page>();
        pages.add(theGame.getNotePadPage());
        pages.add(theGame.getBrowserPage());
        pageDisplay = new PageDisplay(pages, theGame);


        setPreferredSize(new Dimension(ConcreteHackingGame.WIDTH, ConcreteHackingGame.LENGTH));
        setBackground(Color.BLACK);
        this.theGame = theGame;
        messageDisplay = new MessageDisplay(theGame);
        rectDisplay = new RectDisplay();
        this.centerY = theGame.LENGTH / 2 - constants.LOGIN_BOX_LENGTH / 2;


        addMouseMotionListener(new MouseAdapter() {


            @Override
            public void mouseMoved(MouseEvent event) {

                if (
                        iconDisplay.validateMouse(event, constants.NOTEPAD)
                                || iconDisplay.validateMouse(event, constants.BROWSER)
                                || pageDisplay.validateMouse(event, constants.PAGE_CLOSE_BTN, constants.NOTEPAD)
                                || pageDisplay.validateMouse(event, constants.PAGE_CLOSE_BTN, constants.BROWSER)
                                || pageDisplay.validateMouse(event, constants.WEB_LINK, constants.BROWSER)) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else if (pageDisplay.validateMouse(event, constants.PAGE_INPUT, constants.NOTEPAD)) {
                    setCursor(new Cursor(Cursor.TEXT_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

            }


        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (pageDisplay.validateMouse(event, constants.PAGE_NAVBAR, constants.NOTEPAD)) {

                    onMousePressedPage(constants.NOTEPAD);
                } else if (pageDisplay.validateMouse(event, constants.PAGE_NAVBAR, constants.BROWSER)) {
                    onMousePressedPage(constants.BROWSER);
                }

            }


            @Override
            public void mouseClicked(MouseEvent event) {
                resetInput();
                if (event.getClickCount() == 2) {
                    validateMouseOnDoubleClicked(event);
                } else if (event.getClickCount() == 1) {
                    checkMouseClicks(event);

                }
            }
        });


    }

    private void checkMouseClicks(MouseEvent event) {
        if (iconDisplay.validateMouse(event, constants.NOTEPAD)) {
            iconDisplay.onMouseClickedIcon(constants.NOTEPAD);
        } else if (iconDisplay.validateMouse(event, constants.BROWSER)) {
            iconDisplay.onMouseClickedIcon(constants.BROWSER);
        } else if (pageDisplay.validateMouse(event, constants.PAGE_CLOSE_BTN, constants.NOTEPAD)) {
            iconDisplay.onMouseClickedPageCloseBtn(constants.NOTEPAD);
        } else if (pageDisplay.validateMouse(event, constants.PAGE_CLOSE_BTN, constants.BROWSER)) {
            theGame.getBrowserPage().mainPageState = theGame.getBrowserPage().HOME;
            iconDisplay.onMouseClickedPageCloseBtn(constants.BROWSER);
        } else if (pageDisplay.validateMouse(event, constants.PAGE_INPUT, constants.NOTEPAD)) {
            pageDisplay.onMouseClickedPageInput(constants.NOTEPAD);
        } else if (pageDisplay.validateMouse(event, constants.WEB_LINK, constants.BROWSER)) {
            return;
        }
    }

    private void validateMouseOnDoubleClicked(MouseEvent event) {
        if (iconDisplay.validateMouse(event, constants.NOTEPAD)) {
            iconDisplay.onMouseDoubleClickedIcon(constants.NOTEPAD);
        } else if (iconDisplay.validateMouse(event, constants.BROWSER)) {
            iconDisplay.onMouseDoubleClickedIcon(constants.BROWSER);
        }
    }


    private void resetInput() {

        pageDisplay.getPage(constants.NOTEPAD).setInputContent(constants.DEFAULT_INPUT_NOTEPAD);
        pageDisplay.getPage(constants.BROWSER).setInputContent(constants.DEFAULT_INPUT_BROWSER);

        pageDisplay.getPage(constants.BROWSER).setInputTextColor(new Color(173, 173, 173));
        pageDisplay.getPage(constants.NOTEPAD).setInputTextColor(new Color(173, 173, 173));

        pageDisplay.getPage(constants.NOTEPAD).setInputHasCursor(false);
        pageDisplay.getPage(constants.BROWSER).setInputHasCursor(false);

        iconDisplay.getIcon(constants.BROWSER).clickHandler(false);
        iconDisplay.getIcon(constants.NOTEPAD).clickHandler(false);

    }


    private void onMousePressedPage(int index) {
        pageDisplay.getPage(index).setPressed(true);

        mlDrag = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                pageDisplay.validateMouseDraggedPage(e, index);
            }


        };

        mlPressed = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {

                pageDisplay.getPage(index).setPressed(false);
                removeMouseMotionListener(mlDrag);
                removeMouseListener(mlPressed);


            }
        };
        addMouseMotionListener(mlDrag);
        addMouseListener(mlPressed);

    }


    @Override
    protected void paintComponent(Graphics gameGraphics) {
        super.paintComponent(gameGraphics);

        if (theGame.isLogIn()) {
            displayLogInScreen(gameGraphics);
        } else if (theGame.isOver()) {
            displayGameOverScreen(gameGraphics);
        } else if (theGame.isPlayed()) {
            displayGameScreen(gameGraphics);
        } else {
            displayHackScreen(gameGraphics);
        }


    }

    private void displayHackScreen(Graphics gameGraphics) {
        Color savedColor = gameGraphics.getColor();
        Color messageTextColor = new Color(0, 215, 0);
        drawMessage(constants.HACK_SCREEN_1, gameGraphics, 50, 0, centerY - 250, messageTextColor);
        drawMessage(constants.HACK_SCREEN_2, gameGraphics, 25, 0, centerY - 150, messageTextColor);
        drawMessage(constants.HACK_SCREEN_3, gameGraphics, 10, 0, centerY - 100, messageTextColor);
        drawMessage(constants.HACK_SCREEN_4,
                gameGraphics, 10, 0, centerY - 50, messageTextColor);
        drawMessage(constants.HACK_SCREEN_5,
                gameGraphics, 10, 0, centerY, messageTextColor);
        drawCodes(gameGraphics);
        gameGraphics.setColor(savedColor);
    }

    private void drawCodes(Graphics gameGraphics) {
        Color savedColor = gameGraphics.getColor();
        Color messageTextColor = new Color(0, 215, 0);
        gameGraphics.setColor(messageTextColor);
        gameGraphics.drawRect((theGame.WIDTH / 2) - 300, centerY + 50, 600, 50);
        drawRect(gameGraphics, new Color(42, 42, 43), (theGame.WIDTH / 2) - 298, centerY + 52, 50, 48);
        drawMessage("1", gameGraphics, 30, (theGame.WIDTH / 2) - 280, centerY + 85, Color.WHITE);
        drawMessage(theGame.getHackScreen().askedCode, gameGraphics, 15,
                (theGame.WIDTH / 2) - 240, centerY + 77, messageTextColor);
        drawRect(gameGraphics, messageTextColor, (theGame.WIDTH / 2) - 300, centerY + 130, 30, 30);
        drawMessage(">_", gameGraphics, 20, (theGame.WIDTH / 2) - 298, centerY + 150, Color.WHITE);
        drawInputCode(gameGraphics);
        gameGraphics.setColor(savedColor);
    }

    private void drawInputCode(Graphics gameGraphics) {
        int contentWidth = drawMessage(theGame.getHackScreen().inputCode,
                gameGraphics, 20, (theGame.WIDTH / 2) - 250,
                centerY + 150,
                Color.WHITE);
        flashCursorDisplay.drawFlashingCursor(gameGraphics,
                (theGame.WIDTH / 2) - 250, centerY + 165, contentWidth, Color.WHITE);
    }


    // MODIFIES: gameGraphics
    // EFFECTS:  draws GAME_OVER_MESSAGE strings, and replay instructions onto gameGraphics
    private void displayGameOverScreen(Graphics gameGraphics) {
        Color savedColor = gameGraphics.getColor();
        Color messageTextColor = new Color(255, 0, 0);
        drawMessage(constants.GAME_OVER_MESSAGE_0, gameGraphics, 50, 0, centerY - 150, messageTextColor);
        drawMessage(constants.GAME_OVER_MESSAGE_1, gameGraphics, 25, 0, centerY - 100, messageTextColor);
        drawMessage(constants.GAME_OVER_MESSAGE_2,
                gameGraphics, 10, 0, centerY - 50, messageTextColor);
        drawMessage(constants.GAME_OVER_MESSAGE_3,
                gameGraphics, 10, 0, centerY, messageTextColor);
        int stringWidth = drawMessage(
                constants.GAME_OVER_MESSAGE_4,
                gameGraphics, 25, 0, centerY + 50, messageTextColor);
        gameGraphics.setColor(new Color(255, 255, 255));
        gameGraphics.setColor(savedColor);


    }

    // Draws the game
    // MODIFIES: gameGraphics
    // EFFECTS:  draws the game onto gameGraphics
    private void displayGameScreen(Graphics gameGraphics) {
        drawRect(gameGraphics,
                new Color(78, 78, 78), 0, 0, theGame.WIDTH, constants.NAV_BAR_LENGTH);
        drawRect(gameGraphics,
                new Color(56, 91, 110), 0, 30, theGame.WIDTH, theGame.LENGTH);
        iconDisplay.drawIcon(gameGraphics);
        if (iconDisplay.getIcon(constants.NOTEPAD).isDoubleClicked()) {
            pageDisplay.drawPage(gameGraphics, constants.NOTEPAD);
        }
        if (iconDisplay.getIcon(constants.BROWSER).isDoubleClicked()) {
            pageDisplay.drawPage(gameGraphics, constants.BROWSER);
        }

    }


    //Draw rect component of main game screen
    //MODIFIES: gameGraphics
    //EFFECTS: draws all the rectangular component of main game screen
    private void drawRect(Graphics gameGraphics, Color rectColor, int rectX, int rectY, int rectWidth, int rectLength) {
        rectDisplay.drawRect(gameGraphics, rectColor, rectX, rectY, rectWidth, rectLength);


    }


    // Draws log in screen
    // MODIFIES: gameGraphics
    // EFFECTS:  draws log in screen onto gameGraphics
    private void displayLogInScreen(Graphics gameGraphics) {
        Color messageTextColor = new Color(0, 215, 0);
        drawMessage(constants.WELCOME, gameGraphics, 50, 0, centerY - 150, messageTextColor);
        drawLogInBox(gameGraphics,
                centerY,
                constants.LOGIN_LABEL,
                constants.LOGIN_CONTENT);
        drawLogInBox(gameGraphics,
                centerY + 50,
                constants.PASSWORD_LABEL, theGame.getPassword());
        int messageY = centerY + 100;
        drawMessage(constants.GREETING_0, gameGraphics, 10, 0, messageY + 100, messageTextColor);
        drawMessage(constants.GREETING_1, gameGraphics, 10, 0, messageY + 120, messageTextColor);
        drawMessage(constants.GREETING_2, gameGraphics, 20, 0, messageY + 200, messageTextColor);


    }

    //Draws message
    //MODIFIES: gameGraphics
    //EFFECTS: draws messages onto game Graphics
    private int drawMessage(
            String message,
            Graphics gameGraphics,
            int fontSize,
            int messageX,
            int messageY,
            Color textColor) {

        return messageDisplay.drawMessage(message, gameGraphics, fontSize, messageX, messageY, textColor);


    }


    // MODIFIES: gameGraphics
    // EFFECTS: Draw log in boxes with label and content on login screen
    private void drawLogInBox(Graphics gameGraphics, int logInBoxY, String label, String content) {
        Color savedColor = gameGraphics.getColor();
        int logInBoxX = theGame.WIDTH / 2 - constants.LOGIN_BOX_WIDTH / 2 + 50;
        drawBoxes(gameGraphics, logInBoxX, logInBoxY);
        drawLogInLabel(gameGraphics, logInBoxX, logInBoxY, label);
        if (content == constants.LOGIN_CONTENT) {
            drawLogInContent(gameGraphics, logInBoxX, logInBoxY, content);
        } else {
            drawPasswordContent(gameGraphics, logInBoxX, logInBoxY, content);
        }
        gameGraphics.setColor(savedColor);

    }

    private void drawBoxes(Graphics gameGraphics, int logInBoxX, int logInBoxY) {
        gameGraphics.setColor(new Color(0, 215, 0, 100));
        gameGraphics.fillRect(
                logInBoxX + 1, logInBoxY + 1,
                constants.LOGIN_BOX_WIDTH,
                constants.LOGIN_BOX_LENGTH);
        gameGraphics.setColor(new Color(0, 215, 0));
        gameGraphics.drawRect(
                logInBoxX, logInBoxY,
                constants.LOGIN_BOX_WIDTH + 2,
                constants.LOGIN_BOX_LENGTH + 2);
    }

    // MODIFIES: gameGraphics
    // EFFECTS: Draw labels for log in boxes on login screen
    private void drawLogInLabel(Graphics gameGraphics, int logInBoxX, int logInBoxY, String label) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(new Color(0, 215, 0, 150));
        gameGraphics.setFont(new Font("Arial", 28, 28));
        FontMetrics fontMetrics = gameGraphics.getFontMetrics();
        int labelWidth = fontMetrics.stringWidth(label);
        gameGraphics.drawString(label,
                logInBoxX - labelWidth,
                logInBoxY + 28);
        gameGraphics.setColor(savedColor);
    }

    // MODIFIES: gameGraphics
    // EFFECTS: Draw content for log in boxes on login screen
    private void drawLogInContent(Graphics gameGraphics, int logInBoxX, int logInBoxY, String content) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(new Color(255, 255, 255));
        gameGraphics.setFont(new Font("Arial", 25, 25));
        gameGraphics.drawString(content,
                logInBoxX + 10,
                logInBoxY + 23);
        gameGraphics.setColor(savedColor);
    }

    // MODIFIES: gameGraphics
    // EFFECTS: Draw content for password boxes on login screen
    private void drawPasswordContent(Graphics gameGraphics, int logInBoxX, int logInBoxY, String content) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(new Color(255, 255, 255));
        gameGraphics.setFont(new Font("Arial", 25, 25));
        int contentWidth = gameGraphics.getFontMetrics().stringWidth(content);
        int offsetLogInBoxX = logInBoxX + 10;
        int offsetLogInBoxY = logInBoxY + 30;
        gameGraphics.drawString(content,
                offsetLogInBoxX,
                offsetLogInBoxY);
        flashCursorDisplay.drawFlashingCursor(gameGraphics,
                offsetLogInBoxX, offsetLogInBoxY, contentWidth, new Color(255, 255, 255));
        gameGraphics.setColor(savedColor);

    }


}
