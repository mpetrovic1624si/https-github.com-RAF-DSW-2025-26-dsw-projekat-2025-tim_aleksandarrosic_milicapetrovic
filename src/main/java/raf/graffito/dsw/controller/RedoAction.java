package raf.graffito.dsw.controller;

import raf.graffito.dsw.controller.undo.UndoManager;

import java.awt.event.ActionEvent;

public class RedoAction extends AbstractGraffAction {
    private UndoManager undoManager;

    public RedoAction(UndoManager undoManager) {
        putValue(NAME, "Ponovi");
        putValue(SHORT_DESCRIPTION, "Ponovi poništenu akciju");
        putValue(ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.Event.CTRL_MASK));
        this.undoManager = undoManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        undoManager.redo();
    }
}

