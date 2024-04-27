import java.awt.Color;

public class Circles {
    /* metodo para dibujar un circulo en la pantalla */
    // formula: R^2 = (x - xc)^2 + (y - yc)^2
    public static void drawCircle(int x1, int y1, int xc, int yc, Color color) {
        float r = (float) Math.sqrt(Math.pow(x1 - xc, 2) + Math.pow(y1 - yc, 2));
        for (int x = (int) (xc - r); x <= (int) (xc + r); x++) {
            int topY = (int) (yc + Math.sqrt(Math.pow(r, 2) - Math.pow(x - xc, 2)));
            int bottomY = (int) (yc - Math.sqrt(Math.pow(r, 2) - Math.pow(x - xc, 2)));
            Pixel.drawPixel(x, topY, color);
            Pixel.drawPixel(x, bottomY, color);
        }
    }

    /* metodo para dibujar un circulo en la pantalla usando coordenadas polares */
    // x = xc + r sin(t)
    // y = yc + r cos(t)
    public static void drawCirclePolar(int x, int y, int xc, int yc, Color color) {
        float theta = 0;
        float r = (float) Math.sqrt(Math.pow(x - xc, 2) + Math.pow(y - yc, 2));
        for (int i = 0; i < 360; i++) {
            float x1 = (float) (xc + r * Math.sin(theta));
            float y1 = (float) (yc + r * Math.cos(theta));
            Pixel.drawPixel((int) x1, (int) y1, color);
            theta += 2.0 * Math.PI / 360.0;
        }
    }

    /* metodo para dibujar un circulo en la pantalla usando puntos medios */
    public static void drawCircleMidPoint(int xc, int yc, int r, Color color) {
        int x = 0;
        int y = r;
        int p = 1 - r;
        while (x < y) {
            Pixel.drawPixel(xc + x, yc + y, color);
            Pixel.drawPixel(xc - x, yc + y, color);
            Pixel.drawPixel(xc + x, yc - y, color);
            Pixel.drawPixel(xc - x, yc - y, color);
            Pixel.drawPixel(xc + y, yc + x, color);
            Pixel.drawPixel(xc - y, yc + x, color);
            Pixel.drawPixel(xc + y, yc - x, color);
            Pixel.drawPixel(xc - y, yc - x, color);
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;
        }
    }

    /* metodo para dibujar un circulo en la pantalla usando el algoritmo de Bresenham */
    public static void drawCircleBresenham(int xc, int yc, int r, Color color) {
        int x = 0;
        int y = r;
        int p = 1 * r;
        while (x < y) {
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;
            Pixel.drawPixel(xc + x, yc + y, color);
            Pixel.drawPixel(xc - x, yc + y, color);
            Pixel.drawPixel(xc + x, yc - y, color);
            Pixel.drawPixel(xc - x, yc - y, color);
            Pixel.drawPixel(xc + y, yc + x, color);
            Pixel.drawPixel(xc - y, yc + x, color);
            Pixel.drawPixel(xc + y, yc - x, color);
            Pixel.drawPixel(xc - y, yc - x, color);
        }
    }

    /* metodo para dibujar un elipse en la pantalla */
    public static void drawEllipse(int xc, int yc, int rx, int ry, Color color) {
        int x = 0;
        int y = ry;

        // Region
        int p1 = (int) ((float) Math.pow(ry, 2) - (float) Math.pow(rx, 2) * ry + 0.25 + (float) Math.pow(rx, 2));
        int dx = 2 * (int) Math.pow(ry, 2) * x;
        int dy = 2 * (int) Math.pow(rx, 2) * y;

        while (dx < dy) {
            Pixel.drawPixel(xc + x, yc + y, color);
            Pixel.drawPixel(xc - x, yc + y, color);
            Pixel.drawPixel(xc + x, yc - y, color);
            Pixel.drawPixel(xc - x, yc - y, color);

            if (p1 < 0) {
                x++;
                dx += 2 * (int) Math.pow(ry, 2);
                p1 += dx + (int) Math.pow(ry, 2);
            } else {
                x++;
                y--;
                dx += 2 * (int) Math.pow(ry, 2);
                dy -= 2 * (int) Math.pow(rx, 2);
                p1 += dx - dy + (int) Math.pow(ry, 2);
            }
        }

        // Region 2
        int p2 = (int) ((float) Math.pow(ry, 2) * Math.pow(x + 0.5, 2) + (float) Math.pow(rx, 2) * Math.pow(y - 1.0, 2) - (float) Math.pow(rx, 2) * Math.pow(ry, 2));

        while (y >= 0) {
            Pixel.drawPixel(xc + x, yc + y, color);
            Pixel.drawPixel(xc - x, yc + y, color);
            Pixel.drawPixel(xc + x, yc - y, color);
            Pixel.drawPixel(xc - x, yc - y, color);

            if (p2 > 0) {
                y--;
                dy -= 2 * (int) Math.pow(rx, 2);
                p2 += (int) Math.pow(rx, 2) - dy;
            } else {
                y--;
                x++;
                dx += 2 * (int) Math.pow(ry, 2);
                dy -= 2 * (int) Math.pow(rx, 2);
                p2 += dx - dy + (int) Math.pow(rx, 2);
            }
        }
    }
}
