import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class Arquimedes extends JFrame implements Runnable {

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    public Arquimedes() {
        setTitle("Arquimedes");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);

        /*
            x = 0.5 * theta * cos(theta)
            y = 0.5 * theta * sin(theta)
            0 <= theta <= aumento
        */

        double theta = 0;
        while (true) {
            double x = 0.9 * theta * Math.cos(theta);
            double y = 0.9 * theta * Math.sin(theta);

            double x2 = 0.9 * (theta + 0.2) * Math.cos(theta + 0.2);
            double y2 = 0.9 * (theta + 0.2) * Math.sin(theta + 0.2);

            g.drawLine((int) (x + WIDTH/2), (int) (HEIGHT/2 - y), (int) (x2 + WIDTH/2), (int) (HEIGHT/2 - y2));

            theta += 0.1;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        repaint();
    }

    void main() {
        Arquimedes arq = new Arquimedes();
        arq.setVisible(true);
        Thread t = new Thread(arq);
        t.start();
    }
}
