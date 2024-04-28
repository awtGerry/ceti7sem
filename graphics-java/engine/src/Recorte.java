import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Recorte extends MouseAdapter {

    private int x1, y1, x2, y2;
    private JFrame frame;
    private Pixel pixel;
    private List<Figure> figuras;
    private String figure = "rectangle";
    int maxX, maxY, minX, minY; // area where is allowed to draw

    public static void main(String[] args) {
        new Recorte().start();
    }

    public void start() {
        frame = new JFrame("Recorte");
        pixel = new Pixel(800, 600);
        figuras = new ArrayList<>();

        frame.add(pixel);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                Pixel.clear();
                drawFigures();
                if (figure.equals("rectangle")) {
                    Figures.drawRectangle(x1, y1, Math.min(maxX, Math.max(minX, x2)), Math.min(maxY, Math.max(minY, y2)), Color.BLUE);
                } else if (figure.equals("circle")) {
                    Circles.drawCircleMidPoint(x1, y1, Math.min(maxX, Math.max(minX, x2)) - x1, Color.RED);
                } else if (figure.equals("ellipse")) {
                    Circles.drawEllipse(x1, y1, Math.min(maxX, Math.max(minX, x2)) - x1, Math.min(maxY, Math.max(minY, y2)) - y1, Color.GREEN);
                } else if (figure.equals("recorte")) {
                    Figures.drawRectangle(x1, y1, x2, y2, Color.WHITE);
                    maxX = Math.max(x1, x2);
                    maxY = Math.max(y1, y2);
                    minX = Math.min(x1, x2);
                    minY = Math.min(y1, y2);
                }
                Pixel.refresh();
            }
        });

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    figure = "rectangle";
                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    figure = "circle";
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    figure = "ellipse";
                }
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    Pixel.clear();
                    figuras.clear();
                    Pixel.refresh();
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    figure = "recorte";
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used
            }
        });

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        /* here is the starting point */
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /* here is the ending point */
        x2 = e.getX();
        y2 = e.getY();
        figuras.add(new Figure(figure, x1, y1, x2, y2, getColor(figure)));
        Pixel.clear();
        drawFigures();
        Pixel.refresh();
    }

    private Color getColor(String figure) {
        switch (figure) {
            case "rectangle":
                return Color.BLUE;
            case "circle":
                return Color.RED;
            case "ellipse":
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }

    private void drawFigures() {
        for (Figure f : figuras) {
            if (f.getType().equals("rectangle")) {
                Figures.drawRectangle(f.getX1(), f.getY1(), Math.min(maxX, Math.max(minX, f.getX2())), Math.min(maxY, Math.max(minY, f.getY2())), f.getColor());
            } else if (f.getType().equals("circle")) {
                Circles.drawCircleMidPoint(f.getX1(), f.getY1(), Math.min(maxX, Math.max(minX, f.getX2())) - f.getX1(), f.getColor());
            } else if (f.getType().equals("ellipse")) {
                Circles.drawEllipse(f.getX1(), f.getY1(), Math.min(maxX, Math.max(minX, f.getX2())) - f.getX1(), Math.min(maxY, Math.max(minY, f.getY2())) - f.getY1(), f.getColor());
            } else if (f.getType().equals("recorte")) {
                Figures.drawRectangle(f.getX1(), f.getY1(), f.getX2(), f.getY2(), Color.WHITE);
            }
        }
    }

    private static class Figure {
        private String type;
        private int x1, y1, x2, y2;
        private Color color;

        public Figure(String type, int x1, int y1, int x2, int y2, Color color) {
            this.type = type;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }

        public String getType() { return type; }
        public int getX1() { return x1; }
        public int getY1() { return y1; }
        public int getX2() { return x2; }
        public int getY2() { return y2; }
        public Color getColor() { return color; }
    }
}
