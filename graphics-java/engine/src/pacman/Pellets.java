import java.awt.Color;
import java.util.ArrayList;

public class Pellets {
    public float x;
    public float y;
    public boolean eaten;

    public Pellets(float x, float y, boolean eaten) {
        this.x = x;
        this.y = y;
        this.eaten = eaten;
    }

    public static void drawSmallPellet(float pacmanX, float pacmanY, ArrayList<Pellets> pellets) {
        for (Pellets pellet : pellets) {
            if (!pellet.eaten) {
                if (pellet.x > 140.0 && pellet.x < 460.0 && pellet.y > 200.0 && pellet.y < 420.0) {
                    continue;
                }

                if (pellet.x >= 0.0 && pellet.x < 110.0 && pellet.y > 190.0 && pellet.y < 430.0) {
                    continue;
                }

                if (pellet.x > 490.0 && pellet.x <= 600.0 && pellet.y > 190.0 && pellet.y < 430.0) {
                    continue;
                }

                if (((pellet.x >= 50.0 && pellet.x <= 110.0) && pellet.y > 158.0 && pellet.y < 170.0) ||
                    ((pellet.x >= 150.0 && pellet.x <= 250.0) && pellet.y > 158.0 && pellet.y < 170.0) ||
                    ((pellet.x >= 150.0 && pellet.x <= 180.0) && pellet.y > 70.0 && pellet.y < 130.0) ||
                    ((pellet.x >= 340.0 && pellet.x <= 440.0) && pellet.y > 158.0 && pellet.y < 170.0)) {
                    continue;
                }

                Circles.fillCircle((int) pellet.x, (int) pellet.y, 2, Color.WHITE);
            }
        }

        for (Pellets pellet : pellets) {
            if (!pellet.eaten && Math.abs(pacmanX - pellet.x) < 10.0 && Math.abs(pacmanY - pellet.y) < 10.0) {
                pellet.eaten = true;
            }
        }
    }

    public static void bigPellet(float x1, float y1, float x2, float y2, float increment) {
        // Perform the scalation
        // func: public static float[] scale(float x, float y, float sx, float sy)
        float[] v1 = Transformations.scale(x1, y1, increment, increment);
        float[] v2 = Transformations.scale(x2, y2, increment, increment);
        drawBigPellet(x1, y1, new float[] { v1[0], v1[1] }, Color.WHITE);
    }

    private static void drawBigPellet(float x1, float y1, float[] vertices, Color color) {
        // float radius = (vertices[1].x - vertices[0].x) / 2.0f;
        float radius = (vertices[1] - vertices[0]) / 2.0f;
        Circles.fillCircle((int) x1, (int) y1, (int) radius, color);
    }
}
