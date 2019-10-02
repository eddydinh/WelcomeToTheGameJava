package interfaces;

import java.awt.event.MouseListener;

public interface Draggable {
    public void dragHandler(double mouseX, double mouseY);

    public boolean isPressed();

    int getIconX();

    int getIconY();
}
