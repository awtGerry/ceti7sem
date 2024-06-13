import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/* 3D projections */
public class Projections {

    // projection
    public static Coordinates3D parallelProjection(Coordinates3D point, Coordinates3D vector) {
        double u;
        if (vector.z == 0) {
            u = 0;
        } else {
            u = (double) (-point.z / vector.z);
        }
        int x = (int) (point.x + vector.x * u);
        int y = (int) (point.y + vector.y * u);
        return new Coordinates3D(x, y, 0);
    }

    // projection (perspective)
    public static Coordinates2D perspectiveProjection(Coordinates3D point) {
        double u;
        Coordinates3D vector = new Coordinates3D(0, 0, 1);
        if ((point.z - vector.z) == 0) {
            u = 0;
        } else {
            u = (double) (-vector.z / (point.z - vector.z));
        }
        double x = (vector.x + ((double) point.x - (double) vector.x) * u);

        double y = (vector.y + ((double) point.y - (double) vector.y) * u);

        return new Coordinates2D((int) x, (int) y);
    }

    // Parallel projection to draw a cube
    public static void parallelCube(int x, int y, int z, int width, int[] translation, Color color) {
        Coordinates3D transformation = new Coordinates3D(translation[0], translation[1], translation[2]);
        Coordinates3D vector = new Coordinates3D(1, 0.5, 2);
        Coordinates3D[] points = new Coordinates3D[8];
        points[0] = new Coordinates3D(x, y, z);
        points[1] = new Coordinates3D(x + width, y, z);
        points[2] = new Coordinates3D(x + width, y + width, z);
        points[3] = new Coordinates3D(x, y + width, z);
        points[4] = new Coordinates3D(x, y, z + width);
        points[5] = new Coordinates3D(x + width, y, z + width);
        points[6] = new Coordinates3D(x + width, y + width, z + width);
        points[7] = new Coordinates3D(x, y + width, z + width);

        for (int i = 0; i < points.length; i++) {
            // points[i] = Coordinates3D.add(points[i], transformation);
            points[i] = Transformations.translate3D(points[i], translation[0], translation[1], translation[2]);
            points[i] = parallelProjection(points[i], vector);
        }

        // Draw
        Lines.drawDDA((int) points[0].x, (int) points[0].y, (int) points[1].x, (int) points[1].y, color);
        Lines.drawDDA((int) points[1].x, (int) points[1].y, (int) points[2].x, (int) points[2].y, color);
        Lines.drawDDA((int) points[2].x, (int) points[2].y, (int) points[3].x, (int) points[3].y, color);
        Lines.drawDDA((int) points[3].x, (int) points[3].y, (int) points[0].x, (int) points[0].y, color);

        Lines.drawDDA((int) points[4].x, (int) points[4].y, (int) points[5].x, (int) points[5].y, color);
        Lines.drawDDA((int) points[5].x, (int) points[5].y, (int) points[6].x, (int) points[6].y, color);
        Lines.drawDDA((int) points[6].x, (int) points[6].y, (int) points[7].x, (int) points[7].y, color);
        Lines.drawDDA((int) points[7].x, (int) points[7].y, (int) points[4].x, (int) points[4].y, color);

        Lines.drawDDA((int) points[0].x, (int) points[0].y, (int) points[4].x, (int) points[4].y, color);
        Lines.drawDDA((int) points[1].x, (int) points[1].y, (int) points[5].x, (int) points[5].y, color);
        Lines.drawDDA((int) points[2].x, (int) points[2].y, (int) points[6].x, (int) points[6].y, color);
        Lines.drawDDA((int) points[3].x, (int) points[3].y, (int) points[7].x, (int) points[7].y, color);
    }

    // Perspective projection to draw a cube
    public static void perspectiveCube(int vp[], int points[][], Color color) {
        int projectedPoints[][] = new int[8][2];

        for (int i = 0; i < 8; i++) {
            int u = (-vp[2]) / (points[i][2] - vp[2]);
            projectedPoints[i][0] = points[i][0] + (vp[0] * u);
            projectedPoints[i][1] = points[i][1] + (vp[1] * u);
        }

        for (int i = 0; i < 8; i++) {
            int p1x = projectedPoints[i][0];
            int p1y = projectedPoints[i][1];
            Pixel.drawPixel(p1x, p1y, color);
        }

        Lines.drawDDA(projectedPoints[0][0], projectedPoints[0][1], projectedPoints[4][0], projectedPoints[4][1], color);
        Lines.drawDDA(projectedPoints[0][0], projectedPoints[0][1], projectedPoints[4][0], projectedPoints[4][1], color);
        Lines.drawDDA(projectedPoints[2][0], projectedPoints[2][1], projectedPoints[6][0], projectedPoints[6][1], color);
        Lines.drawDDA(projectedPoints[4][0], projectedPoints[4][1], projectedPoints[6][0], projectedPoints[6][1], color);

        Lines.drawDDA(projectedPoints[5][0], projectedPoints[5][1], projectedPoints[7][0], projectedPoints[7][1], color);
        Lines.drawDDA(projectedPoints[5][0], projectedPoints[5][1], projectedPoints[1][0], projectedPoints[1][1], color);
        Lines.drawDDA(projectedPoints[1][0], projectedPoints[1][1], projectedPoints[3][0], projectedPoints[3][1], color);
        Lines.drawDDA(projectedPoints[7][0], projectedPoints[7][1], projectedPoints[3][0], projectedPoints[3][1], color);

        Lines.drawDDA(projectedPoints[4][0], projectedPoints[4][1], projectedPoints[5][0], projectedPoints[5][1], color);
        Lines.drawDDA(projectedPoints[3][0], projectedPoints[3][1], projectedPoints[2][0], projectedPoints[2][1], color);
        Lines.drawDDA(projectedPoints[1][0], projectedPoints[1][1], projectedPoints[0][0], projectedPoints[0][1], color);
        Lines.drawDDA(projectedPoints[7][0], projectedPoints[7][1], projectedPoints[6][0], projectedPoints[6][1], color);
    }

