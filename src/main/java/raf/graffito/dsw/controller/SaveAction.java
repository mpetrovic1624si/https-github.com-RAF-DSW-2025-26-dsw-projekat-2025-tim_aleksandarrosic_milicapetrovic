package raf.graffito.dsw.controller;

import raf.graffito.dsw.controller.serializer.Serializer;
import raf.graffito.dsw.core.graff.GraffRepository;

import java.awt.event.ActionEvent;

public class SaveAction extends AbstractGraffAction {
    private Serializer serializer;

    public SaveAction(Serializer serializer) {
        putValue(NAME, "Sačuvaj");
        putValue(SHORT_DESCRIPTION, "Sačuvaj projekat");
        putValue(ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
        this.serializer = serializer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        serializer.save();
    }
}

