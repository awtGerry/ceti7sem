import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;

public class Animation extends JPanel implements KeyListener {
    public static int[] values;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    public static boolean hit_ghost = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Translate Rectangle");
        Pixel pixel = new Pixel(WIDTH, HEIGHT);
        Animation animation = new Animation();
        frame.add(pixel);
        frame.addKeyListener(animation);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        /* Variables */
        AtomicReference<Float> radius = new AtomicReference<>(12.0f);
        AtomicReference<Float> increment = new AtomicReference<>(0.0f);
        AtomicReference<Float> timer = new AtomicReference<>(0.0f);

        final int fixedSleepTime = 15; // milliseconds

        AtomicReference<Float> pacman_timer = new AtomicReference<>(0.0f);
        AtomicReference<Integer> pacman_death_increment = new AtomicReference<>(0);

        // Pacman
        // randomize the initial direction.
        Characters.Direction direction = Math.random() < 0.5 ? Characters.Direction.Right : Characters.Direction.Left;
        Characters.Pacman pacman = new Characters.Pacman(WIDTH/2, WIDTH-130, direction, 12.0f);
        pacman.draw(radius.get());

        // Ghosts
        Characters.Ghost red_ghost = new Characters.Ghost((WIDTH/2)-50, HEIGHT/2, Color.RED);
        Characters.Ghost pink_ghost = new Characters.Ghost((WIDTH/2)-20, HEIGHT/2, Color.PINK);
        Characters.Ghost blue_ghost = new Characters.Ghost((WIDTH/2)+20, HEIGHT/2, Color.CYAN);
        Characters.Ghost orange_ghost = new Characters.Ghost((WIDTH/2)+50, HEIGHT/2, Color.ORANGE);

        // Put the ghosts in a vector to iterate over them
        Characters.Ghost[] ghosts = {red_ghost, blue_ghost, pink_ghost, orange_ghost};

        // draw yellow rectangle in ghost's box
        Fill.scanline(230, WIDTH-350, 370, WIDTH-345, Color.YELLOW);

        // draw pellets
        ArrayList<Pellets> consumed_pellets = new ArrayList<Pellets>();
        for (int i = 0; i < 600; i += 25) {
            for (int j = 0; j < 600; j += 27) {
                Pellets pellet = new Pellets(i, j, false);
                consumed_pellets.add(pellet);
            }
        }

        // Translation thread
        Thread translationThread = new Thread(() -> {
            while (true) {
                // Temporary buffer to store drawing operations
                ArrayList<Runnable> drawingOperations = new ArrayList<>();

                // Clear the screen and redraw the rectangle at its new position
                drawingOperations.add(() -> Pixel.clear());

                try {
                    Thread.sleep(fixedSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (radius.get() < 5.0f) { radius.set(12.0f); }
                if (increment.get() > 0.6f) { increment.set(0.0f); }

                // Move the ghosts
                drawingOperations.add(() -> ghosts[0].moveGhost());
                if (timer.get() >= 2.5f) { drawingOperations.add(() -> ghosts[2].moveGhost()); }
                if (timer.get() >= 5.0f) { drawingOperations.add(() -> ghosts[1].moveGhost()); }
                if (timer.get() >= 7.5f) { drawingOperations.add(() -> ghosts[3].moveGhost()); }

                for (Characters.Ghost ghost : ghosts) {
                    if (isCollision(pacman, ghost)) {
                        hit_ghost = true;
                        break;
                    }
                }

                if (hit_ghost) {
                    if (pacman_timer.get() <= 2) {
                        pacman_death_increment.set(pacman_death_increment.get() + 1);
                        pacman_timer.set(pacman_timer.get() + 0.1f);
                    } else {
                        pacman_death_increment.set(pacman_death_increment.get() - 1);
                    }
                    Walls.drawWalls();
                } else {
                  drawingOperations.add(() -> pacman.movePacman());
                  drawingOperations.add(() -> pacman.draw(radius.get()));

                  for (Characters.Ghost ghost : ghosts) {
                    drawingOperations.add(() -> ghost.draw());
                  }

                  drawingOperations.add(() -> Walls.drawWalls());

                  drawingOperations.add(() -> Pellets.drawSmallPellet(Characters.Pacman.getX(), Characters.Pacman.getY(), consumed_pellets));
                  drawingOperations.add(() -> Pellets.bigPellet((float) 25.0, (float) 493.0, (float) 26.0, (float) 494.0, increment.get()));
                  // Pellets.bigPellet((float) 575.0, (float) 493.0, (float) 595.0, (float) 513.0, increment.get());
                  // Pellets.bigPellet((float) 25.0, (float) 140.0, (float) 45.0, (float) 160.0, increment.get());
                  // Pellets.bigPellet((float) 575.0, (float) 140.0, (float) 595.0, (float) 160.0, increment.get());
                }

                radius.set(radius.get() - 2.5f);
                increment.set(increment.get() + 0.1f);
                timer.set(timer.get() + 0.1f);

                // Execute all drawing operations at once
                for (Runnable operation : drawingOperations) {
                    operation.run();
                }
          }
        });
        translationThread.start();
    }

    public static boolean isCollision(Characters.Pacman pacman, Characters.Ghost ghost) {
        double distance = Math.sqrt(Math.pow(pacman.getX() - ghost.getX(), 2) + Math.pow(pacman.getY() - ghost.getY(), 2));
        return distance < 10.0;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            Characters.Pacman.userChangeDirection(Characters.Direction.Left);
        } else if (key == KeyEvent.VK_RIGHT) {
            Characters.Pacman.userChangeDirection(Characters.Direction.Right);
        } else if (key == KeyEvent.VK_UP) {
            Characters.Pacman.userChangeDirection(Characters.Direction.Up);
        } else if (key == KeyEvent.VK_DOWN) {
            Characters.Pacman.userChangeDirection(Characters.Direction.Down);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
