import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Figures {
    public static void drawRectangle(int x1, int y1, int x2, int y2, Color color) {
        Lines.drawDDA(x1, y1, x2, y1, color);
        Lines.drawDDA(x1, y1, x1, y2, color);
        Lines.drawDDA(x1, y2, x2, y2, color);
        Lines.drawDDA(x2, y1, x2, y2, color);
    }

    public static void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
        Lines.drawDDA(x1, y1, x2, y2, color);
        Lines.drawDDA(x2, y2, x3, y3, color);
        Lines.drawDDA(x3, y3, x1, y1, color);
    }

    public static void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
        int[] xs = {x1, x2, x3};
        int[] ys = {y1, y2, y3};
        
        // Find the minimum and maximum y-coordinates
        int minY = Arrays.stream(ys).min().getAsInt();
        int maxY = Arrays.stream(ys).max().getAsInt();
        
        // For each scanline
        for (int y = minY; y <= maxY; y++) {
            List<Integer> intersections = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                int next = (i + 1) % 3;
                int y0 = ys[i];
                y1 = ys[next];
                if ((y0 <= y && y < y1) || (y1 <= y && y < y0)) {
                    // Find the intersection point of the scanline with the edge
                    int xIntersect = (int) (xs[i] + (xs[next] - xs[i]) * (y - y0) / (double) (y1 - y0));
                    intersections.add(xIntersect);
                }
            }
            // Sort the intersection points
            Collections.sort(intersections);
            
            // Draw horizontal lines between pairs of intersection points
            for (int i = 0; i < intersections.size(); i += 2) {
                int xStart = intersections.get(i);
                int xEnd = Math.min(intersections.get(i + 1), 799); // Limit to the screen width
                for (int x = xStart; x <= xEnd; x++) {
                    Pixel.drawPixel(x, y, color);
                }
            }
        }
    }
}

