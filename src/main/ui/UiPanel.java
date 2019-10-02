package ui;

import interfaces.Clickable;
import interfaces.Draggable;
import model.HackingGame;
import model.NotePad;
import model.NotePage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.util.List;


public class UiPanel extends JPanel {
    private static final int LOGIN_BOX_WIDTH = 200;
    private static final int LOGIN_BOX_LENGTH = 30;
    private static final String WELCOME = "WELCOME TO THE GAME";
    private static final String LOGIN_LABEL = "LOGIN: ";
    private static final String PASSWORD_LABEL = "PASSWORD: ";
    private static final String LOGIN_CONTENT = "root";

    private static final String GREETING_0 = "ADMINISTRATION TERMINAL...";
    private static final String GREETING_1 = "SESSION ENCRYPTED...";
    private static final String GREETING_2 = "PRESS ENTER TO LOG IN >";

    private static final String GAME_OVER_MESSAGE_0 = "SYSTEM FAILURE";
    private static final String GAME_OVER_MESSAGE_1 = "KERNEL PANIC!";
    private static final String GAME_OVER_MESSAGE_2 = "Files corrupted: error 23423413...pending...";
    private static final String GAME_OVER_MESSAGE_3 = "fatal ER # 45337QW32Z_WS @%$$";
    private static final String GAME_OVER_MESSAGE_4 = "PRESS R TO REBOOT";

    private static final int NAV_BAR_LENGTH = 30;
    private static final String NOTE_PAD = "NOTE_PAD";
    private static final String NOTE_PAGE_CLOSE_BTN = "NOTE_PAGE_CLOSE_BTN";
    private static final String NOTE_PAGE_INPUT = "NOTE_PAGE_INPUT";


    private HackingGame theGame;
    private boolean flash = false;
    private int centerY;
    private Color inputTextColor = new Color(173, 173, 173);


    public UiPanel(HackingGame theGame) {
        setPreferredSize(new Dimension(HackingGame.WIDTH, HackingGame.LENGTH));
        setBackground(Color.BLACK);
        this.theGame = theGame;
        this.centerY = theGame.LENGTH / 2 - LOGIN_BOX_LENGTH / 2;
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent event) {
                validateMouseDraggedNotePad(event);
            }

