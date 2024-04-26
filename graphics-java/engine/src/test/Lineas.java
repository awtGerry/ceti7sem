/* Primeras 5 practicas que dibujan distintas lineas */
package test;

public class Lineas {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel");
        Pixel pixel = new Pixel(800, 600);
        frame.add(pixel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        Lines.drawLine(0, 0, 400, 300, Color.RED);
        Lines.drawBresenham(400, 300, 800, 0, Color.BLUE);
    }
}
