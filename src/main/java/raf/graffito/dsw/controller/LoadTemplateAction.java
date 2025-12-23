package raf.graffito.dsw.controller;

import raf.graffito.dsw.controller.serializer.Serializer;

import java.awt.event.ActionEvent;

public class LoadTemplateAction extends AbstractGraffAction {
    private Serializer serializer;

    public LoadTemplateAction(Serializer serializer) {
        putValue(NAME, "Učitaj šablon");
        putValue(SHORT_DESCRIPTION, "Učitaj šablon iz galerije");
        this.serializer = serializer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        serializer.loadTemplate();
    }
}

