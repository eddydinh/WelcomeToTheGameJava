package model;

import interfaces.Clickable;

import java.awt.*;

public abstract class Icon implements Clickable {
    private Image iconImg;
    private int iconX;
    private int iconY;
    private int iconHeight;
    private int iconWidth;


    private String iconName;

    private boolean isClicked = false;
    private boolean isDoubleClicked = false;
    protected Page page;

    public Image getIconImg() {
        return iconImg;
    }

    public void setIconImg(Image iconImg) {
        this.iconImg = iconImg;
    }

    public int getIconX() {
        return iconX;
    }

    public void setIconX(int iconX) {
        this.iconX = iconX;
    }

    public int getIconY() {
        return iconY;
    }

    public void setIconY(int iconY) {
        this.iconY = iconY;
    }

    public int getIconHeight() {
        return iconHeight;
    }

    public void setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
    }

    public int getIconWidth() {
        return iconWidth;
    }

    public void setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean isDoubleClicked() {
        return isDoubleClicked;
    }

    public void setDoubleClicked(boolean doubleClicked) {
        isDoubleClicked = doubleClicked;
    }


    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    private MousePosDetector mousePosDetector;

    public Icon(int iconX, int iconY, int iconWidth, int iconHeight, String iconName, Image iconImg) {
        setIconImg(iconImg);
        setIconX(iconX);
        setIconY(iconY);
        setIconHeight(iconHeight);
        setIconWidth(iconWidth);
        setIconName(iconName);
        mousePosDetector = new MousePosDetector();

    }

    //MODIFIES: this
    //EFFECT: set background icon to blue on click

    @Override
    public void clickHandler(boolean isClicked) {
        setClicked(isClicked);
    }

    //MODIFIES: this
    //EFFECT: open associated page on double click

    @Override
    public void doubleClickHandler(boolean isDoubleClicked) {
        setDoubleClicked(isDoubleClicked);
    }



    //EFFECT:check if mouse is over icon
    public boolean isMouseOver(double mouseX, double mouseY) {

        return mousePosDetector.detectMousePos(mouseX,
                mouseY,
                getIconX(),
                getIconX() + getIconWidth(),
                getIconY(),
                getIconY() + getIconHeight());

    }

    //MODIFIES: this, Page
    //EFFECT: Pair page and icon
    void setPage(Page thePage) {
        if (page != thePage) {
            page = thePage;
            page.setIcon(this);
        }
    }


    public Page getPage() {
        return page;
    }
}
