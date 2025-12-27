package raf.graffito.dsw.controller;
import raf.graffito.dsw.controller.serializer.Serializer;
import raf.graffito.dsw.controller.toolmodes.*;

import java.awt.event.*;

import java.awt.event.*;
import javax.swing.*;
import raf.graffito.dsw.controller.undo.UndoManager;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class SlideController {

    private model.Slide slide;
    private UndoManager undoManager;
    private Serializer serializer;
    private GraffRepository repository;

    private ToolMode currentMode;
    private view.SlideView slideView;

    // Mode objekti
    private AddMode addMode;
    private SelectMode selectMode;
    private MoveMode moveMode;
    private raf.graffito.dsw.controller.toolmodes.ResizeMode resizeMode;
    private raf.graffito.dsw.controller.toolmodes.RotateMode rotateMode;
    private raf.graffito.dsw.controller.toolmodes.ZoomMode zoomMode;

    public SlideController(model.Slide slide, GraffRepository repository, view.SlideView slideView) {
        this.slide = slide;
        this.repository = repository;
        this.slideView = slideView;
        this.undoManager = new UndoManager();
        this.serializer = new Serializer(repository);

        // Inicijalizacija modova
        this.addMode = new AddMode(slide, undoManager);
        this.selectMode = new SelectMode(slide);
        this.moveMode = new MoveMode(slide, undoManager);
        this.resizeMode = new raf.graffito.dsw.controller.toolmodes.ResizeMode(slide, undoManager);
        this.rotateMode = new raf.graffito.dsw.controller.toolmodes.RotateMode(slide, undoManager);
        this.zoomMode = new raf.graffito.dsw.controller.toolmodes.ZoomMode(slideView);

        // Po defaultu AddMode
        this.currentMode = addMode;
    }
    
    public SlideController(model.Slide slide, GraffRepository repository) {
        this(slide, repository, null);
    }
    
    public void setSlideView(view.SlideView slideView) {
        this.slideView = slideView;
        if (slideView != null) {
            this.zoomMode = new raf.graffito.dsw.controller.toolmodes.ZoomMode(slideView);
        }
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
        return e -> {
            // Zoom je uvek aktivan, nezavisno od trenutnog moda
            if (zoomMode != null) {
                zoomMode.mouseWheelMoved(e);
            }
            // Takođe pozovi trenutni mode ako ima implementaciju
            currentMode.mouseWheelMoved(e);
        };
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
    public void setRotateMode() { setMode(rotateMode); }
    
    // Delete selected elements
    public void deleteSelectedElements() {
        DeleteElementAction.deleteSelectedElements(slide, undoManager);
    }
    
    // Get keyboard listener for delete key
    public KeyListener getKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    deleteSelectedElements();
                } else if (e.isControlDown() || e.isMetaDown()) {
                    if (e.getKeyCode() == KeyEvent.VK_C) {
                        copySelectedElements();
                    } else if (e.getKeyCode() == KeyEvent.VK_V) {
                        pasteElements();
                    }
                }
            }
        };
    }
    
    // Rotate 90 degrees
    public void rotate90Left() {
        rotateSelected(-90);
    }
    
    public void rotate90Right() {
        rotateSelected(90);
    }
    
    // Copy/Paste
    public void copySelectedElements() {
        CopyAction.copySelectedElements(slide);
    }
    
    public void pasteElements() {
        PasteAction.pasteElements(slide, undoManager);
        slide.notifyObservers();
    }