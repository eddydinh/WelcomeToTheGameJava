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


    PageDisplay(List<Page> pages, ConcreteHackingGame theGame) {
        this.pages = pages;
        messageDisplay = new MessageDisplay(theGame);
        this.theGame = theGame;
        constants = new Constants();
        rectDisplay = new RectDisplay();
        flashCursorDisplay = new FlashCursorDisplay();
        BrowserPage browserPage = theGame.getBrowserPage();
        webLinks = new ArrayList<>();

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
            return mouseIsOver(event, mouseX, mouseY);
        }

        return false;
    }

    private boolean mouseIsOver(MouseEvent e, double mouseX, double mouseY) {
        for (WebLink link : this.webLinks) {
            link.hasLine = false;
            if (link.isMouseOverLink(mouseX, mouseY)) {
                link.hasLine = true;
                if (e.getClickCount() > 0) {
                    if (link instanceof BrokenWebLink) {
                        theGame.getBrowserPage().mainPageState = constants.BROKEN;
                    } else {
                        theGame.getBrowserPage().mainPageState = constants.WORKING;
                        theGame.getBrowserPage().currentWeb = (LinkWithLinks) link;
                    }
                }
                return true;
            }
        }
        return false;
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
            if (theGame.getBrowserPage().currentWeb.getLinks().size() < 25) {
                populateLink(theGame.getBrowserPage().currentWeb);
            }
            drawWorkingPage(gameGraphics, theGame.getBrowserPage().currentWeb);
        }


    }

    private void populateLink(WebLink currentWeb) {
        ((LinkWithLinks) currentWeb)
                .addLinks(webLinks
                        .get(new Random().nextInt(webLinks.size() - 1)));

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
        rectDisplay.drawRect(gameGraphics,
                new Color(0, 0, 0),
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
            WebLink link = (WebLink) webLinks.get(name);
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


}
