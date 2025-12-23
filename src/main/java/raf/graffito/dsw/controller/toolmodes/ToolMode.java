package raf.graffito.dsw.controller.toolmodes;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface ToolMode {
    void mousePressed(MouseEvent e);
    void mouseDragged(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseWheelMoved(MouseWheelEvent e);
}
