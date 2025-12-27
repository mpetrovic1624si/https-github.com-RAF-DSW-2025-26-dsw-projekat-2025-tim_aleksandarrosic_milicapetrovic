package raf.graffito.dsw.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveToolModeAction extends SetToolModeAction {
    public MoveToolModeAction() {
        super("Move", null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SlideController controller = SlideControllerManager.getInstance().getCurrentController();
        if (controller != null) {
            controller.setMoveMode();
            if (e.getSource() instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) e.getSource();
                ButtonGroup group = (ButtonGroup) button.getClientProperty("buttonGroup");
                if (group != null) {
                    for (java.util.Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                        buttons.nextElement().setSelected(false);
                    }
                    button.setSelected(true);
                }
            }
        }
    }
}

