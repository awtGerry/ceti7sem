import java.awt.Point;
import java.awt.image.BufferedImage;

public class Transformations
{
    // Rotation vector
    public static Coordinates3D rotation = new Coordinates3D(0, 0, 0);
    // Translation vector
    public static Coordinates3D translation = new Coordinates3D(0, 0, 0);

    public static float[] translate(float x, float y, float tx, float ty)
    {
        float[][] v1 = {
            {1.0f, 0.0f, tx},
            {0.0f, 1.0f, ty},
            {0.0f, 0.0f, 1.0f}
        };

        float[] v2 = new float[3];

        for (int i = 0; i < 3; i++)
        {
            v2[i] = v1[i][0] * x + v1[i][1] * y + v1[i][2];
        }

        return new float[] {v2[0], v2[1]};
    }

    public static void rotate(double angle) {
        if (Pixel.bufferedImage != null) {
            int width = Pixel.bufferedImage.getWidth();
            int height = Pixel.bufferedImage.getHeight();

            BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            double centerX = width / 2.0;
            double centerY = height / 2.0;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    double xOffset = x - centerX;
                    double yOffset = y - centerY;

                    double newX = xOffset * Math.cos(angle) - yOffset * Math.sin(angle) + centerX;
                    double newY = xOffset * Math.sin(angle) + yOffset * Math.cos(angle) + centerY;

                    if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                        int pixelColor = Pixel.bufferedImage.getRGB(x, y);
                        rotatedImage.setRGB((int) newX, (int) newY, pixelColor);
                    }
                }
            }

            Pixel.bufferedImage = rotatedImage;
            Pixel.refresh();
        }
    }

    public static Coordinates3D translate3D(int[] point, double tx, double ty, double tz) {
        double[][] pointMatrix = {
            {point[0]}, // x
            {point[1]}, // y
            {point[2]}, // z
            {1}         // w
        };

        double[][] translationMatrix = {
            {1, 0, 0, tx},
            {0, 1, 0, ty},
            {0, 0, 1, tz},
            {0, 0, 0, 1}
        };

        double[][] result = Coordinates3D.multiply(translationMatrix, pointMatrix);

        return new Coordinates3D(result[0][0], result[1][0], result[2][0]);
    }

    public static void setTranslation(double x, double y, double z) {
        translation.setX(x);
        translation.setY(y);
        translation.setZ(z);
    }

    public static Coordinates3D rotate3D(int[] origin) {
        double[][] originMatrix = {
            {origin[0]}, // x
            {origin[1]}, // y
            {origin[2]}, // z
            {1}         // w
        };
        double xRads = rotation.getX();
        double yRads = rotation.getY();
        double zRads = rotation.getZ();

        // Rotation matrix
        double[][] xMatrix = {
            {Math.cos(yRads) * Math.cos(zRads), -Math.cos(xRads) * Math.sin(zRads) + Math.sin(xRads) * Math.sin(yRads) * Math.cos(zRads), Math.sin(xRads) * Math.sin(zRads) + Math.cos(xRads) * Math.sin(yRads) * Math.cos(zRads), 0},
            {Math.cos(yRads) * Math.sin(zRads), Math.cos(xRads) * Math.cos(zRads) + Math.sin(xRads) * Math.sin(yRads) * Math.sin(zRads), -Math.sin(xRads) * Math.cos(zRads) + Math.cos(xRads) * Math.sin(yRads) * Math.sin(zRads), 0},
            {-Math.sin(yRads), Math.sin(xRads) * Math.cos(yRads), Math.cos(xRads) * Math.cos(yRads), 0},
            {0, 0, 0, 1}
        };

        double[][] result = Coordinates3D.multiply(xMatrix, originMatrix);

        return new Coordinates3D(result[0][0], result[1][0], result[2][0]);
    }

    public static void setRotation(double x, double y, double z) {
        rotation.setX(x);
        rotation.setY(y);
        rotation.setZ(z);
    }

    public static float[] scale(float x, float y, float sx, float sy)
    {
        float[][] v1 = {
            {sx, 0.0f, 0.0f},
            {0.0f, sy, 0.0f},
            {0.0f, 0.0f, 1.0f}
        };

        float[] v2 = new float[3];

        for (int i = 0; i < 3; i++)
        {
            v2[i] = v1[i][0] * x + v1[i][1] * y + v1[i][2];
        }

        return new float[] {v2[0], v2[1]};
    }
}
