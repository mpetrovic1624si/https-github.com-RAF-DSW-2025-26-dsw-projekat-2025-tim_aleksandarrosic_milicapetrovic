package raf.graffito.dsw.controller;

import raf.graffito.dsw.controller.serializer.Serializer;

import java.awt.event.ActionEvent;

public class SaveTemplateAction extends AbstractGraffAction {
    private Serializer serializer;

    public SaveTemplateAction(Serializer serializer) {
        putValue(NAME, "Sačuvaj kao šablon");
        putValue(SHORT_DESCRIPTION, "Sačuvaj projekat kao šablon");
        this.serializer = serializer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        serializer.saveAsTemplate();
    }
}

