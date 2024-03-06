import java.awt.*;
import javax.swing.*;

public class Visor extends JFrame {
    private JScrollPane scroll;
    private Pantalla pantalla;

    public Visor(String archivo) {
        setTitle("Visor");
        Image image = Toolkit.getDefaultToolkit().getImage(archivo);
        pantalla = new Pantalla(image);
        scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scroll, BorderLayout.CENTER);
        scroll.getViewport().add(pantalla);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Visor("imagen.jpg");
    }

    class Pantalla extends JPanel {
        private Image image;

        public void paint(Graphics g) {
            super.paint(g);
            Dimension size = new Dimension(0, 0);
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            update(g);
        }

        public Pantalla(Image image) {
            this.image = image;
        }

        public void update(Graphics g) {
            g.drawImage(image, 0, 0, this);
        }

    }

}
