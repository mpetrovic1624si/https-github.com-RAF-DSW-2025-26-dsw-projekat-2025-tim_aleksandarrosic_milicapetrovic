package raf.graffito.dsw.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AboutUsAction extends AbstractGraffAction {
    public AboutUsAction() { putValue(NAME, "About"); }
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Graffito - demo", "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
