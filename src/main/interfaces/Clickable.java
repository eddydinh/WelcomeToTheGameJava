package interfaces;

public interface Clickable {
    public void clickHandler(boolean isClicked);

    public void doubleClickHandler(boolean isDoubleClicked);

    boolean isClicked();

    boolean isDoubleClicked();
}
