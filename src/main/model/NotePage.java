package model;

public class NotePage {

    public static final String DEFAULT_INPUT = "Enter note...";

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


    private boolean inputHasCursor = false;

    public NotePage(int mainX, int mainY, int mainWidth, int mainHeight) {
        setMainPageX(mainX);
        setMainPageY(mainY);
        setMainPageWidth(mainWidth);
        setMainPageHeight(mainHeight);
        setInputContent(DEFAULT_INPUT);
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


    public boolean inputHasCursor() {
        return inputHasCursor;
    }

    public void setInputHasCursor(boolean inputHasCursor) {
        this.inputHasCursor = inputHasCursor;
    }

    public boolean isMouseOverCloseBtn(double mouseX, double mouseY) {
        if (mouseX > getCloseBtnX()
                && mouseX < getCloseBtnX() + getCloseBtnWidth()
                && mouseY > getCloseBtnY()
                && mouseY < getCloseBtnY() + getCloseBtnHeight()) {
            return true;
        }
        return false;


    }

    public boolean isMouseOverInput(double mouseX, double mouseY) {
        if (mouseX > getInputX()
                && mouseX < getInputX() + getInputWidth()
                && mouseY > getInputY()
                && mouseY < getInputY() + getInputHeight()) {
            return true;
        }
        return false;


    }

}
