package ui;

import interfaces.Clickable;

import model.ConcreteHackingGame;
import model.Icon;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class IconDisplay {
    private List<Icon> icons;
    private MessageDisplay messageDisplay;
    private Color iconBgColor = new Color(204, 232, 255, 25);

    IconDisplay(List<Icon> icons, ConcreteHackingGame theGame) {
        this.icons = icons;
        messageDisplay = new MessageDisplay(theGame);
    }

    //EFFECT: validate mouse on icon
    public boolean validateMouse(MouseEvent event, int index) {
        Point point = event.getPoint();
        double mouseX = point.getX();
        double mouseY = point.getY();

        if (icons.get(index).isMouseOver(mouseX, mouseY)) {
            return true;
        }
        return false;
    }

    //EFFECT: return icon from icons given index
    public Icon getIcon(int index) {
        return icons.get(index);
    }

    //MODIFIES: Clickable
    //EFFECT: handle click event on icon
    public void onMouseClickedIcon(int index) {
        Clickable icon = icons.get(index);
        icon.clickHandler(true);
        for (Icon i : icons) {
            if (i != icon) {
                i.clickHandler(false);
            }
        }


    }

    //MODIFIES: Icon, Page
    //EFFECT: handle click event on page's close btn

    public void onMouseClickedPageCloseBtn(int index) {
        icons.get(index).doubleClickHandler(false);

    }

    //MODIFIES: Clickable, Page
    //EFFECT: handle double click event on icon
    public void onMouseDoubleClickedIcon(int idex) {
        icons.get(idex).doubleClickHandler(true);


    }


    //MODIFIES: Graphics
    //EFFECT: draw icon

    public void drawIcon(Graphics gameGraphics) {
        for (Icon i : icons) {
            if (i.isClicked()) {
                drawIconWithBackground(gameGraphics, i);
            } else {
                drawIconWithoutBackground(gameGraphics, i);
            }

            messageDisplay.drawMessage(
                    i.getIconName(),
                    gameGraphics,
                    15,
                    i.getIconX() + 20,
                    i.getIconY() + i.getIconHeight() + 20,
                    new Color(255, 255, 255));
        }
    }


    private void drawIconWithBackground(Graphics gameGraphics, Icon icon) {

        gameGraphics.drawImage(icon.getIconImg(),
                icon.getIconX(),
                icon.getIconY(),
                icon.getIconWidth(),
                icon.getIconHeight(), iconBgColor,
                null);
    }



    private void drawIconWithoutBackground(Graphics gameGraphics, Icon icon) {
        gameGraphics.drawImage(icon.getIconImg(),
                icon.getIconX(),
                icon.getIconY(),
                icon.getIconWidth(),
                icon.getIconHeight(),
                null);
    }

}
