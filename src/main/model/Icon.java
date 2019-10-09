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

    public Icon(int iconX, int iconY, int iconWidth, int iconHeight, String iconName, Image iconImg) {
        setIconImg(iconImg);
        setIconX(iconX);
        setIconY(iconY);
        setIconHeight(iconHeight);
        setIconWidth(iconWidth);
        setIconName(iconName);

    }

    @Override
    public void clickHandler(boolean isClicked) {
        setClicked(isClicked);
    }

    @Override
    public void doubleClickHandler(boolean isDoubleClicked) {
        setDoubleClicked(isDoubleClicked);
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        if (mouseX > getIconX()
                && mouseX < getIconX() + getIconWidth()
                && mouseY > getIconY()
                && mouseY < getIconY() + getIconHeight()) {
            return true;
        }
        return false;


    }


}
