package model;

public class BrowserPage extends Page {
    public static final String DEFAULT_INPUT = "http://dd7cb6400452a07c5d17619c84c.ann";

    public BrowserPage(int mainX, int mainY, int mainWidth, int mainHeight, String pageName) {
        super(mainX, mainY, mainWidth, mainHeight, pageName);
        setInputContent(DEFAULT_INPUT);

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
