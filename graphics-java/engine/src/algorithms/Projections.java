import java.awt.Color;

/* 3D projections */
public class Projections {
    // Parallel projection to draw a cube
    public static void parallelCube(int vp[], int points[][], Color color) {

        int projectedPoints[][] = new int[8][2];

        for (int i = 0; i < 8; i++) {
            int u = (-points[i][2]) / vp[2];
            projectedPoints[i][0] = points[i][0] + (vp[0] * u);
            projectedPoints[i][1] = points[i][1] + (vp[1] * u);
        }

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

        for (int i = 0; i < 8; i++) {
            int p1x = projectedPoints[i][0];
            int p1y = projectedPoints[i][1];
            Pixel.drawPixel(p1x, p1y, color);
        }
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

    public static void line3D(int x1, int y1, int z1, int x2, int y2, int z2) {
        Coordinates3D[] puntos = new Coordinates3D[2];

        puntos[0] = new Coordinates3D(x1, y1, z1);
        puntos[1] = new Coordinates3D(x2, y2, z2);
        Coordinates2D[] puntosFinales = new Coordinates2D[2];
        for (int i = 0; i < puntos.length; i++) {
            puntosFinales[i] = p
        }
        Lines.drawDDA(puntosFinales[0].x, puntosFinales[0].y, puntosFinales[1].x, puntosFinales[1].y);
    }
}
