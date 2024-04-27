import java.awt.Color;

public class Figures {
    public static void drawRectangle(int x1, int y1, int x2, int y2, Color color) {
        Lines.drawDDA(x1, y1, x2, y1, color);
        Lines.drawDDA(x1, y1, x1, y2, color);
        Lines.drawDDA(x1, y2, x2, y2, color);
        Lines.drawDDA(x2, y1, x2, y2, color);
    }
}