    public static void fill3DCube(int vp[], int points[][], Color color) {
        int projectedPoints[][] = new int[8][2];

        for (int i = 0; i < 8; i++) {
            int u = (-points[i][2]) / vp[2];
            projectedPoints[i][0] = points[i][0] + (vp[0] * u);
            projectedPoints[i][1] = points[i][1] + (vp[1] * u);
        }

        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int i = 0; i < 8; i++) {
            minY = Math.min(minY, projectedPoints[i][1]);
            maxY = Math.max(maxY, projectedPoints[i][1]);
        }

        for (int y = minY; y <= maxY; y++) {
            int x1 = Integer.MAX_VALUE;
            int x2 = Integer.MIN_VALUE;

            for (int i = 0; i < 8; i++) {
                int j = (i + 1) % 8;
                int y1 = projectedPoints[i][1];
                int y2 = projectedPoints[j][1];

                if ((y1 <= y && y < y2) || (y2 <= y && y < y1)) {
                    int x = (int) (projectedPoints[i][0] + (y - y1) * 1.0 * (projectedPoints[j][0] - projectedPoints[i][0]) / (y2 - y1));
                    x1 = Math.min(x1, x);
                    x2 = Math.max(x2, x);
                }
            }

            if (x1 <= x2) {
                for (int x = x1; x <= x2; x++) {
                    Pixel.drawPixel(x, y, color);
                }
            }
        }

        // Llenar las caras restantes
        Fill.fillPolygon(color, projectedPoints[0], projectedPoints[2], projectedPoints[6], projectedPoints[4]); // Cara frontal
        Fill.fillPolygon(color, projectedPoints[1], projectedPoints[3], projectedPoints[7], projectedPoints[5]); // Cara trasera
        Fill.fillPolygon(color, projectedPoints[0], projectedPoints[1], projectedPoints[5], projectedPoints[4]); // Cara izquierda
        Fill.fillPolygon(color, projectedPoints[2], projectedPoints[3], projectedPoints[7], projectedPoints[6]); // Cara derecha
        Fill.fillPolygon(color, projectedPoints[4], projectedPoints[6], projectedPoints[7], projectedPoints[5]); // Cara superior
        Fill.fillPolygon(color, projectedPoints[0], projectedPoints[2], projectedPoints[3], projectedPoints[1]); // Cara inferior

        Lines.drawDDA(projectedPoints[0][0], projectedPoints[0][1], projectedPoints[2][0], projectedPoints[2][1], color);
        Lines.drawDDA(projectedPoints[0][0], projectedPoints[0][1], projectedPoints[4][0], projectedPoints[4][1], color);
        Lines.drawDDA(projectedPoints[2][0], projectedPoints[2][1], projectedPoints[6][0], projectedPoints[6][1], color);
        Lines.drawDDA(projectedPoints[4][0], projectedPoints[4][1], projectedPoints[6][0], projectedPoints[6][1], color);

        Lines.drawDDA(projectedPoints[5][0], projectedPoints[5][1], projectedPoints[7][0], projectedPoints[7][1], color);
        Lines.drawDDA(projectedPoints[5][0], projectedPoints[5][1], projectedPoints[1][0], projectedPoints[1][1], color);
        Lines.drawDDA(projectedPoints[1][0], projectedPoints[1][1], projectedPoints[3][0], projectedPoints[3][1], color);
        Lines.drawDDA(projectedPoints[7][0], projectedPoints[7][1], projectedPoints[3][0], projectedPoints[3][1], color);

        Lines.drawDDA(projectedPoints[4][0], projectedPoints[4][1], projectedPoints[5][0], projectedPoints[5][1], color);
        Lines.drawDDA(projectedPoints[3][0], projectedPoints[3][1], projectedPoints[2][0], projectedPoints[2][1], color);
        Lines.drawDDA(projectedPoints[1][0], projectedPoints[1][1], projectedPoints[0][0], projectedPoints[0][1], color);
        Lines.drawDDA(projectedPoints[7][0], projectedPoints[7][1], projectedPoints[6][0], projectedPoints[6][1], color);
    }

    // x = (2 + cos(t)) * cos(u), y = (2 + cos(t)) * sin(u), z = t
    // public static void cylinder(List<int[]> points) {
    // }
}
