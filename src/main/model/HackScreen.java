package model;

import java.awt.event.KeyEvent;

public class HackScreen extends GameScreen {


    HackScreen() {
        super();

    }

    public void validateKey(int keyCode, char keyChar) {
        if (keyCode != KeyEvent.VK_ENTER && keyCode != KeyEvent.VK_BACK_SPACE) {

            inputCode += keyChar;
        }
        if (inputCode.length() > 0) {
            if (keyCode == KeyEvent.VK_ENTER) {
                submit();
            } else if (keyCode == KeyEvent.VK_BACK_SPACE) {

                inputCode = inputCode.substring(0, inputCode.length() - 1);

            }
        }
    }

    private void submit() {
        if (inputCode.equals(askedCode)) {
            inputCode = "";
            notifyObservers(ConcreteHackingGame.getInstance().GAME_IS_PLAYED);
        }
    }

}
