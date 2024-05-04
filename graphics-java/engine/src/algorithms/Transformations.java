import java.awt.Point;

public class Transformations
{
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
/*
    public static float[] rotate(float x, float y, float angle)
    {
        float[][] v1 = {
            {(float) Math.cos(angle), (float) -Math.sin(angle), 0.0f},
            {(float) Math.sin(angle), (float) Math.cos(angle), 0.0f},
            {0.0f, 0.0f, 1.0f}
        };

        float[] v2 = new float[3];

        for (int i = 0; i < 3; i++)
        {
            v2[i] = v1[i][0] * x + v1[i][1] * y + v1[i][2];
        }

        return new float[] {v2[0], v2[1]};
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
    } */
}
