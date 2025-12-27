package raf.graffito.dsw.gui.swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowModePanel extends JPanel {
    
    private ButtonGroup modeGroup;
    private JRadioButton normalButton;
    private JRadioButton fullscreenButton;
    private JRadioButton smallButton;
    private WindowModeController controller;
    
    public WindowModePanel() {
        this.controller = WindowModeController.getInstance();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        modeGroup = new ButtonGroup();
        
        normalButton = new JRadioButton("Normal");
        normalButton.setSelected(true);
        normalButton.addActionListener(e -> controller.setMode(WindowMode.NORMAL));
        modeGroup.add(normalButton);
        add(normalButton);
        
        fullscreenButton = new JRadioButton("Fullscreen");
        fullscreenButton.addActionListener(e -> controller.setMode(WindowMode.FULLSCREEN));
        modeGroup.add(fullscreenButton);
        add(fullscreenButton);
        
        smallButton = new JRadioButton("Small");
        smallButton.addActionListener(e -> controller.setMode(WindowMode.SMALL));
        modeGroup.add(smallButton);
        add(smallButton);
    }
}

