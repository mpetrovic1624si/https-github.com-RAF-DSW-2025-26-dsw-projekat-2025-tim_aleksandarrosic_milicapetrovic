package raf.graffito.dsw.controller;

import raf.graffito.dsw.controller.serializer.Serializer;

import java.awt.event.ActionEvent;

public class SaveAsAction extends AbstractGraffAction {
    private Serializer serializer;

    public SaveAsAction(Serializer serializer) {
        putValue(NAME, "Sačuvaj kao...");
        putValue(SHORT_DESCRIPTION, "Sačuvaj projekat na novoj lokaciji");
        putValue(ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK | java.awt.Event.SHIFT_MASK));
        this.serializer = serializer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        serializer.saveAs();
    }
}

