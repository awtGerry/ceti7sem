import java.awt.Color;

public class Lines {
    /* metodo para dibujar una linea en la pantalla */
    // y = mx + b
    public static void drawLine(int x1, int y1, int x2, int y2, Color color) {
        float m = (y2 - y1) / (x2 - x1);
        float b = y1 - m * x1;

        for (int x = x1; x <= x2; x++) {
            int y = Math.round(m * x + b);
            Pixel.drawPixel(x, y, color);
        }
    }

    /* metodo para dibujar una linea en la pantalla mejorando el anterior */
    public static void drawLine2(int x1, int y1, int x2, int y2, Color color) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        if (dx == 0) {
            for (int y = y1; y <= y2; y++) {
                Pixel.drawPixel(x1, y, color);
            }
            return;
        }

        if (dy == 0) {
            for (int x = x1; x <= x2; x++) {
                Pixel.drawPixel(x, y1, color);
            }
            return;
        }

        float m = dy / dx;
        float b = y1 - m * x1;

        if (Math.abs(dx) > Math.abs(dy)) {
            for (int x = x1; x <= x2; x++) {
                int y = Math.round(m * x + b);
                Pixel.drawPixel(x, y, color);
            }
        } else {
            for (int y = y1; y <= y2; y++) {
                int x = Math.round((y - b) / m);
                Pixel.drawPixel(x, y, color);
            }
        }
    }


    /* metodo para dibujar una linea en la pantalla usando el algoritmo DDA */
    public static void drawDDA(int x1, int y1, int x2, int y2, Color color) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        float step = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);
        float xInc = dx / step;
        float yInc = dy / step;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= step; i++) {
            Pixel.drawPixel(Math.round(x), Math.round(y), color);
            x += xInc;
            y += yInc;
        }
    }

    public static void drawBresenham(int x1, int y1, int x2, int y2, Color color) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        float p = 2 * dy - dx;
        float xInc = 1;
        float yInc = 1;

        if (dx < 0) {
            xInc = -1;
            dx = -dx;
        }

        if (dy < 0) {
            yInc = -1;
            dy = -dy;
        }

        float x = x1;
        float y = y1;

        Pixel.drawPixel(Math.round(x), Math.round(y), color);

        while (x < x2) {
            if (p >= 0) {
                Pixel.drawPixel(Math.round(x), Math.round(y), color);
                y += yInc;
                p += 2 * dy - 2 * dx;
            } else {
                Pixel.drawPixel(Math.round(x), Math.round(y), color);
                p += 2 * dy;
            }
            x += xInc;
        }
    }

    public static void drawMidPoint(int x1, int y1, int x2, int y2, Color color) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        float d = dy - (dx / 2);
        float x = x1;
        float y = y1;

        Pixel.drawPixel(Math.round(x), Math.round(y), color);

        while (x < x2) {
            x++;
            if (d < 0) {
                d += dy;
            } else {
                d += dy - dx;
                y++;
            }
            Pixel.drawPixel(Math.round(x), Math.round(y), color);
        }
    }

    /* metodo para dibujar una linea en la pantalla usando el algoritmo DDA con máscara */
    public static void drawDDAMask(int x1, int y1, int x2, int y2, int mask, Color color) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        float step = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);
        float xInc = dx / step;
        float yInc = dy / step;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= step; i++) {
            if (checkMask(i, mask)) {
                Pixel.drawPixel(Math.round(x), Math.round(y), color);
            }
            x += xInc;
            y += yInc;
        }
    }
    private static boolean checkMask(int i, int mask) {
        switch (mask) {
            case 1: // Línea recta
                return true;
            case 2: // Línea discontinua
                return i % 10 < 5; // Cambia el valor para ajustar la longitud de los segmentos
            case 3: // Línea a puntos
                return i % 5 == 0; // Cambia el valor para ajustar la distancia entre los puntos
            default:
                return true;
        }
    }

    /* metodo para dibujar una linea en la pantalla usando el algoritmo DDA con grosor */
    public static void drawDDAWidth(int x1, int y1, int x2, int y2, int thickness, Color color) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        float step = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);
        float xInc = dx / step;
        float yInc = dy / step;

        int x = x1;
        int y = y1;

        for (int i = 0; i <= step; i++) {
            for (int j = -thickness/2; j <= thickness/2; j++) {
                for (int k = -thickness/2; k <= thickness/2; k++) {
                    Pixel.drawPixel(x + j, y + k, color);
                }
            }
            x += xInc;
            y += yInc;
        }
    }
}
