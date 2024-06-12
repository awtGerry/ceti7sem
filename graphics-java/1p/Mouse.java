import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MouseEventsLogger extends JFrame implements MouseListener, MouseMotionListener {
    public MouseEventsLogger() {
        setTitle("Mouse Events Logger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MouseEventsLogger logger = new MouseEventsLogger();
                logger.setVisible(true);
            }
        });
    }

    public void mouseClicked(MouseEvent e) { logMouseEvent("Mouse Clicked", e); }
    public void mousePressed(MouseEvent e) { logMouseEvent("Mouse Pressed", e); }
    public void mouseReleased(MouseEvent e) { logMouseEvent("Mouse Released", e); }
    public void mouseEntered(MouseEvent e) { logMouseEvent("Mouse Entered", e); }
    public void mouseExited(MouseEvent e) { logMouseEvent("Mouse Exited", e); }
    public void mouseDragged(MouseEvent e) { logMouseEvent("Mouse Dragged", e); }
    public void mouseMoved(MouseEvent e) { logMouseEvent("Mouse Moved", e); }

    private void logMouseEvent(String eventType, MouseEvent e) {
        System.out.println(eventType + " at (" + e.getX() + ", " + e.getY() + ")");
    }
}
