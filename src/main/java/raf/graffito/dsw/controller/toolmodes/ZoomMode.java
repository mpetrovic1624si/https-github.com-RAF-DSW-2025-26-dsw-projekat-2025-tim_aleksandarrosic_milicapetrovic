package raf.graffito.dsw.controller.toolmodes;

import view.SlideView;
import java.awt.event.MouseWheelEvent;

public class ZoomMode implements ToolMode {
    
    private SlideView slideView;
    
    public ZoomMode(SlideView slideView) {
        this.slideView = slideView;
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        if (rotation < 0) {
            // Scroll up - zoom in
            slideView.zoomIn();
        } else {
            // Scroll down - zoom out
            slideView.zoomOut();
        }
    }
    
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {}
    
    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {}
    
    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {}
}

