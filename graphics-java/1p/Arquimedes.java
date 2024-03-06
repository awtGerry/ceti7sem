/* Draw archimedean spiral using threads to draw it slowly */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class Arquimedes extends JFrame implements Runnable {
    public Arquimedes() {
        setTitle("Arquimedes");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        int x = 400;
        int y = 300;
        int r = 0;
        for (int i = 0; i < 1000; i++) {
            r = i;
            x = (int) (400 + r * Math.cos(i * 0.1));
            y = (int) (300 + r * Math.sin(i * 0.1));
            g.drawLine(x, y, x, y);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void run() {
        repaint();
    }

    public static void main(String[] args) {
        Arquimedes arq = new Arquimedes();
        arq.setVisible(true);
        Thread t = new Thread(arq);
        t.start();
    }
}
