import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class Fill {
    /* metodo para rellenar una figura usando scanline */
    // relleno a partir de una linea (buena para rectangulos: x1, y1, x2, y2)
    public static void scanline(int x1, int y1, int x2, int y2, Color color) {
        int y = y1;
        while (y <= y2) {
            int x = x1;
            while (x <= x2) {
                Pixel.drawPixel(x, y, color);
                x++;
            }
            y++;
        }
    }

    /* metodo para rellenar una figura usando inundacion */
    // relleno a partir de un punto central
    /*
       x es la coordenada x del punto central
       y es la coordenada y del punto central
       target es el color del pixel central (color del fondo en la mayoria de los casos)
       color es el color con el que se va a rellenar
    */
    public static void floodFill(int x, int y, Color target, Color color) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            x = p.x;
            y = p.y;

            if (Pixel.getPixel(x, y).equals(target)) {
                Pixel.drawPixel(x, y, color);
                if (Pixel.getPixel(x + 1, y).equals(target)) {
                    queue.add(new Point(x + 1, y));
                }
                if (Pixel.getPixel(x - 1, y).equals(target)) {
                    queue.add(new Point(x - 1, y));
                }
                if (Pixel.getPixel(x, y + 1).equals(target)) {
                    queue.add(new Point(x, y + 1));
                }
                if (Pixel.getPixel(x, y - 1).equals(target)) {
                    queue.add(new Point(x, y - 1));
                }
            }
        }
    }
}
