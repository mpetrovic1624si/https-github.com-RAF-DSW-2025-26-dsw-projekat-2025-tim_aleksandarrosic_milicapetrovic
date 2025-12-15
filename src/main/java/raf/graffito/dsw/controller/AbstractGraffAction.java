package raf.graffito.dsw.controller;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class AbstractGraffAction extends AbstractAction {
    protected Icon loadIcon(String path) {
        Icon icon = null;
        URL imageURL = getClass().getResource(path);
        if (imageURL != null) {
            Image img = new ImageIcon(imageURL).getImage();
            Image newImg = img.getScaledInstance(20,20,Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);
        }
        return icon;
    }
}
