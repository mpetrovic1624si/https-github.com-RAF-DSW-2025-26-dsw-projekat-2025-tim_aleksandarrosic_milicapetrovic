package raf.graffito.dsw.controller.toolmodes;

import app.model.SlideElement;
import model.Slide;
import raf.graffito.dsw.controller.undo.ResizeElementCommand;
import raf.graffito.dsw.controller.undo.UndoManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;

public class ResizeMode implements ToolMode {

    private Slide slide;
    private UndoManager undoManager;
    private int lastX, lastY;
    private Map<SlideElement, int[]> oldSizes;

    public ResizeMode(Slide slide, UndoManager undoManager) {
        this.slide = slide;
        this.undoManager = undoManager;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
        oldSizes = new HashMap<>();
        for (SlideElement el : slide.getElements()) {
            if (el.isSelected()) {
                oldSizes.put(el, new int[]{el.getWidth(), el.getHeight()});
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - lastX;
        int dy = e.getY() - lastY;
        for (SlideElement el : oldSizes.keySet()) {
            int[] oldSize = oldSizes.get(el);
            el.setWidth(Math.max(10, oldSize[0] + dx));
            el.setHeight(Math.max(10, oldSize[1] + dy));
        }
        lastX = e.getX();
        lastY = e.getY();
        slide.notifyObservers();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Map<SlideElement, int[]> newSizes = new HashMap<>();
        for (SlideElement el : oldSizes.keySet()) {
            newSizes.put(el, new int[]{el.getWidth(), el.getHeight()});
        }
        undoManager.executeCommand(new ResizeElementCommand(oldSizes, newSizes));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}

