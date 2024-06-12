public class Movements {
    public static float[] translate(float x, float y, float tx, float ty) {
        float[] translated = new float[2];
        translated[0] = x + tx;
        translated[1] = y + ty;
        return translated;
    }
}
