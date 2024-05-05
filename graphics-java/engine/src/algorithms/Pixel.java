import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Pixel extends JComponent {
    public static BufferedImage bufferedImage;
    public static BufferedImage bufferedPixel;
    public static Pixel pixel;

    public Pixel(int width, int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedPixel = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixel = this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bufferedPixel, 0, 0, this);
        bufferedPixel.getGraphics().drawImage(bufferedImage, 0, 0, this);
    }

    /* metodo para dibujar un pixel en la pantalla */
    public static void drawPixel(int x, int y, Color color) {
        // si el pixel esta fuera de la pantalla, no hacer nada
        if (x < 0 || x >= bufferedImage.getWidth() || y < 0 || y >= bufferedImage.getHeight()) {
            return;
        }
        bufferedImage.setRGB(x, y, color.getRGB());
        pixel.repaint();
    }

    /* metodo para limpiar la pantalla */
    public static void clear() {
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                bufferedImage.setRGB(i, j, Color.BLACK.getRGB());
            }
        }
        pixel.repaint();
    }

    /* metodo para actualizar el pixel */
    public static void refresh() {
        pixel.repaint();
    }

    /* metodo para obtener el pixel */
    public static Color getPixel(int x, int y) {
        return new Color(bufferedImage.getRGB(x, y));
    }
}
