package raf.graffito.dsw.controller;

import raf.graffito.dsw.controller.serializer.Serializer;

import java.awt.event.ActionEvent;

public class OpenAction extends AbstractGraffAction {
    private Serializer serializer;

    public OpenAction(Serializer serializer) {
        putValue(NAME, "Otvori projekat");
        putValue(SHORT_DESCRIPTION, "Otvori postojeći projekat");
        putValue(ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
        this.serializer = serializer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        serializer.openProject();
    }
}

