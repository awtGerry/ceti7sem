public class Coordinates3D {
    private double x, y, z;

    public Coordinates3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double dotProduct(Coordinates3D vector) {
        return x * vector.getX() + y * vector.getY() + z * vector.getZ();
    }

    public static double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int aRows = matrixA.length;
        int aCols = matrixA[0].length;
        int bCols = matrixB[0].length;

        double[][] result = new double[aRows][bCols];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                for (int k = 0; k < aCols; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }
}
