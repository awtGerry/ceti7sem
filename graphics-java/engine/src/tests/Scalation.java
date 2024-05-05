public class Scalation {
    public static void main(String[] args) {
        // Test the scale method
        float[] result = Transformations.scale(2.0f, 3.0f, 2.0f, 2.0f);
        System.out.println("Scaled point: (" + result[0] + ", " + result[1] + ")");
        System.out.println("Original point: (2.0, 3.0)");
    }
}
