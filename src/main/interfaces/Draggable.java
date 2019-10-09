package interfaces;


public interface Draggable {
    void dragHandler(double mouseX, double mouseY);

    boolean isPressed();

    int getMainPageX();

    int getMainPageY();

    void setUpPage();
}
