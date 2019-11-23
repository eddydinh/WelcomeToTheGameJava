package model;

import interfaces.Draggable;

import java.awt.*;

public abstract class Page implements Draggable {


    //Nav bar attributes
    private int navBarX;
    private int navBarY;
    private int navBarWidth;
    private int navBarHeight;

    //Close button attributes
    private int closeBtnX;
    private int closeBtnY;
    private int closeBtnWidth;
    private int closeBtnHeight;

    //Main page attributes
    private int mainPageX;
    private int mainPageY;
    private int mainPageWidth;
    private int mainPageHeight;

    //Input box attributes
    private int inputX;
    private int inputY;
    private int inputWidth;
    private int inputHeight;

    //Content of input
    private String inputContent;

    public String mainPageState;
    public static String HOME = "HOME";

    private boolean isMouseOverCloseBtn = false;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    private String pageName;

    public Color getInputTextColor() {
        return inputTextColor;
    }

    public void setInputTextColor(Color inputTextColor) {
        this.inputTextColor = inputTextColor;
    }

    private Color inputTextColor;

    private boolean inputHasCursor = false;
    private boolean isPressed = false;

    public static final Color DEFAULT_INPUT_COLOR = new Color(173, 173, 173);

    protected Icon icon;

    private MousePosDetector mousePosDetector;

    public Page(int mainX, int mainY, int mainWidth, int mainHeight, String pageName) {
        setMainPageX(mainX);
        setMainPageY(mainY);
        setMainPageWidth(mainWidth);
        setPageName(pageName);
        setMainPageHeight(mainHeight);
        setInputTextColor(DEFAULT_INPUT_COLOR);
        setUpPage();
        mousePosDetector = new MousePosDetector();
        mainPageState = HOME;
    }

    public int getNavBarX() {
        return navBarX;
    }

    public void setNavBarX(int navBarX) {
        this.navBarX = navBarX;
    }

    public int getNavBarY() {
        return navBarY;
    }

    public void setNavBarY(int navBarY) {
        this.navBarY = navBarY;
    }

    public int getNavBarWidth() {
        return navBarWidth;
    }

    public void setNavBarWidth(int navBarWidth) {
        this.navBarWidth = navBarWidth;
    }

    public int getNavBarHeight() {
        return navBarHeight;
    }

    public void setNavBarHeight(int navBarHeight) {
        this.navBarHeight = navBarHeight;
    }

    public int getCloseBtnX() {
        return closeBtnX;
    }

    public void setCloseBtnX(int closeBtnX) {
        this.closeBtnX = closeBtnX;
    }

    public int getCloseBtnY() {
        return closeBtnY;
    }

    public void setCloseBtnY(int closeBtnY) {
        this.closeBtnY = closeBtnY;
    }

    public int getCloseBtnWidth() {
        return closeBtnWidth;
    }

    public void setCloseBtnWidth(int closeBtnWidth) {
        this.closeBtnWidth = closeBtnWidth;
    }

    public int getCloseBtnHeight() {
        return closeBtnHeight;
    }

    public void setCloseBtnHeight(int closeBtnHeight) {
        this.closeBtnHeight = closeBtnHeight;
    }

    public int getMainPageX() {
        return mainPageX;
    }

    public void setMainPageX(int mainPageX) {
        this.mainPageX = mainPageX;
    }

    public int getMainPageY() {
        return mainPageY;
    }

    public void setMainPageY(int mainPageY) {
        this.mainPageY = mainPageY;
    }

    public int getMainPageWidth() {
        return mainPageWidth;
    }

    public void setMainPageWidth(int mainPageWidth) {
        this.mainPageWidth = mainPageWidth;
    }

    public int getMainPageHeight() {
        return mainPageHeight;
    }

    public void setMainPageHeight(int mainPageHeight) {
        this.mainPageHeight = mainPageHeight;
    }

    public int getInputX() {
        return inputX;
    }

    public void setInputX(int inputX) {
        this.inputX = inputX;
    }

    public int getInputY() {
        return inputY;
    }

    public void setInputY(int inputY) {
        this.inputY = inputY;
    }

    public int getInputWidth() {
        return inputWidth;
    }

    public void setInputWidth(int inputWidth) {
        this.inputWidth = inputWidth;
    }

    public int getInputHeight() {
        return inputHeight;
    }

    public void setInputHeight(int inputHeight) {
        this.inputHeight = inputHeight;
    }

    public String getInputContent() {
        return inputContent;
    }

    public void setInputContent(String inputContent) {
        this.inputContent = inputContent;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }


    public boolean inputHasCursor() {
        return inputHasCursor;
    }

    public void setInputHasCursor(boolean inputHasCursor) {
        this.inputHasCursor = inputHasCursor;
    }


    public boolean isMouseOverCloseBtn(double mouseX, double mouseY) {

        return mousePosDetector.detectMousePos(mouseX, mouseY,
                getCloseBtnX(),
                getCloseBtnX() + getCloseBtnWidth(),
                getCloseBtnY(),
                getCloseBtnY() + getCloseBtnHeight());


    }

    public boolean isMouseOverInput(double mouseX, double mouseY) {

        return mousePosDetector.detectMousePos(mouseX, mouseY,
                getInputX(),
                getInputX() + getInputWidth(),
                getInputY(),
                getInputY() + getInputHeight());


    }

    public boolean isMouseOverNavBar(double mouseX, double mouseY) {


        return mousePosDetector.detectMousePos(mouseX, mouseY,
                getNavBarX(),
                getNavBarX() + getNavBarWidth() - getCloseBtnWidth(),
                getNavBarY(),
                getNavBarY() + getNavBarHeight());


    }

    @Override
    public void dragHandler(double iconX, double iconY) {
        setMainPageX((int) iconX);
        setMainPageY((int) iconY);
    }

    public abstract void setUpPage();

    public void setMouseOverCloseBtn(boolean mouseOverCloseBtn) {
        isMouseOverCloseBtn = mouseOverCloseBtn;
    }

    public void setIcon(Icon theIcon) {
        if (icon != theIcon) {
            icon = theIcon;
            icon.setPage(this);
        }
    }

    public boolean getIsMouseOverHomeButton() {
        return isMouseOverCloseBtn;
    }
}
