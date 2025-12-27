package raf.graffito.dsw.controller.toolmodes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import model.Slide;
import app.model.ImageElement;
import app.model.SlideElement;
import model.TextElement;
import model.LogoElement;
import raf.graffito.dsw.controller.undo.AddElementCommand;
import raf.graffito.dsw.controller.undo.UndoManager;
import raf.graffito.dsw.controller.ElementTypeSelector;
import raf.graffito.dsw.controller.SlideControllerManager;
import raf.graffito.dsw.gui.swing.ImageLoaderPanel;
import javax.swing.JOptionPane;
import java.io.File;

public class AddMode implements ToolMode {

    private Slide slide;
    private UndoManager undoManager;
    private static ElementTypeSelector.ElementType lastSelectedType = ElementTypeSelector.ElementType.IMAGE;

    public AddMode(Slide slide, UndoManager undoManager) {
        this.slide = slide;
        this.undoManager = undoManager;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // If Ctrl/Cmd pressed, show type selector
        ElementTypeSelector.ElementType type = lastSelectedType;
        if (e.isControlDown() || e.isMetaDown()) {
            type = ElementTypeSelector.showTypeSelector();
            lastSelectedType = type;
        }
        
        SlideElement element = null;
        
        switch (type) {
            case IMAGE:
                element = createImageElement(e.getX(), e.getY());
                break;
            case TEXT:
                element = createTextElement(e.getX(), e.getY());
                break;
            case LOGO:
                element = createLogoElement(e.getX(), e.getY());
                break;
        }
        
        if (element != null) {
            // Check if there's enough space
            if (raf.graffito.dsw.controller.SpaceCheckerManager.getInstance().hasEnoughSpace(slide, element)) {
                undoManager.executeCommand(new AddElementCommand(slide, element));
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Nema dovoljno prostora na slajdu za dodavanje novog elementa!\n" +
                    "Slobodan prostor mora biti najmanje 20% ukupne površine slajda.",
                    "Nedovoljno prostora", 
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private SlideElement createImageElement(int x, int y) {
        // Try to get selected image from ImageLoaderPanel
        String imagePath = getSelectedImagePath();
        if (imagePath == null) {
            imagePath = "test.jpg"; // Default fallback
        }
        return new ImageElement(x, y, 100, 100, imagePath);
    }
    
    private SlideElement createTextElement(int x, int y) {
        String text = JOptionPane.showInputDialog(null, "Enter text:", "Add Text Element", JOptionPane.PLAIN_MESSAGE);
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        return new TextElement(x, y, 200, 50, text);
    }
    
    private SlideElement createLogoElement(int x, int y) {
        return new LogoElement(x, y, 100, 100, "triangle");
    }
    
    private String getSelectedImagePath() {
        return raf.graffito.dsw.controller.ImageLoaderManager.getInstance().getSelectedImagePath();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}
