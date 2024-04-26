import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Pixel extends JComponent {
    private final BufferedImage bufferedPixel;
    private BufferedImage bufferedImage;

    public Pixel(int width, int height) {
        bufferedPixel = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /* metodo para dibujar un pixel en la pantalla */
    public void drawPixel(int x, int y, Color color) {
        // codigo para dibujar un pixel en la pantalla
        bufferedPixel.setRGB(0, 0, color.getRGB());
        if (bufferedImage != null) {
            bufferedImage.getGraphics().drawImage(bufferedPixel, x, y, this);
        } else {
            // bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            // bufferedImage.getGraphics().drawImage(bufferedPixel, x, y, this);
            getGraphics().drawImage(bufferedPixel, x, y, this);
        }
    }

    /* metodo para dibujar una linea en la pantalla */
    // y = mx + b
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        // codigo para dibujar una linea en la pantalla
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        float xinc = dx / (float) steps;
        float yinc = dy / (float) steps;
        float x = x1;
        float y = y1;
        for (int i = 0; i <= steps; i++) {
            drawPixel(Math.round(x), Math.round(y), color);
            x += xinc;
            y += yinc;
        }
    }
}
