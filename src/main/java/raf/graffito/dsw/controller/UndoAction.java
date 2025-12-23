package raf.graffito.dsw.controller;

import raf.graffito.dsw.controller.undo.UndoManager;

import java.awt.event.ActionEvent;

public class UndoAction extends AbstractGraffAction {
    private UndoManager undoManager;

    public UndoAction(UndoManager undoManager) {
        putValue(NAME, "Poništi");
        putValue(SHORT_DESCRIPTION, "Poništi poslednju akciju");
        putValue(ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.Event.CTRL_MASK));
        this.undoManager = undoManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        undoManager.undo();
    }
}

