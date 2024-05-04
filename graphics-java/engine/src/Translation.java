import javax.swing.*;
import java.awt.*;

public class Translation {

    public static int[] values;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Translate Rectangle");
        Pixel pixel = new Pixel(800, 600);
        frame.add(pixel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        Color color = Color.RED;

        values = new int[4];
        values[0] = 0; // x1
        values[1] = 100; // y1
        values[2] = 200; // x2
        values[3] = 200; // y2

        // Initial drawing of the rectangle
        Figures.drawRectangle(values[0], values[1], values[2], values[3], color);

        // Translation thread
        Thread translationThread = new Thread(() -> {
            int speed = 1; // Translation speed
            while (true) {
                try {
                    Thread.sleep(10); // Delay for smooth animation
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Translate the rectangle
                float[] translated = Transformations.translate(values[0], values[1], speed, speed);
                values[0] = (int) translated[0];
                values[1] = (int) translated[1];
                translated = Transformations.translate(values[2], values[3], speed, speed);
                values[2] = (int) translated[0];
                values[3] = (int) translated[1];

                if (values[1] >= 800) { // Reset the rectangle's position if it reaches the end
                    values[0] = 100;
                    values[1] = 100;
                    values[2] = 200;
                    values[3] = 200;
                }

                // Clear the screen and redraw the rectangle at its new position
                Pixel.clear();
                Figures.drawRectangle(values[0], values[1], values[2], values[3], color);
                Pixel.refresh();
            }
        });

        translationThread.start();
    }
}
