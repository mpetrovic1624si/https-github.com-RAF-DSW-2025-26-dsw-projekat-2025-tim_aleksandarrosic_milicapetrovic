package raf.graffito.dsw.controller;

import app.model.SlideElement;
import model.Slide;
import raf.graffito.dsw.controller.undo.DeleteElementCommand;
import raf.graffito.dsw.controller.undo.UndoManager;

import java.util.ArrayList;
import java.util.List;

public class DeleteElementAction {
    
    public static void deleteSelectedElements(Slide slide, UndoManager undoManager) {
        List<SlideElement> toDelete = new ArrayList<>();
        for (SlideElement el : slide.getElements()) {
            if (el.isSelected()) {
                toDelete.add(el);
            }
        }
        
        if (!toDelete.isEmpty()) {
            undoManager.executeCommand(new DeleteElementCommand(slide, toDelete));
        }
    }
}

