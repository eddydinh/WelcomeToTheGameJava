package model;

public class NotePadPage extends Page {

    public static final String DEFAULT_INPUT = "Enter note...";

    public NotePadPage(int mainX, int mainY, int mainWidth, int mainHeight, String pageName) {
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
        setInputY(getMainPageY() + getMainPageHeight() - 42);
        setInputWidth(getMainPageWidth() - 2);
        setInputHeight(40);

        setCloseBtnHeight(30);
        setCloseBtnWidth(30);
        setCloseBtnX(getMainPageX() + getMainPageWidth() - 10 - getCloseBtnWidth());
        setCloseBtnY(getMainPageY() - 27);
    }


}
