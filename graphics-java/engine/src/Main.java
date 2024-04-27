import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class Main extends JPanel implements KeyListener {
    public static final Color BG = Color.BLACK;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel");
        Pixel pixel = new Pixel(800, 600);
        Main main = new Main();
        frame.add(pixel);
        frame.addKeyListener(main);
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
        if (e.getKeyCode() == KeyEvent.VK_1) { // practica 01-05
            Pixel.clear();
            Lines.drawLine(0, 100, 600, 100, Color.RED);
            Lines.drawLine2(700, 100, 700, 500, Color.YELLOW);
            Lines.drawBresenham(0, 300, 600, 300, Color.BLUE);
            Lines.drawMidPoint(0, 500, 600, 500, Color.GREEN);
        }
        if (e.getKeyCode() == KeyEvent.VK_2) { // practica 06-11
            Pixel.clear();
            // Draw rectangle
            Figures.drawRectangle(200, 400, 600, 500, Color.BLUE);
            // Draw circle
            Circles.drawCircle(100, 150, 200, 200, Color.RED);
            Circles.drawCirclePolar(500, 150, 400, 200, Color.GREEN);
            Circles.drawCircleMidPoint(500, 300, 100, Color.CYAN);
            Circles.drawCircleBresenham(100, 300, 100, Color.MAGENTA);
            Circles.drawEllipse(400, 500, 100, 50, Color.YELLOW);
        }
        if (e.getKeyCode() == KeyEvent.VK_3) { // figuras (practica 12)
            Pixel.clear();
            // 4 lines
            Lines.drawLine(10, 10, 100, 100, Color.RED);
            Lines.drawLine(150, 60, 300, 60, Color.GREEN); 
            Lines.drawLine(350, 100, 440, 10, Color.BLUE);
            Lines.drawDDA(640, 60, 490, 60, Color.YELLOW);
            // 4 Circles inside
            Circles.drawCircleMidPoint(200, 200, 5, Color.WHITE);
            Circles.drawCircleMidPoint(200, 200, 30, Color.WHITE);
            Circles.drawCircleMidPoint(200, 200, 60, Color.WHITE);
            Circles.drawCircleMidPoint(200, 200, 90, Color.WHITE);
            // 2 Rectangles
            Figures.drawRectangle(400, 150, 600, 250, Color.WHITE);
            Figures.drawRectangle(450, 180, 550, 220, Color.WHITE);
            // 4 Ellipses inside
            Circles.drawEllipse(400, 400, 80, 10, Color.WHITE);
            Circles.drawEllipse(400, 400, 120, 30, Color.WHITE);
            Circles.drawEllipse(400, 400, 160, 50, Color.WHITE);
            Circles.drawEllipse(400, 400, 200, 70, Color.WHITE);
        }
        if (e.getKeyCode() == KeyEvent.VK_4) { // practica 13-15
            Pixel.clear();
            Lines.drawDDAMask(0, 100, 600, 100, 3, Color.RED); // Linea a puntos
            Lines.drawDDAWidth(0, 200, 600, 200, 20, Color.WHITE); // Linea con grosor
            Circles.drawMaskedCircle(100, 100, 50, 2, Color.GREEN); // Circulo discontinuo
            Circles.drawCircleWidth(300, 300, 50, 20, Color.BLUE); // Circulo con grosor
            Fill.scanline(400, 400, 600, 500, Color.YELLOW); // Relleno de figura
            Figures.drawRectangle(100, 400, 300, 500, Color.CYAN); // Rectangulo
            // Inundacion necesita un punto dentro de la figura a rellenar por eso se dibuja la figura primero
            Fill.floodFill(200, 450, BG, Color.MAGENTA); // Relleno de figura
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }
}
