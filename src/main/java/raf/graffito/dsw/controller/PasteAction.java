package raf.graffito.dsw.controller;

import app.model.SlideElement;
import model.Slide;
import raf.graffito.dsw.controller.undo.CopyPasteCommand;
import raf.graffito.dsw.controller.undo.UndoManager;

import java.util.List;

public class PasteAction {
    
    public static void pasteElements(Slide slide, UndoManager undoManager) {
        List<SlideElement> clipboard = CopyAction.getClipboard();
        if (clipboard.isEmpty()) {
            return;
        }
        
        // Deselect all current elements
        for (SlideElement el : slide.getElements()) {
            el.setSelected(false);
        }
        
        // Paste each element with offset
        int offsetX = 20;
        int offsetY = 20;
        for (SlideElement original : clipboard) {
            SlideElement cloned = original.cloneElement();
            cloned.setX(original.getX() + offsetX);
            cloned.setY(original.getY() + offsetY);
            cloned.setSelected(true);
            undoManager.executeCommand(new CopyPasteCommand(slide, cloned));
        }
    }
}

