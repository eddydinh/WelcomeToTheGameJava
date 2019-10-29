package ui;

import interfaces.Clickable;
import model.*;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private static final String ICON = "ICON";
    private static final String PAGE_CLOSE_BTN = "PAGE_CLOSE_BTN";
    private static final String PAGE_INPUT = "PAGE_INPUT";
    private static final String PAGE_NAVBAR = "PAGE_NAVBAR";


    private HackingGame theGame;
    private boolean flash = false;
    private int centerY;
    private Color iconBgColor = new Color(204, 232, 255, 25);
    private MouseMotionListener mlDrag;
    private MouseListener mlPressed;
    private NotePadIcon notePadIcon;
    private BrowserIcon browserIcon;
    private NotePadPage notePadPage;
    private BrowserPage browserPage;


    public UiPanel(HackingGame theGame) {
        notePadIcon = theGame.getNotePadIcon();
        browserIcon = theGame.getBrowserIcon();
        notePadPage = theGame.getNotePadPage();
        browserPage = theGame.getBrowserPage();
        setPreferredSize(new Dimension(HackingGame.WIDTH, HackingGame.LENGTH));
        setBackground(Color.BLACK);
        this.theGame = theGame;
        this.centerY = theGame.LENGTH / 2 - LOGIN_BOX_LENGTH / 2;
        addMouseMotionListener(new MouseAdapter() {


            @Override
            public void mouseMoved(MouseEvent event) {

                if (
                        validateMouse(event, ICON, notePadIcon)
                                || validateMouse(event, ICON, browserIcon)
                                || validateMouse(event, PAGE_CLOSE_BTN, notePadPage)
                                || validateMouse(event, PAGE_CLOSE_BTN, browserPage)) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else if (validateMouse(event, PAGE_INPUT, notePadPage)) {
                    setCursor(new Cursor(Cursor.TEXT_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

            }


        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (validateMouse(event, PAGE_NAVBAR, notePadPage)) {

                    onMousePressedPage(notePadPage);
                } else if (validateMouse(event, PAGE_NAVBAR, browserPage)) {
                    onMousePressedPage(browserPage);
                }

            }


            @Override
            public void mouseClicked(MouseEvent event) {
                resetInput();
                if (event.getClickCount() == 2) {
                    validateMouseOnDoubleClicked(event);
                } else if (event.getClickCount() == 1) {
                    if (validateMouse(event, ICON, notePadIcon)) {
                        onMouseClickedIcon(notePadIcon);
                    } else if (validateMouse(event, ICON, browserIcon)) {
                        onMouseClickedIcon(browserIcon);
                    } else if (validateMouse(event, PAGE_CLOSE_BTN, notePadPage)) {
                        onMouseClickedPageCloseBtn(notePadIcon);
                    } else if (validateMouse(event, PAGE_CLOSE_BTN, browserPage)) {
                        onMouseClickedPageCloseBtn(browserIcon);
                    } else if (validateMouse(event, PAGE_INPUT, notePadPage)) {
                        onMouseClickedPageInput(notePadPage);
                    } else if (validateMouse(event, PAGE_INPUT, browserPage)) {
                        onMouseClickedPageInput(browserPage);
                    }

                }
            }
        });


    }

    private void validateMouseOnDoubleClicked(MouseEvent event) {
        if (validateMouse(event, ICON, notePadIcon)) {
            onMouseDoubleClickedIcon(notePadIcon);
        } else if (validateMouse(event, ICON, browserIcon)) {
            onMouseDoubleClickedIcon(browserIcon);
        }
    }


    private boolean validateMouse(MouseEvent event, String type, Icon icon) {
        Point point = event.getPoint();
        double mouseX = point.getX();
        double mouseY = point.getY();

        if (type == ICON && icon.isMouseOver(mouseX, mouseY)) {
            return true;
        }
        return false;
    }

    private boolean validateMouse(MouseEvent event, String type, Page page) {
        Point point = event.getPoint();
        double mouseX = point.getX();
        double mouseY = point.getY();
        if (type == PAGE_CLOSE_BTN && page.isMouseOverCloseBtn(mouseX, mouseY)) {
            return true;
        } else if (type == PAGE_INPUT && page.isMouseOverInput(mouseX, mouseY)) {

            return true;
        } else if (type == PAGE_NAVBAR && page.isMouseOverNavBar(mouseX, mouseY)) {
            return true;
        }

        return false;
    }

    private void resetInput() {

        notePadPage.setInputContent(notePadPage.DEFAULT_INPUT);
        browserPage.setInputContent(browserPage.DEFAULT_INPUT);

        browserPage.setInputTextColor(new Color(173, 173, 173));
        notePadPage.setInputTextColor(new Color(173, 173, 173));

        notePadPage.setInputHasCursor(false);
        browserPage.setInputHasCursor(false);

        browserIcon.clickHandler(false);
        notePadIcon.clickHandler(false);

    }

    private void validateMouseDraggedPage(MouseEvent event, Page page) {

        if (page.isPressed()) {
            Point point = event.getPoint();
            double mouseX = validateMouseX(point.getX());
            double mouseY = validateMouseY(point.getY());
            page.dragHandler(mouseX, mouseY);
            page.setUpPage();
        }


    }

    private void onMouseClickedPageInput(Page page) {
        if (page instanceof NotePadPage) {


            if (!page.inputHasCursor()) {
                page.setInputContent("");
                page.setInputTextColor(new Color(0, 0, 0));
                page.setInputHasCursor(true);
            }
        }

    }

    private void onMousePressedPage(Page page) {
        page.setPressed(true);

        mlDrag = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                validateMouseDraggedPage(e, page);
            }


        };

        mlPressed = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {

                page.setPressed(false);
                removeMouseMotionListener(mlDrag);
                removeMouseListener(mlPressed);


            }
        };
        addMouseMotionListener(mlDrag);
        addMouseListener(mlPressed);

    }

    private void onMouseClickedIcon(Icon icon) {
        Clickable notePadIcon = this.notePadIcon;
        Clickable browserIcon = this.browserIcon;
        icon.clickHandler(true);
        if (notePadIcon == icon) {
            browserIcon.clickHandler(false);
        } else {
            notePadIcon.clickHandler(false);
        }


    }

    private void onMouseClickedPageCloseBtn(Icon icon) {

        icon.doubleClickHandler(false);
    }

    private void onMouseDoubleClickedIcon(Icon icon) {
        icon.doubleClickHandler(true);


    }


    private double validateMouseX(double mouseX) {
        if (mouseX < 0) {
            return 0;
        } else if (mouseX > theGame.WIDTH - notePadPage.getNavBarWidth()) {
            return theGame.WIDTH - notePadPage.getNavBarWidth();
        } else {
            return mouseX;
        }
    }

    private double validateMouseY(double mouseY) {
        int offsetYLowerBound = 10;
        if (mouseY < NAV_BAR_LENGTH + notePadPage.getNavBarHeight() - offsetYLowerBound) {
            return NAV_BAR_LENGTH + notePadPage.getNavBarHeight() - offsetYLowerBound;
        } else if (mouseY > theGame.LENGTH - notePadPage.getMainPageHeight()) {
            return theGame.LENGTH - notePadPage.getMainPageHeight();
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
                centerY + 55,
                stringWidth,
                new Color(255, 255, 255));
        gameGraphics.setColor(savedColor);


    }

    // Draws the game
    // MODIFIES: gameGraphics
    // EFFECTS:  draws the game onto gameGraphics
    private void displayGameScreen(Graphics gameGraphics) {
        drawRect(gameGraphics, new Color(78, 78, 78), 0, 0, theGame.WIDTH, NAV_BAR_LENGTH);
        drawRect(gameGraphics, new Color(56, 91, 110), 0, 30, theGame.WIDTH, theGame.LENGTH);
        drawIcon(gameGraphics, notePadIcon);
        drawIcon(gameGraphics, browserIcon);
        if (notePadIcon.isDoubleClicked()) {
            drawPage(gameGraphics, notePadIcon.getPage());
        }
        if (browserIcon.isDoubleClicked()) {
            drawPage(gameGraphics, browserIcon.getPage());
        }

    }

    private void drawPage(Graphics gameGraphics, Page page) {
        drawMainPage(gameGraphics, page);
        drawNavBar(gameGraphics, page);
        drawCloseBtn(gameGraphics, page);
        drawInput(gameGraphics, page);
        drawInputContent(gameGraphics, page);
        if (page instanceof BrowserPage) {
            drawMainPageWelcomeMessage(gameGraphics);
            drawWebLinks(gameGraphics);
        }

    }

    private void drawWebLinks(Graphics gameGraphics) {
        HashMap<String, WebLink> webLinks = browserPage.getWebLinks();
        List<String> webNames = theGame.webNames;
        int x = browserPage.getMainPageX() + 20;
        int y = browserPage.getMainPageY() + 120;
        int linkMarginBottom = 25;
        for (int i = 0; i < webNames.size(); i++) {
            if (y <= browserPage.getMainPageY() + browserPage.getMainPageHeight()) {
                String name = webNames.get(i);
                WebLink link = (WebLink) webLinks.get(name);
                drawMessage(name + " - " + link.toString(), gameGraphics, 15, x, y, Color.BLUE);
                y += linkMarginBottom;
            } else {
                break;
            }
        }

    }


    private void drawMainPageWelcomeMessage(Graphics gameGraphics) {
        drawMessage("The Deep Wiki I", gameGraphics, 20,
                browserPage.getMainPageX() + 10,
                browserPage.getMainPageY() + 75, Color.BLACK);
        drawLine(gameGraphics, Color.BLACK,
                browserPage.getMainPageX() + 10,
                browserPage.getMainPageY() + 85,
                browserPage.getMainPageX() + browserPage.getNavBarWidth() - 20,
                browserPage.getMainPageY() + 85);
    }

    private void drawLine(Graphics gameGraphics, Color color, int x1, int y1, int x2, int y2) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(color);
        gameGraphics.drawLine(x1, y1, x2, y2);
        gameGraphics.setColor(savedColor);
    }


    private void drawInputContent(Graphics gameGraphics, Page page) {

        int contentWidth = drawMessage(page.getInputContent(),
                gameGraphics,
                12,
                page.getInputX() + 10,
                page.getInputY() + 20,
                page.getInputTextColor());
        if (page.inputHasCursor() && page instanceof NotePadPage) {

            drawFlashingCursor(gameGraphics,
                    page.getInputX() + 10,
                    page.getInputY() + 35,
                    contentWidth,
                    new Color(0, 0, 0));
        }

    }

    private void drawInput(Graphics gameGraphics, Page page) {
        drawRect(gameGraphics,
                new Color(0, 0, 0),
                page.getInputX(),
                page.getInputY(),
                page.getInputWidth(),
                page.getInputHeight());
        drawRect(gameGraphics,
                new Color(255, 255, 255),
                page.getInputX() + 1,
                page.getInputY() + 1,
                page.getInputWidth() - 2,
                page.getInputHeight() - 2);

    }

    private void drawCloseBtn(Graphics gameGraphics, Page page) {
        drawRect(gameGraphics,
                new Color(163, 34, 39),
                page.getCloseBtnX(),
                page.getCloseBtnY(),
                page.getCloseBtnWidth(),
                page.getCloseBtnHeight());
        drawMessage("X",
                gameGraphics,
                20,
                page.getCloseBtnX() + 8,
                page.getCloseBtnY() + 23,
                new Color(255, 255, 255));
    }

    private void drawNavBar(Graphics gameGraphics, Page page) {
        drawRect(gameGraphics,
                new Color(228, 227, 228),
                page.getNavBarX(),
                page.getNavBarY(),
                page.getNavBarWidth(),
                page.getNavBarHeight());
        String pageName = page.getPageName();
        if (page instanceof BrowserPage) {
            pageName = pageName + " - Anonymous Node Network";
        }
        drawMessage(pageName,
                gameGraphics,
                20,
                page.getNavBarX() + 10,
                page.getNavBarY() + 25,
                new Color(0, 0, 0));
    }

    private void drawMainPage(Graphics gameGraphics, Page page) {
        drawRect(gameGraphics,
                new Color(255, 255, 255),
                page.getMainPageX(),
                page.getMainPageY(),
                page.getMainPageWidth(),
                page.getMainPageHeight());
        if (page == notePadPage) {
            drawNotes(gameGraphics);
        }
    }


    private void drawNotes(Graphics gameGraphics) {
        List<String> lines = theGame.lines;
        int offsetY = 25;
        int lineHeight = 15;
        for (String line : lines) {
            if (!(notePadPage.getMainPageY() + offsetY
                    >= notePadPage.getMainPageY()
                    + notePadPage.getMainPageHeight()
                    - notePadPage.getInputHeight())) {
                drawMessage(line,
                        gameGraphics,
                        12,
                        notePadPage.getMainPageX() + 10,
                        notePadPage.getMainPageY() + offsetY,
                        Color.BLACK);
                offsetY += lineHeight;
            }
        }
    }


    private void drawIconWithBackground(Graphics gameGraphics, Icon icon) {

        gameGraphics.drawImage(icon.getIconImg(),
                icon.getIconX(),
                icon.getIconY(),
                icon.getIconWidth(),
                icon.getIconHeight(), iconBgColor,
                null);
    }

    private void drawIconWithoutBackground(Graphics gameGraphics, Icon icon) {
        gameGraphics.drawImage(icon.getIconImg(),
                icon.getIconX(),
                icon.getIconY(),
                icon.getIconWidth(),
                icon.getIconHeight(),
                null);
    }


    private void drawIcon(Graphics gameGraphics, Icon icon) {
        if (icon.isClicked()) {
            drawIconWithBackground(gameGraphics, icon);
        } else {
            drawIconWithoutBackground(gameGraphics, icon);
        }

        drawMessage(
                icon.getIconName(),
                gameGraphics,
                15,
                icon.getIconX() + 20,
                icon.getIconY() + icon.getIconHeight() + 20,
                new Color(255, 255, 255));
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
