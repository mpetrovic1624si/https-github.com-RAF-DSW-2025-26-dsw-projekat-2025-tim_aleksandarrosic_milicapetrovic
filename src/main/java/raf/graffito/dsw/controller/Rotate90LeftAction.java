package raf.graffito.dsw.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Rotate90LeftAction extends AbstractGraffAction {
    
    public Rotate90LeftAction() {
        putValue(NAME, "Rotate 90° Left");
        putValue(SHORT_DESCRIPTION, "Rotate selected elements 90 degrees left");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        SlideController controller = SlideControllerManager.getInstance().getCurrentController();
        if (controller != null) {
            controller.rotate90Left();
        }
    }
}

