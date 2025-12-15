package raf.graffito.dsw.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends AbstractGraffAction {
    public ExitAction() {
        putValue(NAME, "Exit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int r = JOptionPane.showConfirmDialog(null, "Exit application?", "Exit", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) System.exit(0);
    }
}
