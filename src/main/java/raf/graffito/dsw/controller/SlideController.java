package raf.graffito.dsw.controller;
import raf.graffito.dsw.controller.serializer.Serializer;
import raf.graffito.dsw.controller.toolmodes.*;

import java.awt.event.*;

import java.awt.event.*;
import javax.swing.*;
import raf.graffito.dsw.controller.undo.UndoManager;

public class SlideController {

    private model.Slide slide;
    private UndoManager undoManager;
    private Serializer serializer;
    private GraffRepository repository;

    private ToolMode currentMode;

    // Mode objekti
    private AddMode addMode;
    private SelectMode selectMode;
    private MoveMode moveMode;
    private raf.graffito.dsw.controller.toolmodes.ResizeMode resizeMode;
    private raf.graffito.dsw.controller.toolmodes.RotateMode rotateMode;

    public SlideController(model.Slide slide, GraffRepository repository) {
        this.slide = slide;
        this.repository = repository;
        this.undoManager = new UndoManager();
        this.serializer = new Serializer(repository);

        // Inicijalizacija modova
        this.addMode = new AddMode(slide, undoManager);
        this.selectMode = new SelectMode(slide);
        this.moveMode = new MoveMode(slide, undoManager);
        this.resizeMode = new raf.graffito.dsw.controller.toolmodes.ResizeMode(slide, undoManager);
        this.rotateMode = new raf.graffito.dsw.controller.toolmodes.RotateMode(slide, undoManager);

        // Po defaultu AddMode
        this.currentMode = addMode;
    }

    public void setMode(ToolMode mode) {
        this.currentMode = mode;
    }

    // Osluškivači miša
    public MouseListener getMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentMode.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentMode.mouseReleased(e);
            }
        };
    }

    public MouseMotionListener getMouseMotionListener() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentMode.mouseDragged(e);
            }
        };
    }

    public MouseWheelListener getMouseWheelListener() {
        return e -> currentMode.mouseWheelMoved(e);
    }

    // Undo/Redo
    public void undo() { undoManager.undo(); }
    public void redo() { undoManager.redo(); }

    // Save/Open
    public void save() { serializer.save(); }
    public void saveAs() { serializer.saveAs(); }
    public void openProject() { serializer.openProject(); }

    // Rotacija 90° levo/desno
    public void rotateSelected(double deltaDegrees) {
        rotateMode.rotateSelected(deltaDegrees);
    }

    // Switch modova
    public void setAddMode() { setMode(addMode); }
    public void setSelectMode() { setMode(selectMode); }
    public void setMoveMode() { setMode(moveMode); }
    public void setResizeMode() { setMode(resizeMode); }
}