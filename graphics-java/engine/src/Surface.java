import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Surface extends JFrame implements KeyListener {
    private BufferedImage bufferImage;
    private BufferedImage bufferPixel;
    private Cubo3D cube;
    private int axis = 2; // 0: X, 1: Y, 2: Z
    private boolean automaticMode = false;
    private long lastTime;
    private Timer timer;

    public Surface() {
        this.setTitle("Cubo 3D");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(this);

        bufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferPixel = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        cube = new Cubo3D(this);

        lastTime = System.currentTimeMillis();

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (automaticMode) { // Rotate automatically
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastTime > 10) {
                        cube.rotar(axis);
                        lastTime = currentTime;
                    }
                }

                repaint();
            }
        });
        timer.start();

        addKeyListener(this);
        setFocusable(true);
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < bufferImage.getWidth() && y >= 0 && y < bufferImage.getHeight()) {
            bufferPixel.setRGB(0, 0, c.getRGB());
            bufferImage.setRGB(x, y, bufferPixel.getRGB(0, 0));
        }
    }

    public void dibujarLinea(int x0, int y0, int x1, int y1, Color c) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x0, y0, c);
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    public void dibujarTriangulo(int[] x, int[] y, Color c) {
        // Ordenar los vértices por y (y0 <= y1 <= y2)
        if (y[0] > y[1]) {
            int tmp = y[0]; y[0] = y[1]; y[1] = tmp;
            tmp = x[0]; x[0] = x[1]; x[1] = tmp;
        }
        if (y[1] > y[2]) {
            int tmp = y[1]; y[1] = y[2]; y[2] = tmp;
            tmp = x[1]; x[1] = x[2]; x[2] = tmp;
        }
        if (y[0] > y[1]) {
            int tmp = y[0]; y[0] = y[1]; y[1] = tmp;
            tmp = x[0]; x[0] = x[1]; x[1] = tmp;
        }

        // Dibujar los bordes del triángulo
        dibujarLinea(x[0], y[0], x[1], y[1], c);
        dibujarLinea(x[1], y[1], x[2], y[2], c);
        dibujarLinea(x[2], y[2], x[0], y[0], c);

        // Rellenar el triángulo usando scanline
        for (int yPos = y[0]; yPos <= y[2]; yPos++) {
            int xStart, xEnd;
            if (yPos < y[1]) {
                xStart = interpolate(y[0], y[1], x[0], x[1], yPos);
                xEnd = interpolate(y[0], y[2], x[0], x[2], yPos);
            } else {
                xStart = interpolate(y[1], y[2], x[1], x[2], yPos);
                xEnd = interpolate(y[0], y[2], x[0], x[2], yPos);
            }
            if (xStart > xEnd) {
                int tmp = xStart; xStart = xEnd; xEnd = tmp;
            }
            for (int xPos = xStart; xPos <= xEnd; xPos++) {
                putPixel(xPos, yPos, c);
            }
        }
    }

    private int interpolate(int y0, int y1, int x0, int x1, int y) {
        if (y1 == y0) return x0;
        return x0 + (x1 - x0) * (y - y0) / (y1 - y0);
    }

    @Override
    public void paint(Graphics g) {
        if (bufferImage == null) {
            bufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        }

        // Clear the buffer by filling it with black color
        for (int x = 0; x < bufferImage.getWidth(); x++) {
            for (int y = 0; y < bufferImage.getHeight(); y++) {
                bufferImage.setRGB(x, y, Color.BLACK.getRGB());
            }
        }

        if (cube != null) {
            cube.dibujar();
        }

        g.drawImage(bufferImage, 0, 0, this);
    }


    public static void main(String[] args) {
        new Surface();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            axis = 1; // Rotate around Y axis
            cube.rotar(axis);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            axis = 1; // Rotate around Y axis (opposite direction)
            cube.rotar(axis);
        } else if (keyCode == KeyEvent.VK_UP) {
            axis = 0; // Rotate around X axis
            cube.rotar(axis);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            axis = 0; // Rotate around X axis (opposite direction)
            cube.rotar(axis);
        } else if (keyCode == KeyEvent.VK_R) {
            automaticMode = !automaticMode; // Toggle automatic mode
            if (automaticMode) {
                timer.start();
            } else {
                timer.stop();
            }
        }

        // Dont accumulate key events
        e.consume();
        
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No action needed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed
    }

}

