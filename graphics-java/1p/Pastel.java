import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Pastel extends JPanel {

    ArrayList<Double> values;
    ArrayList<Color> colors;

    public Pastel(ArrayList<Double> values, ArrayList<Color> colors) {
        this.values = values;
        this.colors = colors;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double total = 0;
        for (double value : values) {
            total += value;
        }

        int diameter = Math.min(getWidth(), getHeight()) - 10;
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        int startAngle = 0;
        for (int i = 0; i < values.size(); i++) {
            double value = values.get(i);
            int arcAngle = (int) Math.round(value / total * 360);
            g.setColor(colors.get(i));
            g.fillArc(x, y, diameter, diameter, startAngle, arcAngle);
            startAngle += arcAngle;
        }
    }

    public static void main(String[] args) {
        // Verificar si se pasaron los argumentos correctamente
        if (args.length % 2 != 0) {
            return;
        }

        // Parsear los argumentos en valores y colores
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<Color> colors = new ArrayList<>();
        for (int i = 0; i < args.length; i += 2) {
            try {
                double value = Double.parseDouble(args[i]);
                Color color = Color.decode(args[i + 1]);
                values.add(value);
                colors.add(color);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Error al parsear los argumentos." + e.getMessage());
                return;
            }
        }

        // Crear y mostrar el gráfico de pastel
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pie Chart");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Pastel pastel = new Pastel(values, colors);
            frame.add(pastel);
            frame.setSize(400, 400);
            frame.setVisible(true);
        });
    }
}
