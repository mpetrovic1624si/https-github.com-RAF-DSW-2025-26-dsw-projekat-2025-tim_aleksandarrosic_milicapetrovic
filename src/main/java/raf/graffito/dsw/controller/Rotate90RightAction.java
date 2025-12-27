package raf.graffito.dsw.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Rotate90RightAction extends AbstractGraffAction {
    
    public Rotate90RightAction() {
        putValue(NAME, "Rotate 90° Right");
        putValue(SHORT_DESCRIPTION, "Rotate selected elements 90 degrees right");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        SlideController controller = SlideControllerManager.getInstance().getCurrentController();
        if (controller != null) {
            controller.rotate90Right();
        }
    }
}

