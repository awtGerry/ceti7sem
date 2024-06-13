import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Tridi extends JPanel implements KeyListener {
    public static final Color BG = Color.BLACK;
    private Set<Integer> pressedKeys = new HashSet<>();
    private Thread thread;

    // public Tridi() {
    //     addKeyListener(this);
    //     setFocusable(true);
    //     requestFocus();
    //     // startTranslationThread();
    // }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel");
        Pixel pixel = new Pixel(800, 600);
        Tridi main = new Tridi();
        frame.add(pixel);
        frame.addKeyListener(main);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_1) { // practica 01 Projecting 3D cube
            Pixel.clear();
            Projections.parallelCube(100, 100, 100, 50, new int[]{0, 0, 0}, Color.MAGENTA);
        }
        if (e.getKeyCode() == KeyEvent.VK_2) {
            Pixel.clear();
            int vp[] = {20, 20, 500};
            int points[][] = {
                {100, 100, 100},
                {100, 300, 100},
                {300, 100, 100},
                {300, 300, 100},
                {100, 100, 300},
                {100, 300, 300},
                {300, 100, 300},
                {300, 300, 300}
            };
            Projections.perspectiveCube(vp, points, Color.MAGENTA);
        }
        if (e.getKeyCode() == KeyEvent.VK_3) { // Translate 3D cube
            Pixel.clear();
            int x = 400, y = 300, z = 100, w = 50;
            Coordinates3D[] points = new Coordinates3D[8];
            Projections.parallelCube(x, y, z, w, new int[]{0, 0, 0}, Color.MAGENTA);
            thread = new Thread(() -> {
                int speed = 5; // Translation speed
                while (true) {
                    try {
                        Thread.sleep(50); // Delay for smooth animation
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    int tx = 0, ty = 0, tz = 0;
                    boolean translated = false;

                    synchronized (this) {
                        if (pressedKeys.contains(KeyEvent.VK_W)) { // Translate up
                            ty = -speed;
                            translated = true;
                        }
                        if (pressedKeys.contains(KeyEvent.VK_S)) { // Translate down
                            ty = speed;
                            translated = true;
                        }
                        if (pressedKeys.contains(KeyEvent.VK_A)) { // Translate left
                            tx = -speed;
                            translated = true;
                        }
                        if (pressedKeys.contains(KeyEvent.VK_D)) { // Translate right
                            tx = speed;
                            translated = true;
                        }

                        if (translated) {
                            Pixel.clear();
                            Projections.parallelCube(x, y, z, w, new int[]{tx, ty, tz}, Color.YELLOW);
                            Pixel.refresh();
                        }
                    }
                }
            });
        }
        /* if (e.getKeyCode() == KeyEvent.VK_3) { // Translate 3D cube
            Pixel.clear();
            int vp[] = {5, 5, 100}; // Viewpoint
            int points[][] = {
                {300, 200, 200},
                {300, 400, 300},
                {500, 200, 200},
                {500, 400, 300},
                {200, 300, 200},
                {200, 500, 400},
                {400, 300, 200},
                {400, 500, 400}};
            Projections.parallelCube(vp, points, Color.MAGENTA);
            thread = new Thread(() -> {
                int speed = 5; // Translation speed
                while (true) {
                    try {
                        Thread.sleep(50); // Delay for smooth animation
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    double tx = 0, ty = 0, tz = 0;
                    boolean translated = false;

                    synchronized (points) {
                        if (pressedKeys.contains(KeyEvent.VK_W)) { // Translate up
                            ty = -speed;
                            translated = true;
                        }
                        if (pressedKeys.contains(KeyEvent.VK_S)) { // Translate down
                            ty = speed;
                            translated = true;
                        }
                        if (pressedKeys.contains(KeyEvent.VK_A)) { // Translate left
                            tx = -speed;
                            translated = true;
                        }
                        if (pressedKeys.contains(KeyEvent.VK_D)) { // Translate right
                            tx = speed;
                            translated = true;
                        }

                        if (translated) {
                            for (int i = 0; i < points.length; i++) {
                                Coordinates3D translatedPoint = Transformations.translate3D(points[i], tx, ty, tz);
                                points[i][0] = (int) translatedPoint.getX();
                                points[i][1] = (int) translatedPoint.getY();
                                points[i][2] = (int) translatedPoint.getZ();
                            }

                            Pixel.clear();
                            Projections.parallelCube(vp, points, Color.YELLOW);
                            Pixel.refresh();
                        }
                    }
                }
            });
            thread.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_4) { // Scale 3D cube
            Pixel.clear();
            pressedKeys.clear();
            int vp[] = {5, 5, 100}; // Viewpoint
            int points[][] = {
                {300, 200, 200},
                {300, 400, 300},
                {500, 200, 200},
                {500, 400, 300},
                {200, 300, 200},
                {200, 500, 400},
                {400, 300, 200},
                {400, 500, 400}};
            thread = new Thread(() -> {
                float scale = 1.01f; // Scale factor
                while (true) {
                    try {
                        Thread.sleep(50); // Delay for smooth animation
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    synchronized (points) {
                        if (pressedKeys.contains(KeyEvent.VK_EQUALS)) { // Scale up
                            pressedKeys.clear();
                            for (int i = 0; i < 8; i++) {
                                float[] scaled = Transformations.scale(points[i][0], points[i][1], scale, scale);
                                points[i][0] = (int) scaled[0];
                                points[i][1] = (int) scaled[1];
                            }
                        }
                        if (pressedKeys.contains(KeyEvent.VK_MINUS)) { // Scale down
                            pressedKeys.clear();
                            for (int i = 0; i < 8; i++) {
                                float[] scaled = Transformations.scale(points[i][0], points[i][1], 1 / scale, 1 / scale);
                                points[i][0] = (int) scaled[0];
                                points[i][1] = (int) scaled[1];
                            }
                        }

                        Pixel.clear();
                        Projections.parallelCube(vp, points, Color.YELLOW);
                        Pixel.refresh();
                    }
                }
            });
            thread.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_5) { // Rotate 3D cube automatically
            Pixel.clear();
            pressedKeys.clear();
            int vp[] = {5, 5, 100}; // Viewpoint
            int points[][] = {
                {300, 200, 200},
                {300, 400, 300},
                {500, 200, 200},
                {500, 400, 300},
                {200, 300, 200},
                {200, 500, 400},
                {400, 300, 200},
                {400, 500, 400}};

            Projections.parallelCube(vp, points, Color.MAGENTA);

            thread = new Thread(() -> {
                double angle = Math.toRadians(1); // Rotation angle
                while (true) {
                    try {
                        Thread.sleep(50); // Delay for smooth animation
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    synchronized (points) {
                        Transformations.setRotation(angle, angle, angle);
                        Coordinates3D centroid = calculateCentroid(points);
                        for (int i = 0; i < points.length; i++) {
                            Coordinates3D rotatedPoint = Transformations.rotate3D(points[i]);
                            points[i][0] = (int) rotatedPoint.getX();
                            points[i][1] = (int) rotatedPoint.getY();
                            points[i][2] = (int) rotatedPoint.getZ();
                        }
                        Coordinates3D newCentroid = calculateCentroid(points);

                        double tx = centroid.getX() - newCentroid.getX();
                        double ty = centroid.getY() - newCentroid.getY();
                        double tz = centroid.getZ() - newCentroid.getZ();

                        for (int i = 0; i < points.length; i++) {
                            points[i][0] += tx;
                            points[i][1] += ty;
                            points[i][2] += tz;
                        }

                        Pixel.clear();
                        Projections.parallelCube(vp, points, Color.YELLOW);
                        Pixel.refresh();
                    }
                }
            });
            thread.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_6) { // Rotate 3D cube manually
            Pixel.clear();
            pressedKeys.clear();
            int vp[] = {5, 5, 100}; // Viewpoint
            int points[][] = {
                {300, 200, 200},
                {300, 400, 300},
                {500, 200, 200},
                {500, 400, 300},
                {200, 300, 200},
                {200, 500, 400},
                {400, 300, 200},
                {400, 500, 400}};

            Projections.parallelCube(vp, points, Color.MAGENTA);

            thread = new Thread(() -> {
                double angle = Math.toRadians(1); // Rotation angle
                while (true) {
                    try {
                        Thread.sleep(50); // Delay for smooth animation
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    boolean rotated = false;
                    synchronized (points) {
                        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) { // Rotate right
                            Transformations.setRotation(angle, 0, 0);
                            rotated = true;
                        }
                        if (pressedKeys.contains(KeyEvent.VK_LEFT)) { // Rotate left
                            Transformations.setRotation(-angle, 0, 0);
                            rotated = true;
                        }
                        if (pressedKeys.contains(KeyEvent.VK_UP)) { // Rotate up
                            Transformations.setRotation(0, angle, 0);
                            rotated = true;
                        }
                        if (pressedKeys.contains(KeyEvent.VK_DOWN)) { // Rotate down
                            Transformations.setRotation(0, -angle, 0);
                            rotated = true;
                        }
                        if (pressedKeys.contains(KeyEvent.VK_A)) { // Rotate counterclockwise
                            Transformations.setRotation(0, 0, angle);
                            rotated = true;
                        }

                        if (rotated) {
                            Coordinates3D centroid = calculateCentroid(points);
                            for (int i = 0; i < points.length; i++) {
                                Coordinates3D rotatedPoint = Transformations.rotate3D(points[i]);
                                points[i][0] = (int) rotatedPoint.getX();
                                points[i][1] = (int) rotatedPoint.getY();
                                points[i][2] = (int) rotatedPoint.getZ();
                            }
                            Coordinates3D newCentroid = calculateCentroid(points);

                            double tx = centroid.getX() - newCentroid.getX();
                            double ty = centroid.getY() - newCentroid.getY();
                            double tz = centroid.getZ() - newCentroid.getZ();

                            for (int i = 0; i < points.length; i++) {
                                points[i][0] += tx;
                                points[i][1] += ty;
                                points[i][2] += tz;
                            }

                            Pixel.clear();
                            Projections.parallelCube(vp, points, Color.YELLOW);
                            Pixel.refresh();
                        }

                        // Pixel.clear();
                        // Projections.parallelCube(vp, points, Color.YELLOW);
                        // Pixel.refresh();
                    }
                }
            });
            thread.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_9) { // cylinder/hyperboloid
            Pixel.clear();
            int vp[] = {5, 5, 100}; // Viewpoint
            java.util.List<int[]> points = new ArrayList<>();
            Projections.cylinder(points);
            for (int i = 0; i < points.size(); i++) {
                int[] point = points.get(i);
                Pixel.drawPixel(point[0], point[1], Color.MAGENTA);
            }
        } */
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    private Coordinates3D calculateCentroid(int[][] points) {
        int cx = 0, cy = 0, cz = 0;
        for (int i = 0; i < points.length; i++) {
            cx += points[i][0];
            cy += points[i][1];
            cz += points[i][2];
        }
        double numPoints = points.length;
        return new Coordinates3D(cx / numPoints, cy / numPoints, cz / numPoints);
    }
}
