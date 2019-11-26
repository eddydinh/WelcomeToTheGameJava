package model;

import ui.Constants;

import java.util.HashMap;

public class BrowserPage extends Page {


    private HashMap<String, WebLink> webLinks = new HashMap<>();
    private WebLink currentWeb = null;
    private int homeBtnX;
    private int homeBtnY;
    private int homeBtnWidth;
    private int homeBtnHeight;


    public BrowserPage(int mainX, int mainY, int mainWidth, int mainHeight, String pageName) {

        super(mainX, mainY, mainWidth, mainHeight, pageName);
        Constants constants = new Constants();
        setInputContent(constants.DEFAULT_INPUT_BROWSER);

    }


    //MODIFIES: webLinks
    //EFFECTS: add a link to the list of links on browser page
    public void addWebLink(WebLink link) {
        if (!webLinks.containsValue(link)) {
            webLinks.put(link.getWebName(), link);
        }
    }

    //EFFECTS: check if mouse over home button
    public boolean isMouseOverHomeButton(double mouseX, double mouseY) {
        return MousePosDetector.detectMousePos(mouseX, mouseY,
                getHomeBtnX(),
                getHomeBtnX() + getHomeBtnWidth(),
                getHomeBtnY(),
                getHomeBtnY() + getHomeBtnHeight());
    }

    public HashMap<String, WebLink> getWebLinks() {
        return webLinks;
    }

    //MODIFIES: this
    //EFFECTS: set up coordinations of browser page elements
    @Override
    public void setUpPage() {
        setNavBarX(getMainPageX());
        setNavBarY(getMainPageY() - 30);
        setNavBarWidth(getMainPageWidth());
        setNavBarHeight(40);

        setInputX(getMainPageX() + 200);
        setInputY(getMainPageY() + getNavBarHeight() - 25);
        setInputWidth(getMainPageWidth() - 250);
        setInputHeight(30);

        setCloseBtnHeight(30);
        setCloseBtnWidth(30);
        setCloseBtnX(getMainPageX() + getMainPageWidth() - 10 - getCloseBtnWidth());
        setCloseBtnY(getMainPageY() - 27);

        setHomeBtnX(getMainPageX() + 10);
        setHomeBtnY(getMainPageY() + getNavBarHeight() - 25);
        setHomeBtnWidth(30);
        setHomeBtnHeight(30);

    }

    public WebLink getCurrentWeb() {
        return currentWeb;
    }

    public void setCurrentWeb(WebLink currentWeb) {
        this.currentWeb = currentWeb;
    }


    public int getHomeBtnX() {
        return homeBtnX;
    }

    public void setHomeBtnX(int homeBtnX) {
        this.homeBtnX = homeBtnX;
    }

    public int getHomeBtnY() {
        return homeBtnY;
    }

    public void setHomeBtnY(int homeBtnY) {
        this.homeBtnY = homeBtnY;
    }

    public int getHomeBtnWidth() {
        return homeBtnWidth;
    }

    public void setHomeBtnWidth(int homeBtnWidth) {
        this.homeBtnWidth = homeBtnWidth;
    }

    public int getHomeBtnHeight() {
        return homeBtnHeight;
    }

    public void setHomeBtnHeight(int homeBtnHeight) {
        this.homeBtnHeight = homeBtnHeight;
    }

}
