package model;

import ui.Constants;

import java.util.HashMap;

public class BrowserPage extends Page {



    private HashMap<String, WebLink> webLinks = new HashMap<>();

    public BrowserPage(int mainX, int mainY, int mainWidth, int mainHeight, String pageName) {

        super(mainX, mainY, mainWidth, mainHeight, pageName);
        Constants constants = new Constants();
        setInputContent(constants.DEFAULT_INPUT_BROWSER);

    }


    public void addWebLink(WebLink link) {
        if (!webLinks.containsValue(link)) {
            webLinks.put(link.getWebName(), link);
        }
    }

    public HashMap<String, WebLink> getWebLinks() {
        return webLinks;
    }


    @Override
    public void setUpPage() {
        setNavBarX(getMainPageX());
        setNavBarY(getMainPageY() - 30);
        setNavBarWidth(getMainPageWidth());
        setNavBarHeight(40);

        setInputX(getMainPageX() + 1);
        setInputY(getMainPageY() + getNavBarHeight() - 30);
        setInputWidth(getMainPageWidth() - 2);
        setInputHeight(40);

        setCloseBtnHeight(30);
        setCloseBtnWidth(30);
        setCloseBtnX(getMainPageX() + getMainPageWidth() - 10 - getCloseBtnWidth());
        setCloseBtnY(getMainPageY() - 27);

    }
}
