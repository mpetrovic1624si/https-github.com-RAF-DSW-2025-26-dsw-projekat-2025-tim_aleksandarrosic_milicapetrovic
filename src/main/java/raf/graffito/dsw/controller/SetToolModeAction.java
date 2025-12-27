package raf.graffito.dsw.controller;

import raf.graffito.dsw.controller.toolmodes.ToolMode;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class SetToolModeAction extends AbstractGraffAction {
    private ToolMode mode;

    public SetToolModeAction(String name, ToolMode mode) {
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, name);
        this.mode = mode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SlideController controller = SlideControllerManager.getInstance().getCurrentController();
        if (controller != null) {
            controller.setMode(mode);
            // Update button selection state
            if (e.getSource() instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) e.getSource();
                // Deselect other buttons in the same button group
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

