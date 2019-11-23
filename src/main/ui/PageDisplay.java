package ui;


import model.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class PageDisplay {
    private List<Page> pages;
    private MessageDisplay messageDisplay;
    private ConcreteHackingGame theGame;
    private Constants constants;
    private RectDisplay rectDisplay;
    private FlashCursorDisplay flashCursorDisplay;
    private List<WebLink> webLinks;
    private BrowserPage browserPage;
    private Image homeImage;
    private Stack<WebLink> backwardStack;
    private Stack<WebLink> forwardStack;


    PageDisplay(List<Page> pages, ConcreteHackingGame theGame) {
        this.pages = pages;
        messageDisplay = new MessageDisplay(theGame);
        this.theGame = theGame;
        constants = new Constants();
        rectDisplay = new RectDisplay();
        flashCursorDisplay = new FlashCursorDisplay();
        browserPage = theGame.getBrowserPage();
        webLinks = new ArrayList<>();
        homeImage = theGame.getImage("./data/img/homeBtn.PNG");
        backwardStack = new Stack<>();
        forwardStack = new Stack<>();

    }


    public boolean validateMouse(MouseEvent event, String type, int index) {
        Point point = event.getPoint();
        double mouseX = point.getX();
        double mouseY = point.getY();
        if (type == constants.PAGE_CLOSE_BTN && pages.get(index).isMouseOverCloseBtn(mouseX, mouseY)) {
            return true;
        } else if (type == constants.PAGE_INPUT && pages.get(index).isMouseOverInput(mouseX, mouseY)) {

            return true;
        } else if (type == constants.PAGE_NAVBAR && pages.get(index).isMouseOverNavBar(mouseX, mouseY)) {
            return true;
        } else if (type == constants.WEB_LINK) {
            return mouseIsOverLink(event, mouseX, mouseY);
        } else if (type == constants.HOME_BTN) {
            return mouseIsOverHomeBtn(event, mouseX, mouseY);
        }

        return false;
    }

    private boolean mouseIsOverHomeBtn(MouseEvent event, double mouseX, double mouseY) {
        if (browserPage.isMouseOverHomeButton(mouseX, mouseY)) {
            browserPage.setMouseOverCloseBtn(true);
            if (event.getClickCount() > 0) {
                browserPage.mainPageState = browserPage.HOME;
                browserPage.setInputContent(constants.DEFAULT_INPUT_BROWSER);
                browserPage.currentWeb = null;
                forwardStack.clear();
                backwardStack.clear();
            }
            return true;
        }

        return false;
    }

    private boolean mouseIsOverLink(MouseEvent e, double mouseX, double mouseY) {
        if (browserPage.mainPageState == constants.WORKING || browserPage.mainPageState == browserPage.HOME) {
            for (WebLink link : this.webLinks) {
                link.hasLine = false;
                if (link.isMouseOverLink(mouseX, mouseY)) {
                    link.hasLine = true;

                    if (e.getClickCount() > 0) {
                        onClick(link);
                        browserPage.setInputContent(link.getWebLink());
                    }
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private void onClick(WebLink link) {
        stackOperationOnLinkClick(link);
        if (link instanceof BrokenWebLink) {
            browserPage.mainPageState = constants.BROKEN;
            browserPage.currentWeb = link;
        } else if (link instanceof LinkWithCode) {
            browserPage.mainPageState = constants.CODE;
            browserPage.currentWeb = link;
        } else {
            browserPage.mainPageState = constants.WORKING;
            browserPage.currentWeb = link;
        }
    }

    private void stackOperationOnLinkClick(WebLink link) {
        forwardStack.clear();
        if (browserPage.currentWeb != null) {
            backwardStack.push(browserPage.currentWeb);
        } else {
            backwardStack.push(null);
        }
    }

    public Page getPage(int index) {
        return pages.get(index);
    }

    public void onMouseClickedPageInput(int index) {

        Page page = pages.get(index);

        if (!page.inputHasCursor()) {
            page.setInputContent("");
            page.setInputTextColor(new Color(0, 0, 0));
            page.setInputHasCursor(true);
        }
    }

    private double validateMouseX(double mouseX) {
        if (mouseX < 0) {
            return 0;
        } else if (mouseX > theGame.WIDTH - pages.get(0).getNavBarWidth()) {
            return theGame.WIDTH - pages.get(0).getNavBarWidth();
        } else {
            return mouseX;
        }
    }

    private double validateMouseY(double mouseY) {
        int offsetYLowerBound = 10;
        if (mouseY < constants.NAV_BAR_LENGTH + pages.get(0).getNavBarHeight() - offsetYLowerBound) {
            return constants.NAV_BAR_LENGTH + pages.get(0).getNavBarHeight() - offsetYLowerBound;
        } else if (mouseY > theGame.LENGTH - pages.get(0).getMainPageHeight()) {
            return theGame.LENGTH - pages.get(0).getMainPageHeight();
        } else {
            return mouseY;
        }
    }

    public void validateMouseDraggedPage(MouseEvent event, int index) {
        Page page = pages.get(index);
        if (page.isPressed()) {
            Point point = event.getPoint();
            double mouseX = validateMouseX(point.getX());
            double mouseY = validateMouseY(point.getY());
            page.dragHandler(mouseX, mouseY);
            page.setUpPage();
        }


    }

    public void drawPage(Graphics gameGraphics, int index) {
        Page page = pages.get(index);
        drawMainPage(gameGraphics, page);
        drawNavBar(gameGraphics, page);
        drawCloseBtn(gameGraphics, page);
        drawInput(gameGraphics, page);
        drawInputContent(gameGraphics, page);
        if (page instanceof BrowserPage) {
            drawBrowserMainPage(gameGraphics, page);
        }

    }

    private void drawBrowserMainPage(Graphics gameGraphics, Page page) {

        if (page.mainPageState == page.HOME) {
            drawHomePage(gameGraphics);
        } else if (page.mainPageState == constants.BROKEN) {
            drawBrokenPage(gameGraphics);
        } else if (page.mainPageState == constants.WORKING) {
            if (((LinkWithLinks) browserPage.currentWeb).getLinks().size() < 25) {
                populateLink(theGame.getBrowserPage().currentWeb);
            }
            drawWorkingPage(gameGraphics, browserPage.currentWeb);
        } else {
            drawPageWithCode(gameGraphics, browserPage.currentWeb);
        }


    }

    private void drawPageWithCode(Graphics gameGraphics, WebLink currentWeb) {
        LinkWithCode link = (LinkWithCode) currentWeb;
        gameGraphics.drawImage(link.getLinkImg(),
                browserPage.getMainPageX() + browserPage.getMainPageWidth() / 8,
                browserPage.getMainPageY() + browserPage.getMainPageHeight() / 4 - 100, 800, 600, null);

        messageDisplay.drawMessage("Secret Key: " + link.getKey(), gameGraphics, 20,
                browserPage.getMainPageX() + browserPage.getMainPageWidth() / 2
                        - messageDisplay.getStringWidth("Secret Key: " + link.getKey(), gameGraphics, 20) / 2,
                browserPage.getMainPageY() + browserPage.getMainPageHeight() / 4 + 550, Color.BLACK);

    }

    private void populateLink(WebLink currentWeb) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < webLinks.size(); i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int j = 0; j < 25; j++) {
            ((LinkWithLinks) currentWeb)
                    .addLinks(webLinks
                            .get(list.get(j)));
        }


    }

    private void drawWorkingPage(Graphics gameGraphics, WebLink webLink) {
        BrowserPage browserPage = theGame.getBrowserPage();
        int x = theGame.getBrowserPage().getMainPageX() + 20;
        int y = theGame.getBrowserPage().getMainPageY() + 120;
        int linkMarginBottom = 25;
        for (WebLink link : ((LinkWithLinks) webLink).getLinks()) {
            if (y <= browserPage.getMainPageY() + browserPage.getMainPageHeight()) {
                String name = link.getWebName();
                int contentWidth = messageDisplay.drawMessage(name + " - " + link.toString(),
                        gameGraphics, 15, x, y, Color.BLUE, link.hasLine);
                setUpLink(link, x, y, linkMarginBottom, contentWidth);
                y += linkMarginBottom;
            }
        }
    }

    private void drawBrokenPage(Graphics gameGraphics) {
        BrowserPage browserPage = (BrowserPage) pages.get(1);
        messageDisplay.drawMessage("Unable to connect to page", gameGraphics, 20,
                browserPage.getMainPageX() + 10,
                browserPage.getMainPageY() + 75, Color.BLACK);
        drawLine(gameGraphics, Color.BLACK,
                browserPage.getMainPageX() + 10,
                browserPage.getMainPageY() + 85,
                browserPage.getMainPageX() + browserPage.getNavBarWidth() - 20,
                browserPage.getMainPageY() + 85);
    }

    private void drawHomePage(Graphics gameGraphics) {
        drawMainPageWelcomeMessage(gameGraphics);
        drawWebLinks(gameGraphics);
    }

    private void drawInput(Graphics gameGraphics, Page page) {
        Color color = new Color(0, 0, 0);
        if (page instanceof BrowserPage) {
            drawBrowserInputItems(gameGraphics);
            color = new Color(196, 198, 192);
        }

        rectDisplay.drawRect(gameGraphics,
                color,
                page.getInputX(),
                page.getInputY(),
                page.getInputWidth(),
                page.getInputHeight());
        rectDisplay.drawRect(gameGraphics,
                new Color(255, 255, 255),
                page.getInputX() + 1,
                page.getInputY() + 1,
                page.getInputWidth() - 2,
                page.getInputHeight() - 2);


    }

    private void drawBrowserInputItems(Graphics gameGraphics) {

        rectDisplay.drawRect(gameGraphics,
                new Color(223, 225, 220),
                browserPage.getMainPageX(),
                browserPage.getMainPageY(),
                browserPage.getNavBarWidth(),
                browserPage.getNavBarHeight() + 10);
        drawLine(gameGraphics, new Color(193, 196, 190),
                browserPage.getMainPageX(), browserPage.getMainPageY() + 10,
                browserPage.getMainPageX() + browserPage.getMainPageWidth(),
                browserPage.getMainPageY() + 10);
        drawLine(gameGraphics, new Color(183, 186, 180),
                browserPage.getMainPageX(),
                browserPage.getMainPageY() + browserPage.getNavBarHeight() + 10,
                browserPage.getMainPageX() + browserPage.getMainPageWidth(),
                browserPage.getMainPageY() + browserPage.getNavBarHeight() + 10);
        drawHomeBtn(gameGraphics);
        drawForwardArrow(gameGraphics);
        drawBackwardArrow(gameGraphics);
    }

    private void drawBackwardArrow(Graphics gameGraphics) {
        Graphics2D g2 = (Graphics2D) gameGraphics;
        Stroke currentStroke = g2.getStroke();
        Color currentColor = g2.getColor();
        g2.setStroke(new BasicStroke(4));
        if (backwardStack.isEmpty()) {
            g2.setColor(new Color(189, 189, 189));
        }
        int y = (browserPage.getHomeBtnY() + browserPage.getHomeBtnHeight() / 2);
        int x1 = browserPage.getHomeBtnX() + browserPage.getHomeBtnWidth() + 30;
        int x2 = browserPage.getHomeBtnX() + browserPage.getHomeBtnWidth() + 50;
        g2.drawLine(x1, y, x2, y);
        int xarrow2 = x1 + (x2 - x1) / 2;
        int upArrow = browserPage.getHomeBtnY() + 5;
        int downArrow = browserPage.getHomeBtnY() + browserPage.getHomeBtnHeight() - 5;
        g2.drawLine(x1 - 3, y, xarrow2, upArrow);
        g2.drawLine(x1 - 3, y, xarrow2, downArrow);
        g2.setStroke(currentStroke);
        g2.setColor(currentColor);
    }

    private void drawForwardArrow(Graphics gameGraphics) {
        Graphics2D g2 = (Graphics2D) gameGraphics;
        Stroke currentStroke = g2.getStroke();
        Color currentColor = g2.getColor();
        g2.setStroke(new BasicStroke(4));
        if (forwardStack.isEmpty()) {
            g2.setColor(new Color(189, 189, 189));
        }
        int y = (browserPage.getHomeBtnY() + browserPage.getHomeBtnHeight() / 2);
        int x1 = browserPage.getHomeBtnX() + browserPage.getHomeBtnWidth() + 80;
        int x2 = browserPage.getHomeBtnX() + browserPage.getHomeBtnWidth() + 100;
        g2.drawLine(x1, y, x2, y);
        int xarrow2 = x1 + (x2 - x1) / 2;
        int upArrow = browserPage.getHomeBtnY() + 5;
        int downArrow = browserPage.getHomeBtnY() + browserPage.getHomeBtnHeight() - 5;
        g2.drawLine(x2 + 3, y, xarrow2, upArrow);
        g2.drawLine(x2 + 3, y, xarrow2, downArrow);
        g2.setStroke(currentStroke);
        g2.setColor(currentColor);

    }

    private void drawHomeBtn(Graphics gameGraphics) {
        if (browserPage.getIsMouseOverHomeButton()) {
            gameGraphics.drawImage(homeImage, browserPage.getHomeBtnX(), browserPage.getHomeBtnY(),
                    browserPage.getHomeBtnWidth(), browserPage.getHomeBtnHeight(), new Color(135, 135, 135), null);
        } else {
            gameGraphics.drawImage(homeImage, browserPage.getHomeBtnX(), browserPage.getHomeBtnY(),
                    browserPage.getHomeBtnWidth(), browserPage.getHomeBtnHeight(), null);
        }
    }

    private void drawCloseBtn(Graphics gameGraphics, Page page) {
        rectDisplay.drawRect(gameGraphics,
                new Color(163, 34, 39),
                page.getCloseBtnX(),
                page.getCloseBtnY(),
                page.getCloseBtnWidth(),
                page.getCloseBtnHeight());
        messageDisplay.drawMessage("X",
                gameGraphics,
                20,
                page.getCloseBtnX() + 8,
                page.getCloseBtnY() + 23,
                new Color(255, 255, 255));
    }


    private void drawNavBar(Graphics gameGraphics, Page page) {
        rectDisplay.drawRect(gameGraphics,
                new Color(228, 227, 228),
                page.getNavBarX(),
                page.getNavBarY(),
                page.getNavBarWidth(),
                page.getNavBarHeight());
        String pageName = page.getPageName();
        if (page instanceof BrowserPage) {
            pageName = pageName + " - Anonymous Node Network";
        }
        messageDisplay.drawMessage(pageName,
                gameGraphics,
                20,
                page.getNavBarX() + 10,
                page.getNavBarY() + 25,
                new Color(0, 0, 0));
    }


    private void drawMainPage(Graphics gameGraphics, Page page) {
        rectDisplay.drawRect(gameGraphics,
                new Color(255, 255, 255),
                page.getMainPageX(),
                page.getMainPageY(),
                page.getMainPageWidth(),
                page.getMainPageHeight());
        if (page instanceof NotePadPage) {
            drawNotes(gameGraphics);
        }
    }


    private void drawWebLinks(Graphics gameGraphics) {
        BrowserPage browserPage = (BrowserPage) pages.get(1);
        HashMap<String, WebLink> webLinks = browserPage.getWebLinks();
        List<String> webNames = theGame.webNames;
        int x = browserPage.getMainPageX() + 20;
        int y = browserPage.getMainPageY() + 120;
        int linkMarginBottom = 25;
        int i = 0;
        while (y <= browserPage.getMainPageY() + browserPage.getMainPageHeight()) {
            String name = webNames.get(i);
            WebLink link = webLinks.get(name);
            int contentWidth = messageDisplay.drawMessage(name + " - " + link.toString(),
                    gameGraphics, 15, x, y, Color.BLUE, link.hasLine);
            setUpLink(link, x, y, linkMarginBottom, contentWidth);
            this.webLinks.add(link);
            y += linkMarginBottom;
            i++;
        }


    }

    private void setUpLink(WebLink link, int x, int y, int linkMarginBottom, int contentWidth) {
        link.setLinkX(x);
        link.setLinkY(y);
        link.setLinkWidth(contentWidth);
        link.setLinkLength(linkMarginBottom);
    }


    private void drawMainPageWelcomeMessage(Graphics gameGraphics) {
        BrowserPage browserPage = (BrowserPage) pages.get(1);
        messageDisplay.drawMessage("The Deep Wiki I", gameGraphics, 20,
                browserPage.getMainPageX() + 10,
                browserPage.getMainPageY() + 75, Color.BLACK);
        drawLine(gameGraphics, Color.BLACK,
                browserPage.getMainPageX() + 10,
                browserPage.getMainPageY() + 85,
                browserPage.getMainPageX() + browserPage.getNavBarWidth() - 20,
                browserPage.getMainPageY() + 85);
    }

    private void drawNotes(Graphics gameGraphics) {
        NotePadPage notePadPage = (NotePadPage) pages.get(0);
        List<String> lines = theGame.lines;
        int offsetY = 25;
        int lineHeight = 15;
        for (String line : lines) {
            if (!(notePadPage.getMainPageY() + offsetY
                    >= notePadPage.getMainPageY()
                    + notePadPage.getMainPageHeight()
                    - notePadPage.getInputHeight())) {
                messageDisplay.drawMessage(line,
                        gameGraphics,
                        12,
                        notePadPage.getMainPageX() + 10,
                        notePadPage.getMainPageY() + offsetY,
                        Color.BLACK);
                offsetY += lineHeight;
            }
        }
    }


    private void drawInputContent(Graphics gameGraphics, Page page) {

        int contentWidth = messageDisplay.drawMessage(page.getInputContent(),
                gameGraphics,
                12,
                page.getInputX() + 10,
                page.getInputY() + 20,
                page.getInputTextColor());
        if (page.inputHasCursor() && page instanceof NotePadPage) {

            flashCursorDisplay.drawFlashingCursor(gameGraphics,
                    page.getInputX() + 10,
                    page.getInputY() + 35,
                    contentWidth,
                    new Color(0, 0, 0));
        }

    }


    private void drawLine(Graphics gameGraphics, Color color, int x1, int y1, int x2, int y2) {
        Color savedColor = gameGraphics.getColor();
        gameGraphics.setColor(color);
        gameGraphics.drawLine(x1, y1, x2, y2);
        gameGraphics.setColor(savedColor);
    }


    public boolean validateArrowBack(MouseEvent event) {
        if (!backwardStack.isEmpty()) {
            Point point = event.getPoint();
            double mouseX = point.getX();
            double mouseY = point.getY();
            int x1Back = browserPage.getHomeBtnX() + browserPage.getHomeBtnWidth() + 30;
            int x2Back = browserPage.getHomeBtnX() + browserPage.getHomeBtnWidth() + 50;
            int upArrow = browserPage.getHomeBtnY() + 5;
            int downArrow = browserPage.getHomeBtnY() + browserPage.getHomeBtnHeight() - 5;
            if (MousePosDetector.detectMousePos(mouseX, mouseY, x1Back - 3, x2Back, upArrow, downArrow)) {
                if (event.getClickCount() > 0) {
                    onBackwardClick();
                }
                return true;
            }
            return false;
        }
        return false;

    }

    private void onBackwardClick() {
        WebLink link = backwardStack.pop();
        if (browserPage.currentWeb != null) {
            forwardStack.push(browserPage.currentWeb);
        }
        if (link != null) {
            browserPage.mainPageState = constants.WORKING;
            browserPage.currentWeb = link;
            browserPage.setInputContent(link.getWebLink());
        } else {
            browserPage.mainPageState = browserPage.HOME;
            browserPage.currentWeb = null;
            browserPage.setInputContent(constants.DEFAULT_INPUT_BROWSER);
        }
    }

    public boolean validateArrowFront(MouseEvent event) {
        if (!forwardStack.isEmpty()) {
            Point point = event.getPoint();
            double mouseX = point.getX();
            double mouseY = point.getY();
            int x1For = browserPage.getHomeBtnX() + browserPage.getHomeBtnWidth() + 80;
            int x2For = browserPage.getHomeBtnX() + browserPage.getHomeBtnWidth() + 100;
            int upArrow = browserPage.getHomeBtnY() + 5;
            int downArrow = browserPage.getHomeBtnY() + browserPage.getHomeBtnHeight() - 5;
            if (MousePosDetector.detectMousePos(mouseX, mouseY, x1For + 3, x2For, upArrow, downArrow)) {
                if (event.getClickCount() > 0) {
                    onForwardClick();
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private void onForwardClick() {
        WebLink link = forwardStack.pop();
        if (browserPage.currentWeb != null) {
            backwardStack.push(browserPage.currentWeb);
        } else {
            backwardStack.push(null);
        }
        if (link != null) {
            if (link instanceof LinkWithCode) {
                browserPage.mainPageState = constants.CODE;

            } else if (link instanceof LinkWithLinks) {
                browserPage.mainPageState = constants.WORKING;
            } else {
                browserPage.mainPageState = constants.BROKEN;
            }
            browserPage.currentWeb = link;
            browserPage.setInputContent(link.getWebLink());
        }
    }
}