            @Override
            public void mouseMoved(MouseEvent event) {
                if (
                        validateMouse(event, NOTE_PAD)
                                || validateMouse(event, NOTE_PAGE_CLOSE_BTN)) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else if (validateMouse(event, NOTE_PAGE_INPUT)) {
                    setCursor(new Cursor(Cursor.TEXT_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

            }


        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (validateMouse(event, NOTE_PAD)) {
                    onMousePressedNotePad();
                }

            }

            @Override
            public void mouseReleased(MouseEvent event) {
                NotePad notePad = theGame.getNotePad();
                notePad.setPressed(false);
            }

            @Override
            public void mouseClicked(MouseEvent event) {
                resetInput();
                if (event.getClickCount() == 2) {
                    if (validateMouse(event, NOTE_PAD)) {
                        onMouseDoubleClickedNotePad();
                    }
                } else if (event.getClickCount() == 1) {
                    if (validateMouse(event, NOTE_PAD)) {
                        onMouseClickedNotePad();
                    } else if (validateMouse(event, NOTE_PAGE_CLOSE_BTN)) {
                        onMouseClickedNotePageCloseBtn();
                    } else if (validateMouse(event, NOTE_PAGE_INPUT)) {
                        onMouseClickedNotePageInput();
                    } else {
                        Clickable notePad = theGame.getNotePad();
                        notePad.clickHandler(false);

                    }

                }
            }
        });


    }


    private boolean validateMouse(MouseEvent event, String type) {
        NotePad notePad = theGame.getNotePad();
        NotePage notePage = theGame.getNotePage();
        Point point = event.getPoint();
        double mouseX = point.getX();
        double mouseY = point.getY();
        if (type == NOTE_PAD && notePad.isMouseOver(mouseX, mouseY)) {
            return true;
        } else if (type == NOTE_PAGE_CLOSE_BTN && notePage.isMouseOverCloseBtn(mouseX, mouseY)) {
            return true;
        } else if (type == NOTE_PAGE_INPUT && notePage.isMouseOverInput(mouseX, mouseY)) {
            return true;
        }
        return false;
    }

    private void resetInput() {
        NotePage notePage = theGame.getNotePage();
        notePage.setInputContent(notePage.DEFAULT_INPUT);
        inputTextColor = new Color(173, 173, 173);
        notePage.setInputHasCursor(false);
    }

    private void validateMouseDraggedNotePad(MouseEvent event) {
        Draggable notePad = theGame.getNotePad();
        Point point = event.getPoint();
        double mouseX = validateMouseX(point.getX());
        double mouseY = validateMouseY(point.getY());
        if (notePad.isPressed()) {
            notePad.dragHandler(mouseX, mouseY);
        }


    }

    private void onMouseClickedNotePageInput() {
        NotePage notePage = theGame.getNotePage();
        if (!notePage.inputHasCursor()) {
            notePage.setInputContent("");
            inputTextColor = new Color(0, 0, 0);
            notePage.setInputHasCursor(true);
        }
    }

    private void onMousePressedNotePad() {
        NotePad notePad = theGame.getNotePad();
        notePad.setPressed(true);


    }

    private void onMouseClickedNotePad() {
        Clickable notePad = theGame.getNotePad();
        notePad.clickHandler(true);


    }

    private void onMouseClickedNotePageCloseBtn() {
        NotePad notePad = theGame.getNotePad();
        notePad.doubleClickHandler(false);
    }

    private void onMouseDoubleClickedNotePad() {
        Clickable notePad = theGame.getNotePad();
        notePad.doubleClickHandler(true);


    }


    private double validateMouseX(double mouseX) {
        if (mouseX < 0) {
            return 0;
        } else if (mouseX > theGame.WIDTH - 80) {
            return theGame.WIDTH - 80;
        } else {
            return mouseX;
        }
    }

    private double validateMouseY(double mouseY) {
        if (mouseY < NAV_BAR_LENGTH) {
            return NAV_BAR_LENGTH;
        } else if (mouseY > theGame.LENGTH - 130) {
            return theGame.LENGTH - 130;
        } else {
            return mouseY;
        }
    }


    @Override
    protected void paintComponent(Graphics gameGraphics) {
        super.paintComponent(gameGraphics);

        if (theGame.isLogIn()) {
            displayLogInScreen(gameGraphics);
        } else if (theGame.isOver()) {
            displayGameOverScreen(gameGraphics);
        } else {
            displayGameScreen(gameGraphics);
        }


    }


    // MODIFIES: gameGraphics
    // EFFECTS:  draws GAME_OVER_MESSAGE strings, and replay instructions onto gameGraphics
    private void displayGameOverScreen(Graphics gameGraphics) {
        Color savedColor = gameGraphics.getColor();
        Color messageTextColor = new Color(255, 0, 0);
        drawMessage(GAME_OVER_MESSAGE_0, gameGraphics, 50, 0, centerY - 150, messageTextColor);
        drawMessage(GAME_OVER_MESSAGE_1, gameGraphics, 25, 0, centerY - 100, messageTextColor);
        drawMessage(GAME_OVER_MESSAGE_2, gameGraphics, 10, 0, centerY - 50, messageTextColor);
        drawMessage(GAME_OVER_MESSAGE_3, gameGraphics, 10, 0, centerY, messageTextColor);
        int stringWidth = drawMessage(GAME_OVER_MESSAGE_4, gameGraphics, 25, 0, centerY + 50, messageTextColor);
        gameGraphics.setColor(new Color(255, 255, 255));
        drawFlashingCursor(gameGraphics,
                (theGame.WIDTH - stringWidth) / 2 + 10,
                centerY + 100,
                stringWidth,
                new Color(255, 255, 255));
        gameGraphics.setColor(savedColor);


    }

    // Draws the game
    // MODIFIES: gameGraphics
    // EFFECTS:  draws the game onto gameGraphics
    private void displayGameScreen(Graphics gameGraphics) {
        NotePad notePad = theGame.getNotePad();
        drawRect(gameGraphics, new Color(78, 78, 78), 0, 0, theGame.WIDTH, NAV_BAR_LENGTH);
        drawRect(gameGraphics, new Color(56, 91, 110), 0, 30, theGame.WIDTH, theGame.LENGTH);
        drawNotePad(gameGraphics);
        if (notePad.isDoubleClicked()) {
            drawNotePage(gameGraphics);
        }

    }

    private void drawNotePage(Graphics gameGraphics) {
        drawMainNotePage(gameGraphics);
        drawNavBar(gameGraphics);
        drawCloseBtn(gameGraphics);
        drawInput(gameGraphics);
        drawInputContent(gameGraphics);

    }

    private void drawInputContent(Graphics gameGraphics) {
        NotePage notePage = theGame.getNotePage();

        int contentWidth = drawMessage(notePage.getInputContent(),
                gameGraphics,
                12,
                notePage.getInputX() + 10,
                notePage.getInputY() + 20,
                inputTextColor);
        if (notePage.inputHasCursor()) {

            drawFlashingCursor(gameGraphics,
                    notePage.getInputX() + 10,
                    notePage.getInputY() + 35,
                    contentWidth,
                    new Color(0, 0, 0));
        }

    }

    private void drawInput(Graphics gameGraphics) {
        NotePage notePage = theGame.getNotePage();
        drawRect(gameGraphics,
                new Color(0, 0, 0),
                notePage.getInputX(),
                notePage.getInputY(),
                notePage.getInputWidth(),
                notePage.getInputHeight());
        drawRect(gameGraphics,
                new Color(255, 255, 255),
                notePage.getInputX() + 1,
                notePage.getInputY() + 1,
                notePage.getInputWidth() - 2,
                notePage.getInputHeight() - 2);

    }

    private void drawCloseBtn(Graphics gameGraphics) {
        NotePage notePage = theGame.getNotePage();
        drawRect(gameGraphics,
                new Color(163, 34, 39),
                notePage.getCloseBtnX(),
                notePage.getCloseBtnY(),
                notePage.getCloseBtnWidth(),
                notePage.getCloseBtnHeight());
        drawMessage("X",
                gameGraphics,
                20,
                notePage.getCloseBtnX() + 8,
                notePage.getCloseBtnY() + 23,
                new Color(255, 255, 255));
    }

    private void drawNavBar(Graphics gameGraphics) {
        NotePage notePage = theGame.getNotePage();
        drawRect(gameGraphics,
                new Color(228, 227, 228),
                notePage.getNavBarX(),
                notePage.getNavBarY(),
                notePage.getNavBarWidth(),
                notePage.getNavBarHeight());
        drawMessage("Notes",
                gameGraphics,
                20,
                notePage.getNavBarX() + 10,
                notePage.getNavBarY() + 25,
                new Color(0, 0, 0));
    }

    private void drawMainNotePage(Graphics gameGraphics) {
        NotePage notePage = theGame.getNotePage();
        drawRect(gameGraphics,
                new Color(255, 255, 255),
                notePage.getMainPageX(),
                notePage.getMainPageY(),
                notePage.getMainPageWidth(),
                notePage.getMainPageHeight());
        drawNotes(gameGraphics);
    }

    private void drawNotes(Graphics gameGraphics) {
        List<String> lines = theGame.lines;
        NotePage notePage = theGame.getNotePage();
        int offsetY = 25;
        int lineHeight = 15;
        for (String line : lines) {
            if (!(notePage.getMainPageY() + offsetY
                    >= notePage.getMainPageY()
                    + notePage.getMainPageHeight()
                    - notePage.getInputHeight())) {
                drawMessage(line,
                        gameGraphics,
                        12,
                        notePage.getMainPageX() + 10,
                        notePage.getMainPageY() + offsetY,
                        Color.BLACK);
                offsetY += lineHeight;
            }
        }
    }

    private void drawNotePad(Graphics gameGraphics) {
        NotePad notePad = theGame.getNotePad();
        if (notePad.isClicked()) {
            drawNotePadIconWithBackground(gameGraphics);
        } else {
            drawNotePadIconWithoutBackground(gameGraphics);
        }

        drawMessage(
                "Notepad",
                gameGraphics,
                15,
                notePad.getIconX() + 15,
                notePad.getIconY() + notePad.getIconHeight() + 20,
                new Color(255, 255, 255));
    }

    private void drawNotePadIconWithBackground(Graphics gameGraphics) {
        NotePad notePad = theGame.getNotePad();
        gameGraphics.drawImage(notePad.getIconImg(),
                notePad.getIconX(),
                notePad.getIconY(),
                notePad.getIconWidth(),
                notePad.getIconHeight(), new Color(204, 232, 255, 25),
                null);
    }

    private void drawNotePadIconWithoutBackground(Graphics gameGraphics) {
        NotePad notePad = theGame.getNotePad();
        gameGraphics.drawImage(notePad.getIconImg(),
                notePad.getIconX(),
                notePad.getIconY(),
                notePad.getIconWidth(),
                notePad.getIconHeight(),
                null);
    }

    //Draw rect component of main game screen
    //MODIFIES: gameGraphics
    //EFFECTS: draws all the rectangular component of main game screen
    private void drawRect(Graphics gameGraphics, Color rectColor, int rectX, int rectY, int rectWidth, int rectLength) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(rectColor);
        gameGraphics.fillRect(
                rectX, rectY,
                rectWidth,
                rectLength);
        gameGraphics.setColor(savedColor);


    }


    // Draws log in screen
    // MODIFIES: gameGraphics
    // EFFECTS:  draws log in screen onto gameGraphics
    private void displayLogInScreen(Graphics gameGraphics) {
        Color messageTextColor = new Color(0, 215, 0);
        drawMessage(WELCOME, gameGraphics, 50, 0, centerY - 150, messageTextColor);
        drawLogInBox(gameGraphics,
                centerY,
                LOGIN_LABEL,
                LOGIN_CONTENT);
        drawLogInBox(gameGraphics,
                centerY + 50,
                PASSWORD_LABEL, theGame.getPassword());
        int messageY = centerY + 100;
        drawMessage(GREETING_0, gameGraphics, 10, 0, messageY + 100, messageTextColor);
        drawMessage(GREETING_1, gameGraphics, 10, 0, messageY + 120, messageTextColor);
        drawMessage(GREETING_2, gameGraphics, 20, 0, messageY + 200, messageTextColor);


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
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(textColor);
        gameGraphics.setFont(new Font("Arial", fontSize, fontSize));
        FontMetrics fontMetrics = gameGraphics.getFontMetrics();
        int stringWidth = displayString(message, gameGraphics, fontMetrics, messageX, messageY);
        gameGraphics.setColor(savedColor);
        return stringWidth;


    }


    // MODIFIES: gameGraphics
    // EFFECTS: Draw log in boxes with label and content on login screen
    private void drawLogInBox(Graphics gameGraphics, int logInBoxY, String label, String content) {
        Color savedColor = gameGraphics.getColor();
        int logInBoxX = theGame.WIDTH / 2 - LOGIN_BOX_WIDTH / 2 + 50;
        drawBoxes(gameGraphics, logInBoxX, logInBoxY);
        drawLogInLabel(gameGraphics, logInBoxX, logInBoxY, label);
        if (content == LOGIN_CONTENT) {
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
                LOGIN_BOX_WIDTH,
                LOGIN_BOX_LENGTH);
        gameGraphics.setColor(new Color(0, 215, 0));
        gameGraphics.drawRect(
                logInBoxX, logInBoxY,
                LOGIN_BOX_WIDTH + 2,
                LOGIN_BOX_LENGTH + 2);
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
        drawFlashingCursor(gameGraphics, offsetLogInBoxX, offsetLogInBoxY, contentWidth, new Color(255, 255, 255));
        gameGraphics.setColor(savedColor);

    }


    // MODIFIES: gameGraphics
    // EFFECTS: Draw content for password boxes on login screen
    private void drawFlashingCursor(Graphics gameGraphics,
                                    int logInBoxX,
                                    int logInBoxY,
                                    int contentWidth,
                                    Color color) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(color);
        gameGraphics.setFont(new Font("Arial", 25, 25));
        String str = "|";
        if (flash) {
            str = "";
        }
        gameGraphics.drawString(str,
                logInBoxX + contentWidth,
                logInBoxY - 8);
        gameGraphics.setColor(savedColor);

    }

    // Centres a string on the screen
    // MODIFIES: gameGraphics
    // EFFECTS:  display the string str horizontally onto g at vertical position y
    private int displayString(String string, Graphics gameGraphics, FontMetrics fontMetrics, int stringX, int stringY) {
        int stringWidth = fontMetrics.stringWidth(string);
        if (stringX == 0) {
            gameGraphics.drawString(string, (theGame.WIDTH - stringWidth) / 2, stringY);
        } else {
            gameGraphics.drawString(string, stringX, stringY);
        }
        return stringWidth;
    }

    //MODIFIES: flash
    //EFFECTS: invert flash boolean variable
    public void updateFlash() {
        flash = !flash;
    }


}
