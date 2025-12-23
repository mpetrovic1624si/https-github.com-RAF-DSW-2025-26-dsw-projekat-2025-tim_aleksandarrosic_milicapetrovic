package raf.graffito.dsw.controller.toolmodes;

import app.model.SlideElement;
import model.Slide;
import raf.graffito.dsw.controller.undo.RotateElementCommand;
import raf.graffito.dsw.controller.undo.UndoManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.Map;

public class RotateMode implements ToolMode {

    private Slide slide;
    private UndoManager undoManager;

    public RotateMode(Slide slide, UndoManager undoManager) {
        this.slide = slide;
        this.undoManager = undoManager;
    }

    public void rotateSelected(double deltaDegrees) {
        Map<SlideElement, Double> oldRotations = new HashMap<>();
        Map<SlideElement, Double> newRotations = new HashMap<>();
        
        for (SlideElement el : slide.getElements()) {
            if (el.isSelected()) {
                oldRotations.put(el, el.getRotation());
                double newRotation = el.getRotation() + deltaDegrees;
                el.setRotation(newRotation);
                newRotations.put(el, newRotation);
            }
        }
        
        if (!oldRotations.isEmpty()) {
            undoManager.executeCommand(new RotateElementCommand(oldRotations, newRotations));
            slide.notifyObservers();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}
