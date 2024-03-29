package model;

public class MousePosDetector {
    //EFFECT: detect whether mouse is within given bound
    public static boolean detectMousePos(
            double mouseX, double mouseY, int x1, int x2, int y1, int y2) {
        if (mouseX > x1
                && mouseX < x2
                && mouseY > y1
                && mouseY < y2) {
            return true;
        }
        return false;
    }
}