class Cubo3D {
    private double[][] vertices;
    private int[][] aristas;
    private double angleX, angleY, angleZ;
    private Surface ventana;

    public Cubo3D(Surface ventana) {
        this.ventana = ventana;
        vertices = new double[][]{
                {-1, -1, -1},
                {1, -1, -1},
                {1, 1, -1},
                {-1, 1, -1},
                {-1, -1, 1},
                {1, -1, 1},
                {1, 1, 1},
                {-1, 1, 1}
        };

        aristas = new int[][]{
                {0, 1}, {1, 2}, {2, 3}, {3, 0},
                {4, 5}, {5, 6}, {6, 7}, {7, 4},
                {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        angleX = angleY = angleZ = 0;
    }

    public void dibujar() {
        double[][] projVertices = new double[8][2];

        for (int i = 0; i < vertices.length; i++) {
            double[] v = vertices[i];

            // Rotación en el eje X
            double y = v[1] * Math.cos(angleX) - v[2] * Math.sin(angleX);
            double z = v[1] * Math.sin(angleX) + v[2] * Math.cos(angleX);
            v[1] = y;
            v[2] = z;

            // Rotación en el eje Y
            double x = v[0] * Math.cos(angleY) + v[2] * Math.sin(angleY);
            z = -v[0] * Math.sin(angleY) + v[2] * Math.cos(angleY);
            v[0] = x;
            v[2] = z;

            // Rotación en el eje Z
            x = v[0] * Math.cos(angleZ) - v[1] * Math.sin(angleZ);
            y = v[0] * Math.sin(angleZ) + v[1] * Math.cos(angleZ);
            v[0] = x;
            v[1] = y;

            // Proyección ortográfica
            projVertices[i][0] = v[0];
            projVertices[i][1] = v[1];
        }

        int centroX = ventana.getWidth() / 2;
        int centroY = ventana.getHeight() / 2;
        int escala = 100;

        for (int[] arista : aristas) {
            int x0 = centroX + (int) (projVertices[arista[0]][0] * escala);
            int y0 = centroY + (int) (projVertices[arista[0]][1] * escala);
            int x1 = centroX + (int) (projVertices[arista[1]][0] * escala);
            int y1 = centroY + (int) (projVertices[arista[1]][1] * escala);

            ventana.dibujarLinea(x0, y0, x1, y1, Color.MAGENTA); // Cubo verde
        }

        int[][] triangles = {
                {0, 1, 2}, {0, 2, 3}, // Cara delantera
                {4, 5, 6}, {4, 6, 7}, // Cara trasera
                {0, 1, 5}, {0, 5, 4}, // Cara inferior
                {2, 3, 7}, {2, 7, 6}, // Cara superior
                {1, 2, 6}, {1, 6, 5}, // Cara derecha
                {0, 3, 7}, {0, 7, 4}  // Cara izquierda
        };

        for (int[] triangulo : triangles) {
            int x0 = centroX + (int) (projVertices[triangulo[0]][0] * escala);
            int y0 = centroY + (int) (projVertices[triangulo[0]][1] * escala);
            int x1 = centroX + (int) (projVertices[triangulo[1]][0] * escala);
            int y1 = centroY + (int) (projVertices[triangulo[1]][1] * escala);
            int x2 = centroX + (int) (projVertices[triangulo[2]][0] * escala);
            int y2 = centroY + (int) (projVertices[triangulo[2]][1] * escala);

            ventana.dibujarTriangulo(new int[]{x0, x1, x2}, new int[]{y0, y1, y2}, Color.MAGENTA); // Triángulo rojo
        }
    }

    // Velocidades
    public void rotar(int axis) {
        if (axis == 0) {
            angleX += 0.01;
        } else if (axis == 1) {
            angleY += 0.01;
        } else if (axis == 2) {
            angleX += 0.01;
            angleZ += 0.01;
        }
    }
}
