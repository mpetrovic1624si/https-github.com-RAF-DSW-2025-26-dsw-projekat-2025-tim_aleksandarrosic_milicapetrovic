package raf.graffito.dsw.controller.toolmodes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import model.Slide;
import app.model.ImageElement;
import raf.graffito.dsw.controller.undo.AddElementCommand;
import raf.graffito.dsw.controller.undo.UndoManager;

public class AddMode implements ToolMode {

    private Slide slide;
    private UndoManager undoManager;

    public AddMode(Slide slide, UndoManager undoManager) {
        this.slide = slide;
        this.undoManager = undoManager;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ImageElement element = new ImageElement(e.getX(), e.getY(), 100, 100, "test.jpg");
        undoManager.executeCommand(new AddElementCommand(slide, element));
    }

    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}
