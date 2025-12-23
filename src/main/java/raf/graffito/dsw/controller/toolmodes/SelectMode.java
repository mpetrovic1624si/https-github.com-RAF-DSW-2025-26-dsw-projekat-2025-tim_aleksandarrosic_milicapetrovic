package raf.graffito.dsw.controller.toolmodes;
import app.model.SlideElement;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;


public class SelectMode implements ToolMode {

    private model.Slide slide;
    private int startX, startY;
    private Rectangle selectionRect;

    public SelectMode(model.Slide slide) {
        this.slide = slide;
        selectionRect = new Rectangle();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        selectionRect.setBounds(startX, startY, 0, 0);

        // deselect all
        for (SlideElement el : slide.getElements()) {
            el.setSelected(false);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = Math.min(startX, e.getX());
        int y = Math.min(startY, e.getY());
        int w = Math.abs(startX - e.getX());
        int h = Math.abs(startY - e.getY());
        selectionRect.setBounds(x, y, w, h);

        // select elements in rectangle
        for (SlideElement el : slide.getElements()) {
            if (selectionRect.intersects(el.getX(), el.getY(), el.getWidth(), el.getHeight())) {
                el.setSelected(true);
            } else {
                el.setSelected(false);
            }
        }
        slide.notifyObservers();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selectionRect.setBounds(0, 0, 0, 0);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}