package model;

import interfaces.Clickable;
import interfaces.Draggable;

import javax.swing.*;
import java.awt.Image;

public class NotePad implements Clickable, Draggable {


    private Image iconImg;
    private int iconX;
    private int iconY;
    private int iconHeight;
    private int iconWidth;

    private boolean isPressed = false;
    private boolean isClicked = false;
    private boolean isDoubleClicked = false;

    public NotePad(int iconX, int iconY, int iconWidth, int iconHeight, Image iconImg) {
        setIconImg(iconImg);
        setIconX(iconX);
        setIconY(iconY);
        setIconHeight(iconHeight);
        setIconWidth(iconWidth);

    }

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

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
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

    public boolean isMouseOver(double mouseX, double mouseY) {
        if (mouseX > getIconX()
                && mouseX < getIconX() + getIconWidth()
                && mouseY > getIconY()
                && mouseY < getIconY() + getIconHeight()) {
            return true;
        }
        return false;


    }

    @Override
    public void dragHandler(double iconX, double iconY) {
        setIconX((int) iconX);
        setIconY((int) iconY);
    }

    @Override
    public void clickHandler(boolean isClicked) {
        setClicked(isClicked);
    }

    @Override
    public void doubleClickHandler(boolean isDoubleClicked) {
        setDoubleClicked(isDoubleClicked);
    }

}
