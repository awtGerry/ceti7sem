import javax.swing.*;
import java.awt.*;

public class Animation extends JPanel {

    public static int[] values;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    public boolean hit_ghost = false;

    private static final Color PACMAN_COLOR = Color.YELLOW;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Translate Rectangle");
        Pixel pixel = new Pixel(WIDTH, HEIGHT);
        frame.add(pixel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        /* Variables */
        float radius = 12.0f;
        // float increment = 0.0f;
        // float timer = 0; // sleep timer
        // int pacman_timer = 0;
        // int pacman_death_increment = 0;
        
        /* Draw walls outside the threads */
        Walls.drawWalls();

        // Pacman
        // randomize the initial direction.
        Characters.Direction direction = Math.random() < 0.5 ? Characters.Direction.Right : Characters.Direction.Left;
        Characters.Pacman pacman = new Characters.Pacman(300, 300, direction, 12.0f);
        pacman.draw(radius);

        Characters.Ghost red_ghost = new Characters.Ghost((WIDTH/2)-50, HEIGHT/2, Color.RED);
        Characters.Ghost pink_ghost = new Characters.Ghost((WIDTH/2)-20, HEIGHT/2, Color.PINK);
        Characters.Ghost blue_ghost = new Characters.Ghost((WIDTH/2)+20, HEIGHT/2, Color.BLUE);
        Characters.Ghost orange_ghost = new Characters.Ghost((WIDTH/2)+50, HEIGHT/2, Color.ORANGE);

        // Put the ghosts in a vector to iterate over them
        Characters.Ghost[] ghosts = {red_ghost, blue_ghost, pink_ghost, orange_ghost};

        // draw yellow rectangle in ghost's box
        Fill.scanline(230, WIDTH-350, 370, WIDTH-345, Color.YELLOW);
        ghosts[0].draw();

        // Translation thread
        // Thread translationThread = new Thread(() -> {
        //     int speed = 1; // Translation speed
        //     while (true) {
        //         try {
        //             Thread.sleep(10); // Delay for smooth animation
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //
        //         // Translate the rectangle
        //         float[] translated = Transformations.translate(values[0], values[1], speed, speed);
        //         values[0] = (int) translated[0];
        //         values[1] = (int) translated[1];
        //         translated = Transformations.translate(values[2], values[3], speed, speed);
        //         values[2] = (int) translated[0];
        //         values[3] = (int) translated[1];
        //
        //         if (values[1] >= 800) { // Reset the rectangle's position if it reaches the end
        //             values[0] = 100;
        //             values[1] = 100;
        //             values[2] = 200;
        //             values[3] = 200;
        //         }
        //
        //         // Clear the screen and redraw the rectangle at its new position
        //         // Pixel.clear();
        //         // Figures.drawRectangle(values[0], values[1], values[2], values[3], color);
        //         // Pixel.refresh();
        //     }
        // });

        // translationThread.start();
    }
}
