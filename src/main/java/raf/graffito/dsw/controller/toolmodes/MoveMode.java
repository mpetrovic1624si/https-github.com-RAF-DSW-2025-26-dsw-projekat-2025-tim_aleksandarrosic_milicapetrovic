package raf.graffito.dsw.controller.toolmodes;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;

import app.model.SlideElement;
import model.Slide;
import raf.graffito.dsw.controller.undo.MoveElementCommand;

import raf.graffito.dsw.controller.undo.UndoManager;

public class MoveMode implements ToolMode {

    private Slide slide;
    private UndoManager undoManager;
    private int lastX, lastY;
    private Map<SlideElement, int[]> oldPositions;

    public MoveMode(Slide slide, UndoManager undoManager) {
        this.slide = slide;
        this.undoManager = undoManager;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
        oldPositions = new HashMap<>();
        for (SlideElement el : slide.getElements()) {
            if (el.isSelected()) {
                oldPositions.put(el, new int[]{el.getX(), el.getY()});
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - lastX;
        int dy = e.getY() - lastY;
        for (SlideElement el : oldPositions.keySet()) {
            el.setX(el.getX() + dx);
            el.setY(el.getY() + dy);
        }
        lastX = e.getX();
        lastY = e.getY();
        slide.notifyObservers();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!oldPositions.isEmpty()) {
            Map<SlideElement, int[]> newPositions = new HashMap<>();
            for (SlideElement el : oldPositions.keySet()) {
                newPositions.put(el, new int[]{el.getX(), el.getY()});
            }
            undoManager.executeCommand(new MoveElementCommand(oldPositions, newPositions));
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}
