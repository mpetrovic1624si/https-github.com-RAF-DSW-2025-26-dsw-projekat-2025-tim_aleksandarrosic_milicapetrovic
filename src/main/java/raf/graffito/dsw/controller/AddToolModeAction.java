package raf.graffito.dsw.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddToolModeAction extends SetToolModeAction {
    public AddToolModeAction() {
        super("Add", null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SlideController controller = SlideControllerManager.getInstance().getCurrentController();
        if (controller != null) {
            controller.setAddMode();
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

