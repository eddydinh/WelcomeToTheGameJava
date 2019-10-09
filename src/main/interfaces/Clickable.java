package interfaces;

public interface Clickable {
    void clickHandler(boolean isClicked);

    void doubleClickHandler(boolean isDoubleClicked);

    boolean isClicked();

    boolean isDoubleClicked();
}
