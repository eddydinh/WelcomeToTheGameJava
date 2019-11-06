package model;

import ui.Constants;

public class NotePadPage extends Page {


    public NotePadPage(int mainX, int mainY, int mainWidth, int mainHeight, String pageName) {
        super(mainX, mainY, mainWidth, mainHeight, pageName);
        Constants constants = new Constants();
        setInputContent(constants.DEFAULT_INPUT_NOTEPAD);
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
