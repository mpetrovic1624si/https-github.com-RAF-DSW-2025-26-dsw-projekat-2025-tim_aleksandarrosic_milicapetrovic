package raf.graffito.dsw.controller;

import model.Slide;
import raf.graffito.dsw.controller.undo.UndoManager;

// This class is deprecated - use raf.graffito.dsw.controller.toolmodes.ResizeMode directly
@Deprecated
public class ResizeMode {
    private raf.graffito.dsw.controller.toolmodes.ResizeMode toolResizeMode;
    
    public ResizeMode(Slide slide, UndoManager undoManager) {
        this.toolResizeMode = new raf.graffito.dsw.controller.toolmodes.ResizeMode(slide, undoManager);
    }
    
    public raf.graffito.dsw.controller.toolmodes.ResizeMode getToolResizeMode() {
        return toolResizeMode;
    }
}
